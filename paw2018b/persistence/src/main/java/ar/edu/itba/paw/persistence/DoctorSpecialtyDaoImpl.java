package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.DoctorSpecialtyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DoctorSpecialtyDaoImpl implements DoctorSpecialtyDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public DoctorSpecialtyDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("doctorSpecialty")
                .usingColumns("doctorID","specialtyID");
    }

    @Override
    public void addDoctorSpecialty(Integer doctorId, Integer specialtyId) {
        final Map<String,Object> entry = new HashMap<>();
        entry.put("doctorID",doctorId);
        entry.put("specialtyID",specialtyId);
        jdbcInsert.executeAndReturnKey(entry);
    }

    @Override
    public void addDoctorSpecialtyList(Integer doctorId, List<Integer> specialtyId){
        for(Integer idIterator: specialtyId){
            addDoctorSpecialty(doctorId,idIterator);
        }
    }
}