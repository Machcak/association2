package pl.bzowski.association.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

/**
 *
 * @author Machcak
 */
@Entity
@Table(name = "membershiphistory")
@NamedQueries({
    @NamedQuery(name = "Membershiphistory.findAll", query = "SELECT m FROM Membershiphistory m"),
    @NamedQuery(name = "Membershiphistory.findById", query = "SELECT m FROM Membershiphistory m WHERE m.id = :id"),
    @NamedQuery(name = "Membershiphistory.findByDatefrom", query = "SELECT m FROM Membershiphistory m WHERE m.datefrom = :datefrom"),
    @NamedQuery(name = "Membershiphistory.findByDateto", query = "SELECT m FROM Membershiphistory m WHERE m.dateto = :dateto"),
    @NamedQuery(name = Membershiphistory.FIND_ALL_TODAY_ACTIVE_MEMBERS,
    query = "SELECT mh.member FROM Membershiphistory mh "
            + " WHERE :curentDate  between mh.datefrom and CASE WHEN (mh.dateto is null) THEN '9999-12-31' ELSE mh.dateto END "
            )
})
@Audited
public class Membershiphistory implements Serializable {
	
	public static final String FIND_ALL_TODAY_ACTIVE_MEMBERS = "AssociationMember.FIND_ALL_TODAY_ACTIVE_MEMBERS";
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="member_id")
    private AssociationMember member;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "datefrom")
    @Temporal(TemporalType.DATE)
    private Date datefrom;
    
    @Column(name = "dateto")
    @Temporal(TemporalType.DATE)
    private Date dateto;

    public Membershiphistory() {
    }

    public Membershiphistory(Long id) {
        this.id = id;
    }

    public Membershiphistory(Long id, Date datefrom) {
        this.id = id;
        this.datefrom = datefrom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(Date datefrom) {
        this.datefrom = datefrom;
    }

    public Date getDateto() {
        return dateto;
    }

    public void setDateto(Date dateto) {
        this.dateto = dateto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Membershiphistory)) {
            return false;
        }
        Membershiphistory other = (Membershiphistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.bzowski.association.business.entity.Membershiphistory[ id=" + id + " ]";
    }

    public AssociationMember getMember() {
        return member;
    }

    public void setMember(AssociationMember member) {
        this.member = member;
    }
}
