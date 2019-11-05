package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Doctor;

import java.util.LinkedList;
import java.util.List;

public class DoctorListDTO {
    private List<DoctorDTO> doctors;

    private int count;

    public DoctorListDTO() {
    }

    public DoctorListDTO (List<Doctor> doctorList){
        this.doctors = new LinkedList<>();
        for (Doctor doctor : doctorList) {
            this.doctors.add(new DoctorDTO(doctor));
        }
        this.count = this.doctors.size();
    }

    public List<DoctorDTO> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorDTO> doctors) {
        this.doctors = doctors;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
