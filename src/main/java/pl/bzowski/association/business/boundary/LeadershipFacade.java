package pl.bzowski.association.business.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.Leadership;

/**
 *
 * @author Machcak
 */
@Stateless
public class LeadershipFacade extends AbstractFacade<Leadership> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LeadershipFacade() {
        super(Leadership.class);
    }

}
