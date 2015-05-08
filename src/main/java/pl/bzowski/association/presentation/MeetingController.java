package pl.bzowski.association.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.model.DualListModel;

import pl.bzowski.association.business.boundary.AssociationMemberFacade;
import pl.bzowski.association.business.boundary.LeadershipFacade;
import pl.bzowski.association.business.boundary.MeetingFacade;
import pl.bzowski.association.business.boundary.MemberAdder;
import pl.bzowski.association.business.boundary.ReportFacade;
import pl.bzowski.association.business.entity.AssociationMember;
import pl.bzowski.association.business.entity.Leadership;
import pl.bzowski.association.business.entity.Meeting;
import pl.bzowski.association.business.entity.Report;
import pl.bzowski.association.presentation.util.JsfUtil;
import pl.bzowski.association.presentation.util.JsfUtil.PersistAction;

@Named("meetingController")
@ViewScoped
public class MeetingController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3366743965821147604L;

	@Inject
    private MeetingFacade meetingFacade;

    @Inject
    private MemberAdder ma;

    @Inject
    private ReportFacade reportFacade;
    
    @Inject 
    private AssociationMemberFacade associationMemberFacade;
        
    @Inject
    private LeadershipFacade leadershipFacade;

    private List<Meeting> items = null;
    private Meeting selected;
    private DualListModel<AssociationMember> meetingMembers = new DualListModel<>(new ArrayList<AssociationMember>(), new ArrayList<AssociationMember>());
    private Report report = new Report();
    private List<Leadership> leaderships;

    public MeetingController() {
        
    }
    
    @PostConstruct
    public void init(){
        leaderships = leadershipFacade.findAll();
    }

    public Meeting getSelected() {
        return selected;
    }

    public void setSelected(Meeting selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MeetingFacade getFacade() {
        return meetingFacade;
    }

    public Meeting prepareCreate() {
        selected = new Meeting();
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

    public List<Meeting> getItems() {
        if (items == null) {
            items = getFacade().findAllOrderByDateDesc();
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

    public Meeting getMeeting(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Meeting> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Meeting> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void prepareAddMember() {
        meetingMembers = ma.prepareAddMeeatingMember((MemberAdder.HaveingId) selected);
    }

    public void prepareReport() {
        report = reportFacade.findReportForMeeting(selected);
    }

    public void saveMembers() {
        List<AssociationMember> source = meetingMembers.getSource();
        List<AssociationMember> target = meetingMembers.getTarget();
        ma.saveMeetingMembers(selected, source, target);
    }

    public DualListModel<AssociationMember> getMeetingMembers() {
        return meetingMembers;
    }

    public void setMeetingMembers(DualListModel<AssociationMember> meetingMembers) {
        this.meetingMembers = meetingMembers;
    }

    public String getMeetingReportContent() {
        return report.getReport();
    }

    public void setMeetingReportContent(String content) {
        this.report.setReport(content);
    }

    public void saveListener() {
        report.setMeeting(selected);
        report.setDateOf(selected.getDayOf());
        report.setTitle(selected.getNumber());
        JsfUtil.persist(PersistAction.CREATE, "Udało się zapisać sprawozdanie", reportFacade, report);
    }
    
    public void changeMember(javax.faces.event.AjaxBehaviorEvent e){
    	SelectOneMenu selectOneMenu =(SelectOneMenu )e.getSource();
        AssociationMember newValue = (AssociationMember) selectOneMenu.getValue();
        report.setAssociationMember(newValue);
    }
    
    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    
    public List<AssociationMember> getAssociationMembers() {
        return associationMemberFacade.findAll();
    }

    public List<Leadership> getLeaderships() {
        return leaderships;
    }

    public void setLeaderships(List<Leadership> leaderships) {
        this.leaderships = leaderships;
    }
    
    

    @FacesConverter(forClass = Meeting.class)
    public static class MeetingControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MeetingController controller = (MeetingController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "meetingController");
            return controller.getMeeting(getKey(value));
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
            if (object instanceof Meeting) {
                Meeting o = (Meeting) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Meeting.class.getName()});
                return null;
            }
        }

    }

}
