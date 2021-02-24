package pcshaman.web.core.model.repo;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import pcshaman.web.core.model.entities.DictionaryEntity;

/**
 *
 * @author jjurczak
 * @param <E>
 */
@NoRepositoryBean
public interface DictionaryRepository<E extends DictionaryEntity> extends BasicRepository<E> {

    List<E> getDictionary();
    
    Page<E> getActive(Pageable page);
    
    Page<E> activeSuperSearcher(String find, Pageable page);
}
