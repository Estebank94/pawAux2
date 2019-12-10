package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.WorkingHours;
import sun.security.acl.WorldGroupImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AvailableDaysDTO {
    List<AvailableTimesDTO> availability;

    public AvailableDaysDTO(){}

    public AvailableDaysDTO (List<WorkingHours> wh, List<Appointment> future) {
        Map<String,List<String>> map = getAvailabilityMap(wh, future);
        this.availability = new ArrayList<>();
        for (String ld : map.keySet()){
            this.availability.add(new AvailableTimesDTO(ld, map.get(ld)));
        }
    }

    private Map<String, List<String>> getAvailabilityMap(List<WorkingHours> wh, List<Appointment> future) {
        Map<String, List<String>> map = new HashMap<>();
        if (wh == null || wh.size() == 0){
            return map;
        }
        LocalDate now = LocalDate.now();

        for (int i = 0; i < 7; i++){

            LocalDate day = now.plusDays(1L);
            int dayWeek = day.getDayOfWeek().getValue();
            List<WorkingHours> whList = getWorkingHoursByDayWeek(wh, dayWeek);
            if (whList.size() > 0){
                map.put(day.toString(), getTimesFromWorkingHoursList(getWorkingHoursByDayWeek(wh, dayWeek)));
            }
        }

        for (Appointment app : future){
            String day = app.getAppointmentDay();
            String time = app.getAppointmentTime();
            List<String> times = map.get(day);
            times.remove(time);
        }
        return map;
    }

    private List<String> getTimesFromWorkingHoursList (List<WorkingHours> dayWh){
        List<String> times = new ArrayList<>();
        for (WorkingHours wh : dayWh){
            LocalTime startDay = LocalTime.parse(wh.getStartTime());
            LocalTime finishTime = LocalTime.parse(wh.getFinishTime());
            while (startDay.isBefore(finishTime)){
                times.add(startDay.toString());
                startDay.plusMinutes(30);
            }
        }
        Collections.sort(times, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return times;
    }

    private List<WorkingHours> getWorkingHoursByDayWeek(List<WorkingHours> wh,int dayWeek){
        List<WorkingHours> list = new ArrayList<>();
        for (WorkingHours h : wh){
            if (h.getDayOfWeek() == dayWeek){
                list.add(h);
            }
        }
        return list;
    }

    public List<AvailableTimesDTO> getAvailability() {
        return availability;
    }

    public void setAvailability(List<AvailableTimesDTO> availability) {
        this.availability = availability;
    }
}
