package pcshaman.web.core.model.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pcshaman.web.core.model.entities.DictionaryEntity;
import pcshaman.web.core.model.entities.UserEntity;
import pcshaman.web.core.model.repo.DictionaryRepository;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 * @param <E>
 */
public abstract class DictionaryDAO<E extends DictionaryEntity> extends BasicDAO<E> {

    @Override
    protected abstract DictionaryRepository<E> getRepository();

    public List<E> getDictionary() {
        return getRepository().getDictionary();
    }

    public Page<E> getActive(Pageable page) {
        return getRepository().getActive(page);
    }

    public Page<E> activeSuperSearcher(String find, Pageable page) {
        return getRepository().activeSuperSearcher(find, page);
    }

    public boolean activate(E entity, UserEntity operator) {
        boolean isOK = Boolean.FALSE;
        if (ObjectTool.exist(entity)) {
            Boolean aktywny = entity.getAktywny();
            entity.setAktywny(!aktywny);
            isOK = save(entity, operator);
        }
        return isOK;
    }

    public boolean activate(String id, UserEntity operator) {
        boolean isOK = Boolean.FALSE;
        if (ObjectTool.exist(id)) {
            E entity = get(Long.valueOf(id));
            Boolean aktywny = entity.getAktywny();
            entity.setAktywny(!aktywny);
            isOK = save(entity, operator);
        }
        return isOK;
    }

    public int activateAll(String[] ids, UserEntity operator) {
        int count = 0;
        if (ObjectTool.exist(ids)) {
            for (String id : ids) {
                activate(id, operator);
            }
        }
        return count;
    }

}
