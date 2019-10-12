package ar.edu.itba.paw.webapp.dto;

import java.util.List;

public class FutureDayListDTO {
    private List<String> futureDays;

    public FutureDayListDTO(List<String> futureDays) {
        this.futureDays = futureDays;
    }

    public FutureDayListDTO(){}

    public List<String> getFutureDays() {
        return futureDays;
    }

    public void setFutureDays(List<String> futureDays) {
        this.futureDays = futureDays;
    }
}
