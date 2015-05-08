package pl.bzowski.association.presentation;

import java.util.Collection;
import pl.bzowski.association.business.entity.AssociationMember;
import pl.bzowski.association.business.entity.Balanceterm;

/**
 *
 * @author Machcak
 */
public class MembersPaiedInThisTerm {

    private final Balanceterm balanceterm;
    
    private final String members;

    public MembersPaiedInThisTerm(Balanceterm balanceterm, Collection<AssociationMember> associationMembers) {
        this.balanceterm = balanceterm;
        StringBuilder sb = new StringBuilder();
        for(AssociationMember am : associationMembers){
            sb.append(am.getFirstName());
            sb.append(" ");
            sb.append(am.getLastName());
            sb.append(", ");
        }
        this.members = sb.toString();
    }
    
    public Balanceterm getBalanceterm() {
        return balanceterm;
    }

    public String getMembers() {
        return members;
    }
    
    
}
