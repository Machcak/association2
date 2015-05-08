package pl.bzowski.association.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "balance")
@Audited
@NamedQueries({
    @NamedQuery(name = "Balance.findAll", query = "SELECT b FROM Balance b"),
    @NamedQuery(name = "Balance.findById", query = "SELECT b FROM Balance b WHERE b.id = :id"),
    @NamedQuery(name = "Balance.findByIncomingdate", query = "SELECT b FROM Balance b WHERE b.incomingdate = :incomingdate"),
    @NamedQuery(name = "Balance.findByAmount", query = "SELECT b FROM Balance b WHERE b.amount = :amount"),
    @NamedQuery(name = Balance.ACCOUNT_BALANCE, query = "SELECT SUM(b.amount) FROM Balance b WHERE ( b.incometo.title = 'Konto'  or b.incometo.title = 'Kasa'  or  b.incometo.title = 'Bilans Otwarcia Miesiąca' ) "),
    @NamedQuery(name = Balance.FIND_ALL_ORDER_BY_INCOMINGDATE_DESC,
            query = "SELECT b FROM Balance b ORDER BY b.incomingdate DESC"),
    @NamedQuery(name = Balance.FIND_LAST_INCOME_FROM_MEMBER,
            query = "SELECT b FROM Balance b WHERE b.associationMember = :member ORDER BY b.balanceterm.dateto desc"),
    @NamedQuery(name = Balance.ACCOUNT,
            query = "SELECT SUM(b.amount) FROM Balance b WHERE ( b.incometo.title = 'Konto'  or b.incometo.title = 'Operacja na bankomacie'  or  b.incometo.title = 'Bilans Otwarcia Miesiąca')")
})
public class Balance implements Serializable {
    
    public static final String ACCOUNT_BALANCE = "Balance.ACCOUNT_BALANCE";
    
    public static final String FIND_ALL_ORDER_BY_INCOMINGDATE_DESC = "Balance.FIND_ALL_ORDER_BY_INCOMINGDATE_DESC";
    
    public static final String FIND_LAST_INCOME_FROM_MEMBER = "Balance.FIND_LAST_INCOME_FROM_MEMBER";
    
    public static final String ACCOUNT = "Balance.ACCOUNT";
        
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "incomingdate")
    @Temporal(TemporalType.DATE)
    private Date incomingdate;
   
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;
   
    @JoinColumn(name = "incometo_id", referencedColumnName = "id")
    @ManyToOne
    private Incometo incometo;
    
    @Column(name = "description")
    private String description;
  
    @JoinColumn(name = "balanceevent_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Balanceevent balanceevent;
 
    @JoinColumn(name = "balanceterm_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Balanceterm balanceterm;
    
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AssociationMember associationMember;

    public Balance() {
    }

    public Balance(Long id) {
        this.id = id;
    }

    public Balance(Long id, Date incomingdate, BigDecimal amount) {
        this.id = id;
        this.incomingdate = incomingdate;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getIncomingdate() {
        return incomingdate;
    }

    public void setIncomingdate(Date incomingdate) {
        this.incomingdate = incomingdate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Incometo getIncometo() {
        return incometo;
    }

    public void setIncometo(Incometo incometo) {
        this.incometo = incometo;
    }

    public Balanceevent getBalanceevent() {
        return balanceevent;
    }

    public void setBalanceevent(Balanceevent balanceevent) {
        this.balanceevent = balanceevent;
    }

    public Balanceterm getBalanceterm() {
        return balanceterm;
    }

    public void setBalanceterm(Balanceterm balanceterm) {
        this.balanceterm = balanceterm;
    }

    public AssociationMember getAssociationMember() {
        return associationMember;
    }

    public void setAssociationMember(AssociationMember associationMember) {
        this.associationMember = associationMember;
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
        if (!(object instanceof Balance)) {
            return false;
        }
        Balance other = (Balance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.bzowski.association.business.entity.Balance[ id=" + id + " ]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
