package ar.edu.itba.paw.webapp.dto.appointment;

import javax.ws.rs.core.NioWriterHandler;
import java.util.ArrayList;
import java.util.List;

public class BasicAppointmentListDTO {
    private List<BasicAppointmentDTO> futures;

    public BasicAppointmentListDTO(List<BasicAppointmentDTO> retList) {
        this.futures = new ArrayList<>();
        this.futures.addAll(retList);
    }

    public BasicAppointmentListDTO () {

    }

    public List<BasicAppointmentDTO> getFutures() {
        return futures;
    }

    public void setFutures(List<BasicAppointmentDTO> futures) {
        this.futures = futures;
    }
}
