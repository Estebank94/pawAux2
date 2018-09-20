package ar.edu.itba.paw.interfaces;

import java.util.Optional;

public interface SpecialtyDao {
    Optional<Integer> findSpecialtyId(String specialty);
}
