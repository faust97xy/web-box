package pcshaman.web.core.model.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pcshaman.web.core.model.entities.BasicEntity;
import pcshaman.web.core.model.repo.BasicRepository;

/**
 *
 * @author jjurczak
 * @param <E>
 */
public abstract class BasicDAO<E extends BasicEntity> extends AtomDAO<E> {

    @Override
    protected abstract BasicRepository<E> getRepository();

    public Page<E> getExist(Pageable page) {
        return getRepository().getExist(page);
    }

    public Page<E> getDelete(Pageable page) {
        return getRepository().getDelete(page);
    }

    public Page<E> existSuperSearcher(String find, Pageable page) {
        return getRepository().existSuperSearcher(find, page);
    }

    public Page<E> removeSuperSearcher(String find, Pageable page) {
        return getRepository().removeSuperSearcher(find, page);
    }

}
