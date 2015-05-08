package pl.bzowski.association.business.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.LeadershipType;

/**
 *
 * @author Machcak
 */
@Stateless
public class LeadershipTypeFacade extends AbstractFacade<LeadershipType> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LeadershipTypeFacade() {
        super(LeadershipType.class);
    }

}
