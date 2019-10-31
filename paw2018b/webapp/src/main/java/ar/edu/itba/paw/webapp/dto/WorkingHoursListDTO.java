package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.WorkingHours;

import java.util.LinkedList;
import java.util.List;

public class WorkingHoursListDTO {
    private List<WorkingHoursDTO> workingHours;

    public WorkingHoursListDTO(){}

    public WorkingHoursListDTO(List<WorkingHours> workingHours){
        this.workingHours = new LinkedList<>();
        for (WorkingHours wh: workingHours){
            this.workingHours.add(new WorkingHoursDTO(wh));
        }
    }

    public List<WorkingHoursDTO> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHoursDTO> workingHours) {
        this.workingHours = workingHours;
    }
}
