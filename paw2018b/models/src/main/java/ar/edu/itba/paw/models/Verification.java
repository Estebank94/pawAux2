package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "verification")
public class Verification {

    @Id
    @Column(name = "token", length = 36)
    private String token;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="patientid")
    private Patient patient;

    /* package */
    Verification() {
        // Just for Hibernate, we love you!
    }

    public Verification(final String token, final Patient patient) {
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

    @Override
    public String toString() {
        return "Verification{" +
                "token='" + token + '\'' +
                ", patient=" + patient +
                '}';
    }
}
