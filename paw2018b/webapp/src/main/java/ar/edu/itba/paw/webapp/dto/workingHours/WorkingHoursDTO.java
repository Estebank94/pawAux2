package ar.edu.itba.paw.webapp.dto.workingHours;

import ar.edu.itba.paw.models.WorkingHours;

public class WorkingHoursDTO {
    private Integer dayOfWeek;
    private String startTime;
    private String finishTime;

    public WorkingHoursDTO(){}

    public WorkingHoursDTO(WorkingHours wh) {
        this.dayOfWeek = wh.getDayOfWeek();
        this.startTime = wh.getStartTime();
        this.finishTime = wh.getFinishTime();
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
