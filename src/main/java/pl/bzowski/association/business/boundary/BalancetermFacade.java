package pl.bzowski.association.business.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.Balanceterm;

/**
 *
 * @author Machcak
 */
@Stateless
public class BalancetermFacade extends AbstractFacade<Balanceterm> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BalancetermFacade() {
        super(Balanceterm.class);
    }

}
