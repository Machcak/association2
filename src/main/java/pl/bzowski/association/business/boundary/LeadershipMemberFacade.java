package pl.bzowski.association.business.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.LeadershipMember;

/**
 *
 * @author Machcak
 */
@Stateless
public class LeadershipMemberFacade extends AbstractFacade<LeadershipMember> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LeadershipMemberFacade() {
        super(LeadershipMember.class);
    }

}
