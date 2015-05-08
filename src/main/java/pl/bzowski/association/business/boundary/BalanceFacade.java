package pl.bzowski.association.business.boundary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import pl.bzowski.association.business.entity.AssociationMember;
import pl.bzowski.association.business.entity.Balance;
import pl.bzowski.association.business.entity.Balanceterm;
import pl.bzowski.association.business.entity.Membershiphistory;
import pl.bzowski.association.presentation.MembersPaiedInThisTerm;

/**
 *
 * @author Machcak
 */
@Stateless
public class BalanceFacade extends AbstractFacade<Balance> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BalanceFacade() {
        super(Balance.class);
    }

    public BigDecimal getAccountBalance() {
        return em.createNamedQuery(Balance.ACCOUNT_BALANCE, BigDecimal.class)
                .getSingleResult();
    }

    public List<Balance> findAllOrderByIncomingdateDesc() {
        return em.createNamedQuery(Balance.FIND_ALL_ORDER_BY_INCOMINGDATE_DESC, Balance.class).getResultList();
    }

    public Collection<MembersPaiedInThisTerm> getLastPaidContribution() {
        List<AssociationMember> activeMembers = em.createNamedQuery(Membershiphistory.FIND_ALL_TODAY_ACTIVE_MEMBERS, AssociationMember.class)
                .setParameter("curentDate", new Date())
                .getResultList();
        Map<Balanceterm, Collection<AssociationMember>> membersInTerm = new HashMap<>();
        for(AssociationMember am : activeMembers){
            Balance lastMemberIncome = null;
            Balanceterm balanceterm = null;
            try{
                lastMemberIncome = em.createNamedQuery(Balance.FIND_LAST_INCOME_FROM_MEMBER, Balance.class)                    
                        .setParameter("member", am)
                        .setFirstResult(0)
                        .setMaxResults(1)
                        .getSingleResult();
                balanceterm = lastMemberIncome.getBalanceterm();
            }catch(NoResultException ex){
                balanceterm = new Balanceterm(-1L, "Nowy członek", new Date(), new Date());
            }    
            
            if(!membersInTerm.containsKey(balanceterm)){
                Collection<AssociationMember> collection = new ArrayList<>();
                membersInTerm.put(balanceterm, collection);
            }
            Collection<AssociationMember> get = membersInTerm.get(balanceterm);
            get.add(am);
        }
        Collection<MembersPaiedInThisTerm> membersPaiedInThisTerms = new ArrayList<>();
        Set<Balanceterm> keySet = membersInTerm.keySet();
        List<Balanceterm> bbtt = new ArrayList<>(keySet);
        Collections.sort(bbtt, new Comparator<Balanceterm>() {

            @Override
            public int compare(Balanceterm o1, Balanceterm o2) {
                return o1.getDatefrom().compareTo(o2.getDatefrom());
            }
        });
        for(Balanceterm bt : bbtt){
            Collection<AssociationMember> get = membersInTerm.get(bt);
            MembersPaiedInThisTerm mm = new MembersPaiedInThisTerm(bt, get);
            membersPaiedInThisTerms.add(mm);
        }
        return membersPaiedInThisTerms;
    }

    public BigDecimal getAccount() {
        return em.createNamedQuery(Balance.ACCOUNT, BigDecimal.class)
                .getSingleResult();
    }
    
    public BigDecimal getKasa() {
    	BigDecimal accountBalance = getAccountBalance();
    	BigDecimal account = getAccount();
    	
        return accountBalance.subtract(account);
    }

}
