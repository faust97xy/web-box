package pcshaman.web.core.model.entities;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import pcshaman.web.core.model.collections.UzytkownikTyp;

/**
 *
 * @author jjurczak
 */
@Data
@MappedSuperclass
public abstract class UserEntity extends BasicEntity {

    /* *** */
    @Column(name = "aktywny", columnDefinition = "boolean default true")
    private boolean aktywny;
    /* *** */
    @Column(name = "email")
    private String email;
    /* *** */
    @Column(name = "pass")
    private String pswd;
    /* *** */
    @Column(name = "typ")
    @Enumerated(EnumType.STRING)
    private UzytkownikTyp typ;

    public abstract String getLogin();

}
