package ar.edu.itba.paw.webapp.forms;


import ar.edu.itba.paw.models.Doctor;

public class FavoriteForm {

    /* add, remove */
    String action;
    Doctor doctor;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
