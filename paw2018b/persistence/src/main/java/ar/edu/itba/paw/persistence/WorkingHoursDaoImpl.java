package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.WorkingHoursDao;
import ar.edu.itba.paw.models.WorkingHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkingHoursDaoImpl implements WorkingHoursDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public WorkingHoursDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("workinghours")
                .usingColumns("doctorId","dayweek","starttime","finishtime")
        .usingGeneratedKeyColumns("id");
    }


    @Override
    public void addWorkingHour(Integer doctorId, WorkingHours workingHours) {
        final Map<String,Object> entry = new HashMap<>();
        entry.put("doctorId",doctorId);
        entry.put("dayweek",workingHours.getDayOfWeek().toString());
        entry.put("starttime",workingHours.getStartTime().toString());
        entry.put("finishtime",workingHours.getFinishTime().toString());

        jdbcInsert.execute(entry);
    }

    @Override
    public void addWorkingHour(Integer doctorId, List<WorkingHours> workingHours) {
        for (WorkingHours workingHoursIterator: workingHours){
            addWorkingHour(doctorId, workingHoursIterator);
        }
    }
}