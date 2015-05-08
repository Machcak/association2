package pl.bzowski.association.business.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 *
 * @author Machcak
 */
@Entity
@Table(name = "leadershipmember")
@Audited
public class LeadershipMember implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Leadership leadership;
    
    @ManyToOne
    private AssociationMember member;
    

    public LeadershipMember() {
    }

    public LeadershipMember(Leadership leadership, AssociationMember member) {
        this.leadership = leadership;
        this.member = member;
    }

    public Leadership getLeadership() {
        return leadership;
    }

    public void setLeadership(Leadership leadership) {
        this.leadership = leadership;
    }

    public AssociationMember getMember() {
        return member;
    }

    public void setMember(AssociationMember member) {
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof LeadershipMember)) {
            return false;
        }
        LeadershipMember other = (LeadershipMember) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "pl.bzowski.association.business.entity.LeadershipMember[ id=" + id + " ]";
    }

}
