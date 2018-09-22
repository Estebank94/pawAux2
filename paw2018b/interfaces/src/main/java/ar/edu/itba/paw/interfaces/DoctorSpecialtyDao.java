package ar.edu.itba.paw.interfaces;

import java.util.List;

public interface DoctorSpecialtyDao {
    public void addDoctorSpecialty(Integer doctorId, Integer specialtyId);

    public void addDoctorSpecialtyList(Integer doctorId, List<Integer> specialtyId);
}
