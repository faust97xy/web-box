package pcshaman.web.core.front.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import pcshaman.web.core.model.dao.BasicDAO;
import pcshaman.web.core.model.entities.BasicEntity;
import pcshaman.web.core.model.entities.UserEntity;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 * @param <E>
 */
public abstract class BasicController<E extends BasicEntity> extends AtomController<E> {

    /* ========================================================================
       MODEL
     */
    protected class BrowserModel {

        public Integer page_no;
        public Integer length;
        public Sort sort;
        public String search_value;
        public Map<String, String> search_map = new LinkedHashMap<>();
    }


    /* ========================================================================
       ABSTRACT
     */
    @Override
    protected abstract BasicDAO<E> getDAO();

    /* ========================================================================
       BROWSER
     */
    public abstract ModelAndView browser(HttpServletRequest request);

    public ResponseEntity browserController(Map<String, String> params) {
        UserEntity user = getUser();

        if (ObjectTool.exist(user)) {
            ////////////wydruk danych wejściowych
            printParams(params);

            ////////////obsługa wyszukiwania i paginacji
            BrowserModel model = new BrowserModel();
            Integer start = Integer.valueOf(params.get("start"));
            model.search_value = (params.get("search[value]") == null) ? "" : params.get("search[value]").trim();
            model.length = Integer.valueOf(params.get("length"));
            model.page_no = (start == 0) ? 0 : start / model.length;

            ////////////obsługa wyszukiwania po kolumnach
            int index = 0;
            String keyDat = "columns[" + index + "][data]";
            while (params.containsKey(keyDat)) {
                String value = params.get("columns[" + index + "][search][value]");
                if (ObjectTool.exist(value)) {
                    model.search_map.put(params.get(keyDat), value);
                }

                index++;
                keyDat = "columns[" + index + "][data]";
            }

            ////////////obsługa sortowania po kolumnach
            Integer order_index = Integer.valueOf(params.get("order[0][column]"));
            String order_dir = (params.get("order[0][dir]") == null) ? "asc" : params.get("order[0][dir]").trim();
            String order_field = params.get("columns[" + order_index + "][data]").trim().replace("_", ".");
            model.sort = new Sort(order_dir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, order_field);

            ////////////wysłanie sql
            Page<E> records = executeteQuery(model);

            ////////////wysłanie danych zwrotnych jeson
            GsonBuilder gsonBulider = getGsonBuilder();
            Gson gson = gsonBulider.create();

            browserDraw++;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("draw", browserDraw);
            jsonObject.addProperty("recordsTotal", records.getTotalElements());
            jsonObject.addProperty("recordsFiltered", records.getTotalElements());
            jsonObject.add("data", gson.toJsonTree(records.getContent()));

            ////////////zwtotówka
            return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);

        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

    }

    protected Page<E> executeteQuery(BrowserModel model) {
        PageRequest pageRequest = new PageRequest(model.page_no, model.length, model.sort);
        Page<E> records;
        if (ObjectTool.exist(model.search_value)) {
            records = getDAO().existSuperSearcher(model.search_value.toLowerCase().trim().replace("*", "%"), pageRequest);
        } else {
            records = getDAO().getExist(pageRequest);
        }
        return records;
    }

}
