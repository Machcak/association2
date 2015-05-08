package pl.bzowski.association.presentation;

import pl.bzowski.association.business.entity.Leadership;
import pl.bzowski.association.presentation.util.JsfUtil;
import pl.bzowski.association.presentation.util.JsfUtil.PersistAction;
import pl.bzowski.association.business.boundary.LeadershipFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;
import pl.bzowski.association.business.boundary.LeadershipTypeFacade;
import pl.bzowski.association.business.entity.AssociationMember;
import pl.bzowski.association.business.entity.LeadershipType;
import pl.bzowski.association.business.boundary.MemberAdder;


@Named("leadershipController")
@SessionScoped
public class LeadershipController implements Serializable {


    @Inject 
    private LeadershipFacade ejbFacade;
    
    @Inject 
    private LeadershipTypeFacade leadershipTypeFacade;
    
    @Inject
    private MemberAdder ma;
    
    private List<Leadership> items = null;
    private Leadership selected;
    
    private DualListModel<AssociationMember> leadershipMembers = new DualListModel<>(new ArrayList<AssociationMember>(), new ArrayList<AssociationMember>());    
    
    private List<LeadershipType> leadershipTypes;
    
    @PostConstruct
    public void init(){
        leadershipTypes = leadershipTypeFacade.findAll();
    }
    
    public LeadershipController() {
    }

    public Leadership getSelected() {
        return selected;
    }

    public void setSelected(Leadership selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LeadershipFacade getFacade() {
        return ejbFacade;
    }

    public Leadership prepareCreate() {
        selected = new Leadership();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/langs/i18n").getString("created"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/langs/i18n").getString("updated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/langs/i18n").getString("deleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Leadership> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/langs/i18n").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/langs/i18n").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Leadership getLeadership(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Leadership> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Leadership> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass=Leadership.class)
    public static class LeadershipControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LeadershipController controller = (LeadershipController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "leadershipController");
            return controller.getLeadership(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Leadership) {
                Leadership o = (Leadership) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Leadership.class.getName()});
                return null;
            }
        }
    }
    
    public DualListModel<AssociationMember> getLeadershipMembers(){
        return leadershipMembers;
    }

    public void setLeadershipMembers(DualListModel<AssociationMember> leadershipMembers) {
        this.leadershipMembers = leadershipMembers;
    }
    
    public void prepareAddMember(){
        leadershipMembers = ma.prepareAddMember(selected);
    }
    
    public void saveMembers(){
        List<AssociationMember> source = leadershipMembers.getSource();
        List<AssociationMember> target = leadershipMembers.getTarget();
        ma.saveLeadershipMembers(selected, source, target);
    }

    public List<LeadershipType> getLeadershipTypes() {
        return leadershipTypes;
    }
    
}