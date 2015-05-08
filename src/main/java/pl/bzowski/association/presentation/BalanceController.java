package pl.bzowski.association.presentation;

import pl.bzowski.association.business.entity.Balance;
import pl.bzowski.association.presentation.util.JsfUtil;
import pl.bzowski.association.presentation.util.JsfUtil.PersistAction;
import pl.bzowski.association.business.boundary.BalanceFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;

@Named("balanceController")
@ViewScoped
public class BalanceController implements Serializable {

    @EJB
    private pl.bzowski.association.business.boundary.BalanceFacade ejbFacade;
    private List<Balance> items = null;
    private Balance selected;

    public BalanceController() {
    }

    public Balance getSelected() {
        return selected;
    }

    public void setSelected(Balance selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BalanceFacade getFacade() {
        return ejbFacade;
    }

    public Balance prepareCreate() {
        selected = new Balance();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/langs/i18n").getString("BalanceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/langs/i18n").getString("BalanceUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/langs/i18n").getString("BalanceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Balance> getItems() {
        if (items == null) {
            items = getFacade().findAllOrderByIncomingdateDesc();
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

    public Balance getBalance(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Balance> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Balance> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public BigDecimal getAccountBalance(){
        return getFacade().getAccountBalance();
    }
    
    public BigDecimal getAccount(){
        return getFacade().getAccount();
    }
    
    public BigDecimal getKasa(){
        return getFacade().getKasa();
    }
    
    

    @FacesConverter(forClass = Balance.class)
    public static class BalanceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BalanceController controller = (BalanceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "balanceController");
            return controller.getBalance(getKey(value));
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
            if (object instanceof Balance) {
                Balance o = (Balance) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Balance.class.getName()});
                return null;
            }
        }

    }

}
