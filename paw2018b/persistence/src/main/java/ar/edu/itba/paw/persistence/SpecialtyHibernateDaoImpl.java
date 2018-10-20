package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SpecialtyDao;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Specialty;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Created by estebankramer on 19/10/2018.
 */
@Repository
public class SpecialtyHibernateDaoImpl implements SpecialtyDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Specialty> findSpecialties(Set<Specialty> specialties) {
        List<Specialty> list = new ArrayList<>();
        for(Specialty s : specialties){
            list.add(findSpecialty(s));
        }
        return (list.isEmpty() ? Collections.emptyList() : list);
    }

    @Override
    public Specialty findSpecialty(Specialty specialty) {
        final TypedQuery<Specialty> query = em.createQuery("FROM Specialty" +
                "WHERE id = :id ", Specialty.class);
        query.setParameter("id", specialty.getId());
        return query.getSingleResult();
    }

    @Override
    public void addDoctorSpecialty(Doctor doctor, Specialty specialty) {

    }

}
