package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by estebankramer on 02/09/2018.
 */

@Repository
public class SearchDaoImpl implements SearchDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Insurance> listInsurances() {

        final TypedQuery<Insurance> query = em.createQuery("FROM Insurance", Insurance.class);

        final List<Insurance> list = query.getResultList();

        return list.isEmpty() ? Collections.emptyList() : list;
    }

//    @Override
//    public List<Insurance> listInsurancesWithDoctors(){
//
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Insurance> query = cb.createQuery(Insurance.class);
//        Root<Insurance> root = query.from(Insurance.class);
//
//        Join<Insurance, Doctor> insuranceWithDoctors = root.join(Insurance.g)

//        query.append("SELECT DISTINCT insuranceName, insurance.id ");
//        query.append("FROM medicalCare ");
//        query.append("JOIN insurancePlan ON medicalCare.insurancePlanId = insurancePlan.id " );
//        query.append("JOIN insurance ON insurance.id = insurancePlan.insuranceId");

//        return list;
//    }


//    @Override
//    public Optional<List<ListItem>> listSpecialtiesWithDoctors() {
//        StringBuilder query = new StringBuilder();
//        query.append("SELECT DISTINCT specialtyName, specialty.id ");
//        query.append("FROM specialty ");
//        query.append("JOIN doctorSpecialty ON doctorSpecialty.specialtyId = specialty.id;");
//
//        final List<ListItem> list = jdbcTemplate.query(query.toString(), ROW_MAPPER_SPECIALTY);
//
//        if(list.isEmpty()){
//            return Optional.empty();
//        }
//        return Optional.of(list);
//    }


    @Override
    public List<Specialty> listSpecialties() {

        final TypedQuery<Specialty> query = em.createQuery("FROM Specialty", Specialty.class);
        final List<Specialty> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;

    }


    @Override
    public List<InsurancePlan> listInsurancePlans() {
        final TypedQuery<InsurancePlan> query = em.createQuery("FROM InsurancePlan", InsurancePlan.class);
        final List<InsurancePlan> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

}