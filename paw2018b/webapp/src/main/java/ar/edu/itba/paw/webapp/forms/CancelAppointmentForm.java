package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;

public class CancelAppointmentForm {

    private Integer doctorid;
    private String day;
    private String time;

    public Integer getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(Integer doctorid) {
        this.doctorid = doctorid;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
