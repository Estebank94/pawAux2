package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AppointmentDao;
import ar.edu.itba.paw.models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class AppointmentDaoImpl implements AppointmentDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public AppointmentDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("apploiment")
                .usingColumns("","","","",
                        "","","","");
    }
    @Override
    public Optional<Appointment> createAppointment(Integer doctorId, LocalDate appointmentDay, LocalTime appointmentTime) {
        return Optional.empty();
    }
}
