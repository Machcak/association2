package pl.bzowski.association.business.boundary;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import pl.bzowski.association.business.entity.AssociationMember;
import pl.bzowski.association.business.entity.Meeting;
import pl.bzowski.association.business.entity.Report;

/**
 *
 * @author Machcak
 */
@Stateless
public class ReportFacade extends AbstractFacade<Report> {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReportFacade() {
        super(Report.class);
    }

    public Report findReportForMeeting(Meeting selected) {
        try{
        Report report = em.createNamedQuery(Report.findReportForMeeting, Report.class)
                .setParameter("meeting", selected)
                .getSingleResult();
        return report;
        }catch(NoResultException ex){
            Report r = new Report();
//            r.setDateOf(new Date());
//            r.setMeeting(selected);
//            r.setTitle("TYTUL");
            r.setReport("TREŚĆ");
//            AssociationMember merge = em.merge(new AssociationMember(1L));
//            r.setAssociationMember(merge);
//            em.persist(r);
            return r;
        }
    }

}
