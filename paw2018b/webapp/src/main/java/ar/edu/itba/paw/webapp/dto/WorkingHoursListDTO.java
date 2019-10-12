package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.WorkingHours;

import java.util.LinkedList;
import java.util.List;

public class WorkingHoursListDTO {
    private List<WorkingHoursDTO> workingHoursDTOList;

    public WorkingHoursListDTO(){}

    public WorkingHoursListDTO(List<WorkingHours> workingHours){
        this.workingHoursDTOList = new LinkedList<>();
        for (WorkingHours wh: workingHours){
            this.workingHoursDTOList.add(new WorkingHoursDTO(wh));
        }
    }

    public List<WorkingHoursDTO> getWorkingHoursDTOList() {
        return workingHoursDTOList;
    }

    public void setWorkingHoursDTOList(List<WorkingHoursDTO> workingHoursDTOList) {
        this.workingHoursDTOList = workingHoursDTOList;
    }
}
