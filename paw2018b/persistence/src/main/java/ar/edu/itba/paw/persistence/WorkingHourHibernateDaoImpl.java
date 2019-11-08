package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.WorkingHoursDao;
import ar.edu.itba.paw.models.WorkingHours;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;


@Repository
public class WorkingHourHibernateDaoImpl implements WorkingHoursDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addWorkingHour(WorkingHours workingHours) {
        em.persist(workingHours);
    }

    @Override
    public void addWorkingHour(List<WorkingHours> workingHours) {
        for (WorkingHours workingHoursIterator: workingHours){
            addWorkingHour(workingHoursIterator);
        }
    }

//    @Override
//    public List<WorkingHours> findWorkingHoursByDoctor(Doctor doctor){
//        final TypedQuery<WorkingHours> query = em.createQuery("FROM WorkingHours" +
//                "WHERE doctor = :doctor", WorkingHours.class);
//        query.setParameter("doctor", doctor);
//        List<WorkingHours> list = query.getResultList();
//        return list.isEmpty() ? Collections.emptyList() : list;
//    }

    @Override
    public List<WorkingHours> findWorkingHoursByDayWeek(Integer dayOfWeek){
        final TypedQuery<WorkingHours> query = em.createQuery("FROM WorkingHours WHERE dayweek = :dayweek", WorkingHours.class);
        query.setParameter("dayweek", dayOfWeek);
        List<WorkingHours> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }
}