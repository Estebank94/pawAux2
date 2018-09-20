package ar.edu.itba.paw.interfaces;

import java.util.Optional;

public interface SpecialtyService {
    Optional<Integer> findSpecialtyId(String specialty);
}
