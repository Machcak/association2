package pl.bzowski.association.presentation;

import javax.ejb.Stateless;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.boundary.AssociationMemberFacade;
import pl.bzowski.association.business.entity.AssociationMember;

/**
 *
 * @author Machcak
 */
@FacesConverter("member")
public class MemberPicklistConverter implements Converter{
    
    @Inject
    AssociationMemberFacade f;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return f.find(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((AssociationMember)value).getId().toString();
    }

}
