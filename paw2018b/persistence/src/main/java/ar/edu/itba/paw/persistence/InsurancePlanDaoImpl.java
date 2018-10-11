package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.InsurancePlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
@Repository
public class InsurancePlanDaoImpl implements InsurancePlanDao {

    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Integer> ROW_MAPPER = (rs, rowNum) -> new Integer( rs.getString("id"));

    @Autowired
    public InsurancePlanDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<List<Integer>> getInsurancesPlanIds(Map<String, Set<String>> insurance){

        Set<String> keySet = insurance.keySet();

        List<Integer> list = new ArrayList<>();
        int i = 0;
        for (String insuranceIterator: keySet){
            if (insurance.get(insuranceIterator).size() > 0){
                String parsed = parseInsurance(insuranceIterator);
                list.addAll(getInsurancePlanId(parsed, insurance.get(insuranceIterator)));
            }
        }

        if (list.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(list);
    }

    private List<Integer> getInsurancePlanId(String insurance, Set<String> insurancesPlans){

        StringBuilder query = new StringBuilder();
        query.append("SELECT insurancePlan.id ");
        query.append("FROM insurance JOIN insurancePlan ON insurancePlan.insuranceID = insurance.Id ");
        query.append("WHERE insurance.insuranceName = ? ");
        query.append("AND insurancePlan.insurancePlanName in ");
        query.append(setToString(insurancesPlans));

        return jdbcTemplate.query(query.toString(), ROW_MAPPER,insurance);
    }

    private String setToString(Set<String> setToString) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String stringIterator: setToString) {
            if (isFirst){
                sb.append("('");
                isFirst = false;
            } else {
                sb.append(",'");
            }
            sb.append(stringIterator);
            sb.append("'");
        }
        sb.append(")");
        return sb.toString();
    }

    private String parseInsurance( String insurance){

        char [] insuranceNameArray = insurance.toCharArray();
        StringBuilder string = new StringBuilder();
        for(int i=0; i<insuranceNameArray.length; i++){
            if(insuranceNameArray[i] != '.'){string.append(insuranceNameArray[i]);}
            else{string.append(' ');}
        }

        return string.toString();
    }

}
