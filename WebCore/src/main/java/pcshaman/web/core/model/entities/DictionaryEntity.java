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
public abstract class DictionaryEntity extends BasicEntity {

    /* *** */
    @Column(name = "aktywny", columnDefinition = "boolean default true")
    private Boolean aktywny;
    /* *** */
    @Column(name = "nazwa")
    private String nazwa;
    /* *** */
    @Column(name = "opis", columnDefinition = "text")
    private String opis;
    
}
