package pl.bzowski.association.business.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.Incometo;

/**
 *
 * @author Machcak
 */
@Stateless
public class IncometoFacade extends AbstractFacade<Incometo> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IncometoFacade() {
        super(Incometo.class);
    }

}
