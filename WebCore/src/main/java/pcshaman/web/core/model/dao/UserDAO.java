package pcshaman.web.core.model.dao;

import pcshaman.web.core.model.entities.UserEntity;
import pcshaman.web.core.model.repo.UserRepository;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 * @param <U>
 */
public abstract class UserDAO<U extends UserEntity> extends BasicDAO<U> {

    @Override
    protected abstract UserRepository<U> getRepository();

    public boolean existUser(String login) {
        return getUser4Email(login) != null;
    }

    public U getUser4Email(String login) {
        return getRepository().getUser4Email(login);
    }

    public boolean exist(U user) {
        boolean isOk = Boolean.FALSE;
        if (ObjectTool.exist(user)) {
            isOk = ObjectTool.exist(getUser4Email(user.getLogin()));

        } else {
            throw new NullPointerException("Brak przekazanego użytkownika do sprawdzenia czy danny użytkownik istnieje!");
        }
        return isOk;
    }

    public boolean activate(U user, UserEntity operator) {
        boolean isOK = Boolean.FALSE;
        if (ObjectTool.exist(user)) {
            Boolean aktywny = user.isAktywny();
            user.setAktywny(!aktywny);
            isOK = save(user, operator);
        }
        return isOK;
    }

    public boolean activate(String id, UserEntity operator) {
        boolean isOK = Boolean.FALSE;
        if (ObjectTool.exist(id)) {
            U entity = get(Long.valueOf(id));
            Boolean aktywny = entity.isAktywny();
            entity.setAktywny(!aktywny);
            isOK = save(entity, operator);
        }
        return isOK;
    }

    public int activateALL(String[] ids, UserEntity operator) {
        int count = 0;
        if (ObjectTool.exist(ids)) {
            for (String id : ids) {
                activate(id, operator);
            }
        }
        return count;
    }

}
