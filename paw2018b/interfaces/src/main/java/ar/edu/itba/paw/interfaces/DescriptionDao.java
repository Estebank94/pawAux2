package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Description;

import java.util.Optional;
import java.util.Set;

public interface DescriptionDao {

    Description createDescription(Integer doctorId, Set<String> certificate, Set<String> languages, Set<String> education);

    void addDescription(Integer doctorId, Description description);
}
