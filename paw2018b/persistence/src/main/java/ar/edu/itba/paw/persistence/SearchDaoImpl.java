package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.ListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by estebankramer on 02/09/2018.
 */

@Repository
public class SearchDaoImpl implements SearchDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<ListItem> ROW_MAPPER_INSURANCE = (rs, rowNum) -> new ListItem( rs.getString("insuranceName"),
            rs.getInt("id"));

    private static final RowMapper<ListItem> ROW_MAPPER_SPECIALTY = (rs, rowNum) -> new ListItem( rs.getString("specialtyName"),
            rs.getInt("id"));


//    RowMapper<ListItem>(){
//
//        @Override
//        public ListItem mapRow(ResultSet rs, int rowNum) throws SQLException {
//            return new ListItem( rs.getString("insuranceName"), rs.getInt("id"));
//        }
//    };

    @Autowired
    public SearchDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<List<ListItem>> listInsurances() {

        final List<ListItem> list = jdbcTemplate.query("SELECT * FROM insurance", ROW_MAPPER_INSURANCE);

        if(list.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<List<ListItem>> listInsurancesWithDoctors(){
        StringBuilder query = new StringBuilder();
        query.append("SELECT DISTINCT insuranceName ");
        query.append("FROM medicalCare ");
        query.append("JOIN insurancePlan ON medicalCare.insurancePlanId = insurancePlan.id " );
        query.append("JOIN insurance ON insurance.id = insurancePlan.insuranceId");
        final List<ListItem> list = jdbcTemplate.query(query.toString(),ROW_MAPPER_INSURANCE );

        if(list.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(list);
    }



    @Override
    public Optional<List<ListItem>> listZones() {
        return null;
    }

    @Override
    public Optional<List<ListItem>> listSpecialtiesWithDoctors() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT DISTINCT insuranceName ");
        query.append("FROM specialty ");
        query.append("JOIN doctorSpecialty ON doctorSpecialty.specialtyId = specialty.id;");

        final List<ListItem> list = jdbcTemplate.query(query.toString(), ROW_MAPPER_SPECIALTY);

        if(list.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(list);
    }


    @Override
    public Optional<List<ListItem>> listSpecialties() {


        final List<ListItem> list = jdbcTemplate.query("SELECT * FROM specialty", ROW_MAPPER_SPECIALTY);

        if(list.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(list);
    }




    @Override
    public Optional<Map<String, List<String>>> listInsurancePlan() {
        String select = "SELECT insurancePlan.insurancePlanName, insurance.insuranceName ";
        String from = "FROM insurancePlan ";
        String join = "JOIN insurance ON insurance.id = insurancePlan.insuranceID";

        final Map<String, List<String>> insurancesPlans = jdbcTemplate.query(select + from + join, new SearchDaoImpl.CompressedExtractor());

        if(insurancesPlans.isEmpty()){
            return Optional.empty();
        }
        return  Optional.of(insurancesPlans);
    }

    private final class CompressedExtractor implements ResultSetExtractor<Map<String, List<String>>> {
        @Override
        public Map<String, List<String>> extractData(ResultSet rs) throws SQLException {
            Map<String, List<String>> map = new HashMap<>();


            while(rs.next()){
                boolean containsInsurance = false;
                for(String insurance : map.keySet()){
                    if(insurance.equals(rs.getString("insuranceName"))){
                        map.get(insurance).add(rs.getString("insurancePlanName"));
                        containsInsurance = true;
                    }
                }
                if(!containsInsurance){
                    List<String> l = new ArrayList<>();
                    l.add(rs.getString("insurancePlanName"));
                    map.put(rs.getString("insuranceName"),l);
                }

            }
            System.out.println(map.keySet().size());
            for(String insurance : map.keySet()){
                System.out.println(insurance);
            }
            return map;
        }
    }

}