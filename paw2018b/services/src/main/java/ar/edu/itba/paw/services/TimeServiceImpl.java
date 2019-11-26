package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.TimeService;
import ar.edu.itba.paw.models.WorkingHours;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeServiceImpl implements TimeService {
    @Override
    public List<String> getAppointmentsPossibleTimes() {

        List<String> list = new ArrayList<>();
        int minutes_to_add = WorkingHours.APPOINTMENTTIME_TIME;

        LocalTime prevTime = LocalTime.MIN;
        LocalTime time = prevTime.plusMinutes(minutes_to_add);
        list.add(time.toString());
        list.add(time.toString());
        while (time.isAfter(prevTime)){
            time = prevTime;
            time.plusMinutes(minutes_to_add);
            list.add(time.toString());
        }
        return list;
    }
}
