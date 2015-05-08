package pl.bzowski.association.presentation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import java.io.Serializable;
 
@Named
@SessionScoped
public class ReportsBean extends AbstractReportBean implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 5960378929459855033L;
	
	
	private Date datefrom;
    
    private Date dateto;
    
    private Integer balanceterm = 0;
    
    private String value;  
    
 
    @Override
    protected String getCompileFileName() {
        return value;
    }
 
    @Override
    protected Map<String, Object> getReportParameters() {
        Map<String, Object> reportParameters = new HashMap<String, Object>();
        if(datefrom != null){
    		reportParameters.put("datefrom", datefrom.clone());
    	}

    	if(dateto != null){
    		reportParameters.put("dateto",  dateto.clone());
    	}
    	if(balanceterm != null){
    		reportParameters.put("balanceterm", balanceterm.longValue());
    	}
 
        return reportParameters;
    }
 
    public String execute() {
        try {
            super.prepareReport();
        } catch (Exception e) {
            // make your own exception handling
            e.printStackTrace();
        }
 
        return null;
    }
    
    public void setDatefrom(Date datefrom){
    	this.datefrom = datefrom;
    }
    
    public Date getDatefrom(){
    	return datefrom;
    }
    
    public Date getDateto(){
    	return dateto;
    }
    
    public void setDateto(Date dateto){
    	this.dateto = dateto;
    }
    
    public Integer getBalanceterm(){
    	return balanceterm;
    }
    
    public void setBalanceterm(Integer balanceterm){
    	this.balanceterm = balanceterm;
    }
    
    public String getValue() {  
        return value;  
    }  
  
    public void setValue(final String value) {  
        this.value = value;  
    }
}
