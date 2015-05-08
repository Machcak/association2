package pl.bzowski.association.business.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.Balanceevent;

/**
 *
 * @author Machcak
 */
@Stateless
public class BalanceeventFacade extends AbstractFacade<Balanceevent> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BalanceeventFacade() {
        super(Balanceevent.class);
    }

}
