package pcshaman.web.core.model.repo;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pcshaman.web.core.model.entities.LoginHistory;

/**
 *
 * @author jjurczak
 * @param <E>
 */
public interface LoginHistoryRepository<E extends LoginHistory> extends CrudRepository<E, Long> {

    E getLoginHistory4SesionId(@Param("sesionId") String sesionId);

    E getLoginHistory4User(@Param("userId") Long userId, @Param("zalogowany") Date zalogowany);

    List<E> getAllLoginHistory4User(@Param("userId") Long userId);
}
