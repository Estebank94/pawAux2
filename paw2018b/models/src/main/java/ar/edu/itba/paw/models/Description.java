package ar.edu.itba.paw.models;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


public class Description {

    private String certificate;
    private Set<String> languages;
    private String education;
    private Integer id;

    @Autowired
    public Description(String certificate, Set<String> languages, String education){
        this.certificate = certificate;
        this.languages = languages;
        this.education = education;
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
