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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dayweek")
    private Integer dayOfWeek;
    private String startTime;
    private String finishTime;

    @ManyToOne
    @JoinColumn(name = "doctorid")
    private Doctor doctor;

    public WorkingHours(){

    }

    public WorkingHours(Integer dayOfWeek, String startTime, String finishTime, Doctor doctor) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.doctor = doctor;
    }

    public WorkingHours(Integer dayOfWeek, String startTime, String finishTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.finishTime = finishTime;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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