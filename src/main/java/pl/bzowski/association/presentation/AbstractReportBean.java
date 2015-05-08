package pl.bzowski.association.presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 





















import pl.bzowski.association.business.boundary.Database;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.SimpleFileResolver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
 












import net.sf.jasperreports.view.JasperViewer;

import com.jasper.ReportConfigUtil;

 
public abstract class AbstractReportBean {
 
    public enum ExportOption {
 
        PDF, HTML, EXCEL, RTF
    }
    private ExportOption exportOption;
    private final String COMPILE_DIR = "/resources/reports/";
    //private String compileFileName = "productlist";//name of your compiled report file
    private String message;
 
    public AbstractReportBean() {
        super();
        setExportOption(ExportOption.PDF);
    }
    
    public static Connection connectToDatabase(String databaseName, String userName, String password) {
    		    Connection connection = null;
    		    try {
    		      Class.forName("com.mysql.jdbc.Driver");
    		      connection = DriverManager.getConnection(databaseName,userName,password);
    		    } catch(Exception e) {
    		      String text = "Could not connect to the database: " + e.getMessage() + " "
    		 + e.getLocalizedMessage();
    		      System.out.println(text);
    		    }
    		    return connection;
    		  }
    
    
    protected void prepareReport() throws JRException, IOException{
    	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    	ServletContext context = (ServletContext) externalContext.getContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
    	
    	String reportsDirPath = context.getRealPath(getCompileDir());
    	File reportsDir = new File(reportsDirPath);
    	if (!reportsDir.exists()) {
    	    throw new FileNotFoundException(String.valueOf(reportsDir));
    	}
    	
    	kompilujWszystkieraporty(context);
    	Connection conn = null;
    	try {
    		conn = Database.getConnection();
//    		conn = AbstractReportBean.connectToDatabase("http://localhost:3306/association", "root", "");
         } catch (Exception ex) {
             ex.printStackTrace();
         }
    	 System.out.println("Done with connectToDatabase!");
    	 String reportFile = ReportConfigUtil.getJasperFilePath(context, getCompileDir(), getCompileFileName() + ".jrxml");
    	 JasperDesign jasperDesign = JRXmlLoader.load(reportFile);
    	 System.out.println("Done with load!");
    	 JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    	System.out.println("Done with compileReport!");
    	Map<String, Object> reportParameters = getReportParameters();
    	reportParameters.put(JRParameter.REPORT_FILE_RESOLVER, new SimpleFileResolver(reportsDir));
    	System.out.println();
    	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters, conn);
//    	System.out.println("Done with fillReport!");
//    	JRPdfExporter export = new JRPdfExporter();
//    	export.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
//    	export.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,"output.pdf");
//    	System.out.println("Done with setJasperPrint!");
//    	export.exportReport();
//    	System.out.println("Done with exportReport!");
    	
        if (getExportOption().equals(ExportOption.HTML)) {
            ReportConfigUtil.exportReportAsHtml(jasperPrint, response.getWriter());
        } else if (getExportOption().equals(ExportOption.EXCEL)) {
            ReportConfigUtil.exportReportAsExcel(jasperPrint, response.getWriter());
        } else {
            request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            response.sendRedirect(request.getContextPath() + "/servlets/report/" + getExportOption());
        }
 
        FacesContext.getCurrentInstance().responseComplete();
    }

	private void kompilujWszystkieraporty(ServletContext context) throws JRException {
		for(File file : podajWszystkieNazwyRaportow( context )){
			String name = file.getName();
			if(name.contains("jrxml")){
				ReportConfigUtil.compileReport(context, getCompileDir(), name.substring(0, name.indexOf('.')));
			}
		}
	}
    
    private File[] podajWszystkieNazwyRaportow(ServletContext context ) {
    	String compileDir = getCompileDir();
    	File folder = new File(context.getRealPath(compileDir));
    	File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}

	protected void prepareReport0() throws JRException, IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
 
        ServletContext context = (ServletContext) externalContext.getContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
 
        kompilujWszystkieraporty(context);
 
        File reportFile = new File(ReportConfigUtil.getJasperFilePath(context, getCompileDir(), getCompileFileName() + ".jasper"));
 
        ///////////////////
        Connection conn = null;
        try {
            conn = Database.getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
 
        /////////////////////
 
        JasperPrint jasperPrint = ReportConfigUtil.fillReport(reportFile, getReportParameters(), conn);
        
 
        if (getExportOption().equals(ExportOption.HTML)) {
            ReportConfigUtil.exportReportAsHtml(jasperPrint, response.getWriter());
        } else if (getExportOption().equals(ExportOption.EXCEL)) {
            ReportConfigUtil.exportReportAsExcel(jasperPrint, response.getWriter());
        } else {
            request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            response.sendRedirect(request.getContextPath() + "/servlets/report/" + getExportOption());
        }
 
        FacesContext.getCurrentInstance().responseComplete();
    }
 
    public ExportOption getExportOption() {
        return exportOption;
    }
 
    public void setExportOption(ExportOption exportOption) {
        this.exportOption = exportOption;
    }
 
    protected Map<String, Object> getReportParameters() {
        return new HashMap<String, Object>();
    }
 
    protected String getCompileDir() {
        return COMPILE_DIR;
    }
 
    protected abstract String getCompileFileName();
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
}
