package pl.bzowski.association.business.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.Membershiphistory;

/**
 *
 * @author Machcak
 */
@Stateless
public class MembershiphistoryFacade extends AbstractFacade<Membershiphistory> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MembershiphistoryFacade() {
        super(Membershiphistory.class);
    }

}
