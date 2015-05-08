package pl.bzowski.association.business.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.AssociationMember;
import pl.bzowski.association.business.entity.Leadership;
import pl.bzowski.association.business.entity.LeadershipMember;
import pl.bzowski.association.business.entity.Meeting;
import pl.bzowski.association.business.entity.MeetingMember;

/**
 *
 * @author Machcak
 */
@Stateless
public class AssociationMemberFacade extends AbstractFacade<AssociationMember> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;
    
    @Inject
    private LeadershipMemberFacade leadershipMemberFacade;
    
    @Inject
    private MeetingMemberFacade meetingMemberFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AssociationMemberFacade() {
        super(AssociationMember.class);
    }

    public List<AssociationMember> findAllMembersOfLeadership(Long leadershipId) {
        return getEntityManager()
                .createNamedQuery(AssociationMember.findAllMembersOfLeadership, AssociationMember.class)
                .setParameter("leadershipId", leadershipId).getResultList();//TODO: Czy to jest napewno dobra fasada? Może lepiej Dodać do fasady leadershipmembersfacade
    }

    public void saveLeadershipMembers(Leadership leadership, List<AssociationMember> source, List<AssociationMember> target) {
        getEntityManager()
                .createQuery("DELETE FROM LeadershipMember lm WHERE lm.leadership = :leadership")
                .setParameter("leadership", leadership)
                .executeUpdate();
        for(AssociationMember t : target){
            LeadershipMember lm = new LeadershipMember(leadership, t);
            leadershipMemberFacade.create(lm);
        }
    }

    public void saveMeetingMembers(Meeting meeting, List<AssociationMember> source, List<AssociationMember> target) {
                getEntityManager()
                .createQuery("DELETE FROM MeetingMember mm WHERE mm.meeting = :meeting")
                .setParameter("meeting", meeting)
                .executeUpdate();
        for(AssociationMember t : target){
            MeetingMember lm = new MeetingMember(meeting, t);
            meetingMemberFacade.create(lm);
        }
    }

}
