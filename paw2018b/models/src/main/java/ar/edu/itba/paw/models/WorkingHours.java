package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name="workingHour")
public class WorkingHours{
    public static final int APPOINTMENTTIME_TIME = 30;
    @Id
    private Integer id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime finishTime;
    @ManyToOne
    private Doctor doctor;

    public WorkingHours(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime finishTime) {
        this.dayOfWeek = dayOfWeek;
        this.finishTime = finishTime;
        this.startTime = startTime;
    }

    public WorkingHours(){

    }

    public static int getAppointmenttimeTime() {
        return APPOINTMENTTIME_TIME;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingHours that = (WorkingHours) o;
        return dayOfWeek == that.dayOfWeek &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(finishTime, that.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, startTime, finishTime);
    }

}