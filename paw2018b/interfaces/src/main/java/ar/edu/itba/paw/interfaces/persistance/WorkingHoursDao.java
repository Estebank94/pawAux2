package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.WorkingHours;

import java.util.List;

public interface WorkingHoursDao {

    void addWorkingHour(WorkingHours workingHours);

    void addWorkingHour(List<WorkingHours> workingHours);

    List<WorkingHours> findWorkingHoursByDayWeek(Integer dayOfWeek);

}
