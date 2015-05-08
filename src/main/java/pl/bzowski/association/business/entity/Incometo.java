package pl.bzowski.association.business.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

/**
 *
 * @author Machcak
 */
@Entity
@Table(name = "incometo")
@Audited
@NamedQueries({
    @NamedQuery(name = "Incometo.findAll", query = "SELECT i FROM Incometo i"),
    @NamedQuery(name = "Incometo.findById", query = "SELECT i FROM Incometo i WHERE i.id = :id"),
    @NamedQuery(name = "Incometo.findByTitle", query = "SELECT i FROM Incometo i WHERE i.title = :title")})
public class Incometo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 50)
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "incometo")
    private List<Balance> balanceList;

    public Incometo() {
    }

    public Incometo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Balance> getBalanceList() {
        return balanceList;
    }

    public void setBalanceList(List<Balance> balanceList) {
        this.balanceList = balanceList;
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
        if (!(object instanceof Incometo)) {
            return false;
        }
        Incometo other = (Incometo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.bzowski.association.business.entity.Incometo[ id=" + id + " ]";
    }

}
