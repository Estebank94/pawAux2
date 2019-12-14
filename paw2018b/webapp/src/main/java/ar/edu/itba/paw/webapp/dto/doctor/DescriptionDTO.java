package ar.edu.itba.paw.webapp.dto.doctor;

import ar.edu.itba.paw.models.Description;

public class DescriptionDTO {
    private String certificate;
    private String languages;
    private String education;

    public DescriptionDTO(){}

    public DescriptionDTO(Description description){
        this.certificate = description.getCertificate();
        this.education = description.getEducation();
        this.languages = description.getLanguages();
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
