package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.DescriptionDao;
import ar.edu.itba.paw.models.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class DescriptionDaoImpl implements DescriptionDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public DescriptionDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("information")
                .usingColumns("doctorID","certificate","languages","education")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Description createDescription(Integer doctorId, String certificate, Set<String> languages, String education){

        String certificateString = certificate;
        String languagesString = setToString(languages);
        String educationString = education;

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
        String languagesString = setToString(description.getLanguages());

        final Map<String,Object> entry = new HashMap<>();
        entry.put("certificate",description.getCertificate());
        entry.put("languages",languagesString);
        entry.put("education",description.getEducation());
        entry.put("doctorId",doctorId);

        final Number descriptionId = jdbcInsert.execute(entry);
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
