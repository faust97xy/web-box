package pcshaman.web.core.model.dao;

import java.util.Date;
import java.util.List;
import pcshaman.web.core.model.entities.LoginHistory;
import pcshaman.web.core.model.entities.UserEntity;
import pcshaman.web.core.model.repo.LoginHistoryRepository;
import pcshaman.web.core.model.repo.UserRepository;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 */
public abstract class LoginHistoryDAO {

    protected abstract LoginHistoryRepository getLoginHistoryRepository();

    protected abstract UserRepository getUserRepository();

    protected abstract LoginHistory newObject();

    public LoginHistory getLoginHistory4SesionId(String sesionId) {
        return getLoginHistoryRepository().getLoginHistory4SesionId(sesionId);
    }

    public LoginHistory getLoginHistory4User(Long userId, Date zalogowany) {
        return getLoginHistoryRepository().getLoginHistory4User(userId, zalogowany);
    }

    public List getAllLoginHistory4User(Long userId) {
        return getLoginHistoryRepository().getAllLoginHistory4User(userId);
    }

    public LoginHistory getLastLoginHistory4User(String userName) {
        LoginHistory loginHistory = null;

        if (ObjectTool.exist(userName)) {
            UserEntity user = getUserRepository().getUser4Email(userName);
            List<LoginHistory> list = getAllLoginHistory4User(user.getId());
            loginHistory = ObjectTool.exist(list) ? list.get(0) : null;
        }

        return loginHistory;
    }

    public void login(String userName, String sesionId, String addressIp) {
        if (ObjectTool.exist(userName)) {
            UserEntity user = getUserRepository().getUser4Email(userName);

            if (ObjectTool.exist(user)) {
                LoginHistory history = newObject();
                history.setUzytkownik(user);
                history.setZalogowany(new Date());
                history.setIp(addressIp);
                history.setSesionId(ObjectTool.exist(sesionId) ? sesionId : "BRAK");
                getLoginHistoryRepository().save(history);
            }
        }
    }

    public void logout(String userName) {
        if (ObjectTool.exist(userName)) {
            UserEntity user = getUserRepository().getUser4Email(userName);

            if (ObjectTool.exist(user)) {
                LoginHistory history = getLastLoginHistory4User(userName);
                history.setWylogowany(new Date());
                getLoginHistoryRepository().save(history);
            }
        }
    }

}
