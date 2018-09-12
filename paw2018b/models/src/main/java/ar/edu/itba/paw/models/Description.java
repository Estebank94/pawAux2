package ar.edu.itba.paw.models;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


public class Description {

    private Set<String> certificate;
    private Set<String> languages;
    private Set<String> education;
    private Integer id;

    @Autowired
    public Description(Set<String> certificate, Set<String> languages, Set <String> education){
        this.certificate = certificate;
        this.languages = languages;
        this.education = education;
    }

    public Set<String> getCertificate() {
        return certificate;
    }

    public void setCertificate(Set<String> certificate) {
        this.certificate = certificate;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public Set<String> getEducation() {
        return education;
    }

    public void setEducation(Set<String> education) {
        this.education = education;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
