package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.MedicalCareDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MedicalCareDaoImpl implements MedicalCareDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public MedicalCareDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("medicalCare")
                .usingColumns("doctorID","insurancePlanID");
    }

    @Override
    public void addMedicalCare(Integer doctorId, Integer insurancePlanId){
        final Map<String,Object> entry = new HashMap<>();

        entry.put("doctorID",doctorId);
        entry.put("insurancePlanID",insurancePlanId);

        jdbcInsert.executeAndReturnKey(entry);
    }

    @Override
    public void addMedicalCare(Integer doctorId, List<Integer> insurancePlanId) {
        for (Integer insurancePlanIdIterator: insurancePlanId){
            addMedicalCare(doctorId,insurancePlanIdIterator);
        }
    }


}
