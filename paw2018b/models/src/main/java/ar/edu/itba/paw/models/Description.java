package ar.edu.itba.paw.models;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="jnformation")
public class Description {


    private String certificate;
    private Set<String> languages;
    private String education;
    @Id
    private Integer id;
    @OneToOne(mappedBy = "description")
    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Autowired
    public Description(String certificate, Set<String> languages, String education){
        this.certificate = certificate;
        this.languages = languages;
        this.education = education;
    }

    public Description(){

    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
