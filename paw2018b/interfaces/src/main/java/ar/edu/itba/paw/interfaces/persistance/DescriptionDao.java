package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Description;

import java.util.Set;

public interface DescriptionDao {

        Description createDescription(String certificate, String languages, String education);

//        void addDescription(Integer doctorId, Description description);

}
