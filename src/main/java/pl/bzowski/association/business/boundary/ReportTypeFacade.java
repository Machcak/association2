package pl.bzowski.association.business.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.ReportType;

/**
 *
 * @author Machcak
 */
@Stateless
public class ReportTypeFacade extends AbstractFacade<ReportType> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReportTypeFacade() {
        super(ReportType.class);
    }

}
