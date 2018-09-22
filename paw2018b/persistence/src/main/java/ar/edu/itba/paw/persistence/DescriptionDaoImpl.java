package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.DescriptionDao;
import ar.edu.itba.paw.models.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DescriptionDaoImpl implements DescriptionDao {
    private SimpleJdbcInsert jdbcInsert;


    @Autowired
    public DescriptionDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("information")
                .usingColumns("doctorID","certificate","languages","education");
    }

    @Override
    public Description createDescription(Integer doctorId, Set<String> certificate, Set<String> languages, Set<String> education){

        String certificateString = setToString(certificate);
        String languagesString = setToString(certificate);
        String educationString = setToString(certificate);

        final Map<String,Object> entry = new HashMap<>();
        entry.put("certificate",certificateString);
        entry.put("languages",languagesString);
        entry.put("education",educationString);
        entry.put("doctorId",doctorId);

        Description description = new Description(certificate,languages,education);

        final Number descriptionId = jdbcInsert.executeAndReturnKey(entry);
        description.setId(new Integer(descriptionId.intValue()));
        return description;
    }

    @Override
    public void addDescription(Integer doctorId, Description description) {
        String certificateString = setToString(description.getCertificate());
        String languagesString = setToString(description.getLanguages());
        String educationString = setToString(description.getEducation());

        final Map<String,Object> entry = new HashMap<>();
        entry.put("certificate",certificateString);
        entry.put("languages",languagesString);
        entry.put("education",educationString);
        entry.put("doctorId",doctorId);

        final Number descriptionId = jdbcInsert.executeAndReturnKey(entry);
        return;
    }

    private String setToString(Set<String> setToString){
        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();

        for (String stringIterator:setToString){
            if (isFirst){
                isFirst = false;
            } else {
                sb.append(",");
            }
            sb.append(stringIterator);
        }
        return sb.toString();
    }

}
