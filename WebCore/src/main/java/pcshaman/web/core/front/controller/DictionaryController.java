package pcshaman.web.core.front.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import pcshaman.web.core.model.dao.DictionaryDAO;
import pcshaman.web.core.model.entities.DictionaryEntity;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 * @param <E>
 */
public abstract class DictionaryController<E extends DictionaryEntity> extends BasicController<E> {

    /* ========================================================================
       ABSTRACT
     */
    @Override
    protected abstract DictionaryDAO<E> getDAO();

    /* ========================================================================
       BROWSER
     */
    @Override
    public abstract ModelAndView browser(HttpServletRequest request);

    @Override
    public ResponseEntity browserController(Map<String, String> params) {
        return super.browserController(params);
    }

    @Override
    protected Page<E> executeteQuery(BrowserModel model) {
        PageRequest pageRequest = new PageRequest(model.page_no, model.length, model.sort);
        Page<E> records;
        if (ObjectTool.exist(model.search_value)) {
            records = getDAO().activeSuperSearcher(model.search_value.toLowerCase().trim().replace("*", "%"), pageRequest);
        } else {
            records = getDAO().getActive(pageRequest);
        }
        return records;
    }

    /* ========================================================================
       ACTIONS
     */
    @Override
    public ResponseEntity get(Map<String, String> params) {
        return super.get(params);
    }

    @Override
    public ResponseEntity save(E entiti) {
        return super.save(entiti);
    }

    @Override
    public ResponseEntity remove(Map<String, String> params) {
        return super.remove(params);
    }

    public ResponseEntity activateAll(Map<String, String> params) {
        ////////////wydruk danych wejściowych
        printParams(params);

        ////////////obrubka
        String ids = (params.get("ids") == null) ? "" : params.get("ids").trim();
        ResponseEntity response;
        if (ids.isEmpty()) {
            response = new ResponseEntity("Brak elementów do aktywacji/dezaktywacji!", HttpStatus.BAD_REQUEST);

        } else {
            DictionaryDAO dao = getDAO();
            int count = dao.activateAll(ids.split(";"), getUser());
            response = new ResponseEntity("Aktowoano/Dezaktywowano " + count + " elementów!", HttpStatus.OK);
        }

        ////////////
        return response;
    }

}
