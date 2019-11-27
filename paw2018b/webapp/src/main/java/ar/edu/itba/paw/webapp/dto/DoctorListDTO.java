package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Doctor;

import java.util.LinkedList;
import java.util.List;

public class DoctorListDTO {
    private List<DoctorDTO> doctors;

    private int count;

    private Long totalPageCount;

    public DoctorListDTO() {
    }

    public DoctorListDTO (List<Doctor> doctorList, Long pageCount){
        this.doctors = new LinkedList<>();
        for (Doctor doctor : doctorList) {
            this.doctors.add(new DoctorDTO(doctor));
        }
        this.count = this.doctors.size();
        this.totalPageCount = pageCount;
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

    public Long getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Long totalPageCount) {
        this.totalPageCount = totalPageCount;
    }
}
