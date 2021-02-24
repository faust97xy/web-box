package pcshaman.web.core.model.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pcshaman.web.core.model.entities.BasicEntity;

/**
 *
 * @author jjurczak
 * @param <E>
 */
@NoRepositoryBean
public interface AtomRepository<E extends BasicEntity> extends CrudRepository<E, Long> {
    
}
