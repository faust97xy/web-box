package pcshaman.web.core.model.entities;

import java.util.Date;
import javax.persistence.Column;
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
public abstract class BasicEntity {

    /* *** */
    @Column(name = "insert_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;
    /* *** */
    @Column(name = "insert_user")
    private Long insertUser;
    /* *** */
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    /* *** */
    @Column(name = "update_user")
    private Long updateUser;
    /* *** */
    @Column(name = "delete_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;
    /* *** */
    @Column(name = "delete_user")
    private Long deleteUser;

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
