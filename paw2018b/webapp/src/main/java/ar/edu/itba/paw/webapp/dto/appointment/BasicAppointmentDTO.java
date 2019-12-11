package ar.edu.itba.paw.webapp.dto.appointment;

import ar.edu.itba.paw.models.Appointment;

import java.util.*;

public class BasicAppointmentDTO {
    private String day;
    private List<String> times;

    public BasicAppointmentDTO() {
    }

    public static Map<String, List<String>> toMap(List<Appointment> appointments){
        Map<String, List<String>> appMap = new HashMap<>();
        for (Appointment app : appointments){
            if (!appMap.containsKey(app.getAppointmentDay())){
                appMap.put(app.getAppointmentDay(), new ArrayList<>());
            }
            appMap.get(app.getAppointmentDay()).add(app.getAppointmentTime());
        }
        return  appMap;
    }

    public BasicAppointmentDTO(String appDay, List<String> appTimes){
        this.day = appDay;
        this.times = new ArrayList<>();
        for (String app : appTimes){
            this.times.add(app);
        }
        Collections.sort(this.times);
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
