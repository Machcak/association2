package pl.bzowski.association.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

/**
 *
 * @author Machcak
 */
@Entity
@Table(name = "resolution")
@Audited
public class Resolution implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4353882776504028287L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private String number;
    
    @NotNull
    private String content;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dayOfResolution;
    
    
    @ManyToOne
    @NotNull
    private Leadership leadership;
    
    
    @ManyToOne
    @NotNull
    private Meeting meeting;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDayOfResolution() {
        return dayOfResolution;
    }

    public void setDayOfResolution(Date dayOfResolution) {
        this.dayOfResolution = dayOfResolution;
    }

    public Leadership getLeadership() {
        return leadership;
    }

    public void setLeadership(Leadership leadership) {
        this.leadership = leadership;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
