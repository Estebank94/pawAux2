package ar.edu.itba.paw.interfaces;

import java.util.List;

public interface DoctorSpecialtyDao {

    void addDoctorSpecialty(Integer doctorId, Integer specialtyId);

    void addDoctorSpecialtyList(Integer doctorId, List<Integer> specialtyId);

}
