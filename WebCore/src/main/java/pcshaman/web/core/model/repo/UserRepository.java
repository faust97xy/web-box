package pcshaman.web.core.model.repo;

import org.springframework.data.repository.NoRepositoryBean;
import pcshaman.web.core.model.entities.UserEntity;

/**
 *
 * @author jjurczak
 * @param <U>
 */
@NoRepositoryBean
public interface UserRepository<U extends UserEntity> extends BasicRepository<U> {

    U getUser4Email(String name);

}
