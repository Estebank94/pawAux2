package ar.edu.itba.paw.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface InsurancePlanDao {
    public Optional<List<Integer>> getInsurancesPlanIds(Map<String, Set<String>> insurance);
}
