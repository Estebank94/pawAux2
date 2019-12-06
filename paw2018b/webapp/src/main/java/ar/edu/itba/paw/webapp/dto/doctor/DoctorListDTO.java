package ar.edu.itba.paw.webapp.dto.doctor;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.webapp.dto.doctor.DoctorDTO;

import java.util.LinkedList;
import java.util.List;

public class DoctorListDTO {
    private List<DoctorDTO> doctors;

    private Long totalPageCount;

    public DoctorListDTO() {
    }

    public DoctorListDTO (List<Doctor> doctorList, Long pageCount){
        this.doctors = new LinkedList<>();
        for (Doctor doctor : doctorList) {
            this.doctors.add(new DoctorDTO(doctor));
        }
        this.totalPageCount = pageCount;
    }

    public List<DoctorDTO> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorDTO> doctors) {
        this.doctors = doctors;
    }

    public Long getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Long totalPageCount) {
        this.totalPageCount = totalPageCount;
    }
}
