package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.WorkingHoursDao;
import ar.edu.itba.paw.models.WorkingHours;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}