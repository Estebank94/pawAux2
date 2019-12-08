package ar.edu.itba.paw.models;

import java.util.*;

/**
 * Created by estebankramer on 08/09/2018.
 */
public class CompressedSearch {
    private List<Doctor> doctors;
    private Map<String, Set<String>> insurance;
    private Set<String> sex;

    public  CompressedSearch(){
        this.doctors = new ArrayList<Doctor>();
        this.insurance = new HashMap<String, Set<String>>();
        this.sex = new HashSet<String>();
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Map<String, Set<String>> getInsurance() {
        return insurance;
    }

    public void setInsurance(Map<String, Set<String>> insurance) {
        this.insurance = insurance;
    }

    public Set<String> getSex() {
        return sex;
    }

    public void setSex(Set<String> sex) {
        this.sex = sex;
    }
}