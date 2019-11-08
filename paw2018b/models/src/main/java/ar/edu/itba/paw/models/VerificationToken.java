package ar.edu.itba.paw.models;

public class VerificationToken {
    private String token;

    private Patient patient;

    public VerificationToken(final String token, final Patient patient) {
        this.token = token;
        this.patient = patient;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
