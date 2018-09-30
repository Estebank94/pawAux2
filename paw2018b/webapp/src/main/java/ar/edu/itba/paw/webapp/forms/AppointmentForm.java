package ar.edu.itba.paw.webapp.forms;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentForm {

    String day;
    String time;

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
