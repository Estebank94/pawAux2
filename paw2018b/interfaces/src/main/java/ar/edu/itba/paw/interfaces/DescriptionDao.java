package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Description;

import java.util.Set;

public interface DescriptionDao {

        Description createDescription(Integer doctorId, String certificate, Set<String> languages, String education);

        void addDescription(Integer doctorId, Description description);

}
