package pl.bzowski.association.business.boundary;

import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;
import pl.bzowski.association.business.entity.AssociationMember;
import pl.bzowski.association.business.entity.Leadership;
import pl.bzowski.association.business.entity.Meeting;
import pl.bzowski.association.business.entity.MeetingMember;

/**
 *
 * @author Machcak
 */
@Stateless
public class MemberAdder {
    
    public interface HaveingId {
        Long getId();
    }
    
    @Inject 
    private AssociationMemberFacade associationMemberFacade;
    
    @Inject
    private MeetingMemberFacade meetingMemberFacade;

    public MemberAdder() {
    }
    
    public DualListModel<AssociationMember> prepareAddMember(HaveingId selected) {
        List<AssociationMember> source = associationMemberFacade.findAll();
        Long leadershipId = selected.getId();
        List<AssociationMember> target = associationMemberFacade.findAllMembersOfLeadership(leadershipId);
        wywalZSourceZapisyZTarget(source, target);
        return new DualListModel<>(source, target);
    }
    
    public DualListModel<AssociationMember> prepareAddMeeatingMember(HaveingId selected) {
        List<AssociationMember> source = associationMemberFacade.findAll();
        Long meetingId = selected.getId();
        List<AssociationMember> target = meetingMemberFacade.findAllMembersOfMeeting(meetingId);
        wywalZSourceZapisyZTarget(source, target);
        return new DualListModel<>(source, target);
    }
    
    private void wywalZSourceZapisyZTarget(List<? extends HaveingId> source, List<? extends HaveingId> target) {
        Iterator<? extends HaveingId> iterator = source.iterator();
        while (iterator.hasNext()) {
            HaveingId next = iterator.next();
            boolean contains = target.contains(next);
            if(contains){
                iterator.remove();
            }
        }
    }

    public void saveLeadershipMembers(Leadership selected, List<AssociationMember> source, List<AssociationMember> target) {
        associationMemberFacade.saveLeadershipMembers(selected, source, target);
    }
    
    public void saveMeetingMembers(Meeting selected, List<AssociationMember> source, List<AssociationMember> target) {
        associationMemberFacade.saveMeetingMembers(selected, source, target);
    }

}
