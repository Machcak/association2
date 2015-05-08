package pl.bzowski.association.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

/**
 *
 * @author Machcak
 */
@Entity
@Table(name = "balanceterm")
@Audited
@NamedQueries({
    @NamedQuery(name = "Balanceterm.findAll", query = "SELECT b FROM Balanceterm b"),
    @NamedQuery(name = "Balanceterm.findById", query = "SELECT b FROM Balanceterm b WHERE b.id = :id"),
    @NamedQuery(name = "Balanceterm.findByTermname", query = "SELECT b FROM Balanceterm b WHERE b.termname = :termname"),
    @NamedQuery(name = "Balanceterm.findByDatefrom", query = "SELECT b FROM Balanceterm b WHERE b.datefrom = :datefrom"),
    @NamedQuery(name = "Balanceterm.findByDateto", query = "SELECT b FROM Balanceterm b WHERE b.dateto = :dateto")})
public class Balanceterm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "termname")
    private String termname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datefrom")
    @Temporal(TemporalType.DATE)
    private Date datefrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateto")
    @Temporal(TemporalType.DATE)
    private Date dateto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "balanceterm")
    private List<Balance> balanceList;

    public Balanceterm() {
    }

    public Balanceterm(Long id) {
        this.id = id;
    }

    public Balanceterm(Long id, String termname, Date datefrom, Date dateto) {
        this.id = id;
        this.termname = termname;
        this.datefrom = datefrom;
        this.dateto = dateto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTermname() {
        return termname;
    }

    public void setTermname(String termname) {
        this.termname = termname;
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
        if (!(object instanceof Balanceterm)) {
            return false;
        }
        Balanceterm other = (Balanceterm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.bzowski.association.business.entity.Balanceterm[ id=" + id + " ]";
    }

}
