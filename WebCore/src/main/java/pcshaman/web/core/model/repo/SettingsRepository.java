package pcshaman.web.core.model.repo;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import pcshaman.web.core.model.entities.SettingsEntity;

/**
 *
 * @author jjurczak
 * @param <E>
 */
@NoRepositoryBean
public interface SettingsRepository <E extends SettingsEntity> extends BasicRepository<E> {

    List<E> getSettings4Group(String groupe);

    E getSettings4Name(String name);
}

