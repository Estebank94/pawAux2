package ar.edu.itba.paw.webapp.forms;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentForm {

    LocalDate day;
    LocalTime time;

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
