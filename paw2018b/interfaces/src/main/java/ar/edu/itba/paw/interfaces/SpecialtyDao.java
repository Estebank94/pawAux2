package ar.edu.itba.paw.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SpecialtyDao {
    public Optional<List<Integer>> findSpecialtysId(Set<String> specialtySet);

}
