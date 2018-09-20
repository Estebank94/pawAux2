package ar.edu.itba.paw.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class WorkingHours {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime finishTime;

    public WorkingHours(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime finishTime ) {
        this.dayOfWeek = dayOfWeek;
        this.finishTime = finishTime;
        this.startTime = startTime;
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
