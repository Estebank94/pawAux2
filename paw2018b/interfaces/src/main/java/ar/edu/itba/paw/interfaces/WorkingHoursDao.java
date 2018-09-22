package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.WorkingHours;

import java.util.List;

public interface WorkingHoursDao {
    void addWorkingHour(Integer doctorId, WorkingHours workingHours);

    void addWorkingHour(Integer doctorId, List<WorkingHours> workingHours);
}
