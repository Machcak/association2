package pl.bzowski.association.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

/**
 *
 * @author Machcak
 */
@NamedQueries({
    @NamedQuery(name = Report.findReportForMeeting,
            query=" SELECT r FROM Report r WHERE r.meeting = :meeting ")
})
@Entity
@Table(name = "report")
@Audited
public class Report implements Serializable {
    
    public static final String findReportForMeeting = "Report.findReportForMeeting";
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private String title;
    
    @NotNull
    private String report;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateOf;
    
    @NotNull
    @ManyToOne
    private AssociationMember associationMember;
    
    
    
    @OneToOne
    private Meeting meeting;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Date getDateOf() {
        return dateOf;
    }

    public void setDateOf(Date dateOf) {
        this.dateOf = dateOf;
    }

    public void setAssociationMember(AssociationMember associationMember) {
        this.associationMember = associationMember;
    }

    public AssociationMember getAssociationMember() {
        return associationMember;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
    
}
