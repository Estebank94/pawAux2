package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Length;

public class DescriptionForm {

    @Length(max=250)
    private String certificate;

    @Length(max=250)
    private String education;

    private String languages;

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
}
