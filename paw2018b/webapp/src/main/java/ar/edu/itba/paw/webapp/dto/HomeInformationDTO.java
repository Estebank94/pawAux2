package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.InsurancePlan;
import ar.edu.itba.paw.models.Specialty;

import java.util.ArrayList;
import java.util.List;

public class HomeInformationDTO {
    List<String> specialties;
    List<InsuranceDTO> insurances;

    public HomeInformationDTO (){}

    public HomeInformationDTO(List<Specialty> specialties, List<InsurancePlan> insurancePlans) {
        this.specialties = new ArrayList<>();
        for (Specialty sp : specialties) {
            this.specialties.add(sp.getSpeciality());
        }
        this.insurances = InsuranceDTO.insurancePlanMapping(insurancePlans);
    }

    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }

    public List<InsuranceDTO> getInsurances() {
        return insurances;
    }

    public void setInsurances(List<InsuranceDTO> insurances) {
        this.insurances = insurances;
    }
}
