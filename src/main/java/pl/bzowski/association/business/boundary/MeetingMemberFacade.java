package pl.bzowski.association.business.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.AssociationMember;
import pl.bzowski.association.business.entity.MeetingMember;

/**
 *
 * @author Machcak
 */
@Stateless
public class MeetingMemberFacade extends AbstractFacade<MeetingMember> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MeetingMemberFacade() {
        super(MeetingMember.class);
    }

    public List<AssociationMember> findAllMembersOfMeeting(Long meetingId) {
            return getEntityManager()
                .createNamedQuery(MeetingMember.findAllMembersOfMeeting, AssociationMember.class)
                .setParameter("meetingId", meetingId).getResultList();
    }

}
