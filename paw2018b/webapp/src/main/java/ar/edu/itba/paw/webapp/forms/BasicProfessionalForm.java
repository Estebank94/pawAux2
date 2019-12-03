package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.InsurancePlan;
import ar.edu.itba.paw.models.Specialty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.*;

public class BasicProfessionalForm {

    @Length(max=250)
    private String certificate;

    @Length(max=250)
    private String education;

    private String languages;

//    @NotNull
//    private List<String> insurance;
//    @NotNull
//    private List<String> insurancePlan;
//    @NotNull
    private List<String> specialty;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

//    public List<String> getInsurance() {
//        return insurance;
//    }
//
//    public void setInsurance(List<String> insurance) {
//        this.insurance = insurance;
//    }
//
//    public List<String> getInsurancePlan() {
//        return insurancePlan;
//    }
//
//    public void setInsurancePlan(List<String> insurancePlan) {
//        this.insurancePlan = insurancePlan;
//    }
//
    public List<String> getSpecialty() {
        return specialty;
    }

    public void setSpecialty(List<String> specialty) {
        this.specialty = specialty;
    }
//
//    public Set<Specialty> getSpecialties(){
//        Set<Specialty> list = new HashSet<>();
//        for(String s : getSpecialty()){
//            list.add(new Specialty(s));
//        }
//        return (list.isEmpty() ? Collections.emptySet() : list);
//    }
//
//    public List<InsurancePlan> getInsurancePlans(){
//        List<InsurancePlan> list = new ArrayList<>();
//        for(String s : getInsurancePlan()){
//            List<String> plans = insuranceParser(s);
//            for(String p : plans){
//                list.add(new InsurancePlan(p));
//            }
//        }
//        return (list.isEmpty() ? Collections.EMPTY_LIST : list);
//    }
//
//    /* Esto me deberia devolver un List de Strings de 2 */
//    private List<String> insuranceParser(String insurancePlan){
//        List<String> l = new ArrayList<>();
//        StringBuilder s = new StringBuilder();
//        char[] c = insurancePlan.toCharArray();
//
//        for(int i = 0; i<insurancePlan.length(); i++){
//            if(c[i] == ','){
//                l.add(s.toString());
//                s = new StringBuilder();
//            }else {
//                s.append(c[i]);
//            }
//        }
//        l.add(s.toString());
//        return l;
//    }
}
