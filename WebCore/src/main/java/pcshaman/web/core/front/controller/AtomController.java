package pcshaman.web.core.front.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pcshaman.web.core.model.dao.AtomDAO;
import pcshaman.web.core.model.dao.UserDAO;
import pcshaman.web.core.model.entities.BasicEntity;
import pcshaman.web.core.model.entities.UserEntity;
import pcshaman.web.core.front.security.SecurityManager;

/**
 *
 * @author jjurczak
 * @param <E>
 */
@RestController
public abstract class AtomController<E extends BasicEntity> extends Controller {

    protected abstract AtomDAO<E> getDAO();

    @Autowired
    UserDAO userDAO;

    protected final UserEntity getUser() {
        SecurityManager securityManager = new SecurityManager();
        String userName = securityManager.getUserName();
        return userDAO.getUser4Email(userName);
    }

  public ResponseEntity get(Map<String, String> params) {
        ////////////wydruk danych wejściowych
        printParams(params);

        ////////////obrubka
        Long id = Long.valueOf((params.get("id") == null) ? null : params.get("id").trim());
        E entiti = getDAO().get(id);

        ////////////zwtotówka
        return new ResponseEntity(entiti, HttpStatus.OK);
    }

    public ResponseEntity save(E entiti) {
        ////////////wydruk danych wejściowych
        getLogger().info("RequestParam map: {}", entiti);

        ////////////zapis danych
        ResponseEntity response;
        boolean save = getDAO().save(entiti, getUser());
        if (save) {
            try {
                E nInstance = (E) entiti.getClass().newInstance();
                response = new ResponseEntity(nInstance, HttpStatus.OK);

            } catch (InstantiationException | IllegalAccessException ex) {
                getLogger().error("Przy tworzeniu nowej istancji wystąpił wyjątek: ", ex);
                response = new ResponseEntity(entiti, HttpStatus.BAD_GATEWAY);
            }
        } else {
            response = new ResponseEntity(entiti, HttpStatus.BAD_GATEWAY);
        }

        ////////////zwtotówka
        return response;

    }

    public ResponseEntity remove(Map<String, String> params) {
        ////////////wydruk danych wejściowych
        printParams(params);

        ////////////obrubka
        String ids = (params.get("ids") == null) ? "" : params.get("ids").trim();
        ResponseEntity response;
        if (ids.isEmpty()) {
            response = new ResponseEntity("Brak elementów do usunięcia!", HttpStatus.BAD_REQUEST);

        } else {
            int count = getDAO().removes(ids.split(";"), getUser());
            response = new ResponseEntity("Usunięto " + count + " elementów!", HttpStatus.OK);
        }

        ////////////
        return response;
    }

}

