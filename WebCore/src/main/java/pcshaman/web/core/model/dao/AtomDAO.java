package pcshaman.web.core.model.dao;

import java.util.Date;
import java.util.Optional;
import pcshaman.web.core.model.entities.BasicEntity;
import pcshaman.web.core.model.entities.UserEntity;
import pcshaman.web.core.model.repo.AtomRepository;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 * @param <E>
 */
public abstract class AtomDAO<E extends BasicEntity> {

    protected abstract AtomRepository<E> getRepository();

    public E get(Long id) {
        return ObjectTool.exist(id) ? getRepository().findById(id).get() : null;
    }

    public E saveAndGet(E entiti, UserEntity user) {
        if (ObjectTool.exist(entiti)) {
            Long userId = (user == null) ? null : user.getId();
            if (ObjectTool.exist(entiti.getId())) {
                entiti.setUpdateDate(new Date());
                entiti.setUpdateUser(userId);

                E get = get(entiti.getId());
                entiti.setInsertDate(get.getInsertDate());
                entiti.setInsertUser(get.getInsertUser());

                entiti.setDeleteDate(get.getDeleteDate());
                entiti.setDeleteUser(get.getDeleteUser());

            } else {
                entiti.setInsertDate(new Date());
                entiti.setInsertUser(userId);
            }

            return getRepository().save(entiti);

        } else {
            return null;
        }
    }

    public boolean save(E entiti, UserEntity operator) {
        return saveAndGet(entiti, operator) != null;
    }

    public boolean remove(Long id, UserEntity operator) {
        boolean ok = Boolean.FALSE;
        if (ObjectTool.exist(id)) {
            Optional<E> optional = getRepository().findById(id);
            E entiti = optional.get();
            entiti.setDeleteDate(new Date());
            entiti.setDeleteUser(operator.getId());

            getRepository().save(entiti);
            ok = Boolean.TRUE;
        }
        return ok;
    }

    public int removes(String[] ids, UserEntity operator) {
        int count = 0;
        if (ObjectTool.exist(ids)) {
            for (String id : ids) {
                if (ObjectTool.exist(id)) {
                    boolean remove = remove(Long.valueOf(id), operator);
                    count += remove ? 1 : 0;
                }
            }
        }
        return count;
    }

}
