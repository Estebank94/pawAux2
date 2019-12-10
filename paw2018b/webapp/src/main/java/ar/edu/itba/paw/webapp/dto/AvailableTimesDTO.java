package ar.edu.itba.paw.webapp.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvailableTimesDTO {
    String day;
    List<String> times;

    public AvailableTimesDTO(){
    }

    public AvailableTimesDTO (LocalDate day, List<LocalTime> times){
        this.day = day.toString();

        this.times = new ArrayList<>();
        for (LocalTime lt : times){
            this.times.add(lt.toString());
        }
    }

    public AvailableTimesDTO(String day, List<String> times){
        this.day = day;
        this.times = new ArrayList<>();
        this.times.addAll(times);
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}
