package pcshaman.web.core.model.entities;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 */
@Data
@MappedSuperclass
public abstract class LoginHistory {

    /* *** */
    @JoinColumn(name = "uzytkownik", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UserEntity uzytkownik;
    /* *** */
    @Basic(optional = false)
    @Column(name = "zalogowany")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zalogowany;
    /* *** */
    @Column(name = "wylogowany")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wylogowany;
    /* *** */
    @Basic(optional = false)
    @Column(name = "ip")
    private String ip;
    /* *** */
    @Basic(optional = false)
    @Column(name = "sesion_id")
    private String sesionId;

    /* =============================================================== */
    public abstract Long getId();

    public abstract void setId(Long id);

    /* =============================================================== */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean equals = Boolean.FALSE;
        if (ObjectTool.notNull(object)) {
            if (object instanceof BasicEntity) {
                BasicEntity other = (BasicEntity) object;
                if (ObjectTool.existAll(this.getId(), other.getId())) {
                    equals = this.getId().compareTo(other.getId()) == 0;
                }
            }
        }
        return equals;

    }

    /* ========================================================================= */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName()).append("(ID:").append(this.getId()).append(")");
        return builder.toString();
    }

}
