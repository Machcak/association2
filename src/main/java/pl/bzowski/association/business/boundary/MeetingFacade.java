package pl.bzowski.association.business.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.bzowski.association.business.entity.Meeting;

/**
 *
 * @author Machcak
 */
@Stateless
public class MeetingFacade extends AbstractFacade<Meeting> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MeetingFacade() {
        super(Meeting.class);
    }

	public List<Meeting> findAllOrderByDateDesc() {
        return getEntityManager().createNamedQuery(Meeting.findAllOrderByDate, Meeting.class).getResultList();
	}

}
