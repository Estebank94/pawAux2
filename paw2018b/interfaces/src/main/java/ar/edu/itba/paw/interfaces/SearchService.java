package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.ListItem;

import java.util.List;
import java.util.Optional;

/**
 * Created by estebankramer on 02/09/2018.
 */

public interface SearchService {
    Optional<List<ListItem>> listInsurances();

    Optional<List<ListItem>> listZones();

    Optional<List<ListItem>> listSpecialties();
}
