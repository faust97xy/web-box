package pcshaman.web.core.model.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;

/**
 *
 * @author jjurczak
 */
@Data
@MappedSuperclass
public abstract class SettingsEntity extends BasicEntity {

    /* *** */
    @Column(name = "name")
    private String name;
    /* *** */
    @Column(name = "groupe")
    private String groupe;
    /* *** */
    @Column(name = "value")
    private String value;
    /* *** */
    @Column(name = "type")
    private String type;

}
