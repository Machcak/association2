package pl.bzowski.association.business.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.Resolution;

/**
 *
 * @author Machcak
 */
@Stateless
public class ResolutionFacade extends AbstractFacade<Resolution> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResolutionFacade() {
        super(Resolution.class);
    }

}
