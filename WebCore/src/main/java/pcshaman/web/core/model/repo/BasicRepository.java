package pcshaman.web.core.model.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import pcshaman.web.core.model.entities.BasicEntity;

/**
 *
 * @author jjurczak
 * @param <E>
 */
@NoRepositoryBean
public interface BasicRepository<E extends BasicEntity> extends AtomRepository<E> {

    Page<E> getExist(Pageable page);

    Page<E> getDelete(Pageable page);

    Page<E> existSuperSearcher(String find, Pageable page);

    Page<E> removeSuperSearcher(String find, Pageable page);

}
