package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.entity.Station;

import java.util.List;
import java.util.Map;

/**
 * DAO for the {@link Station} objects.
 * Interface provides CRUD methods and:
 * - get list of station
 *
 * @author Denys Redkin
 */
public interface StationRepository extends EntityDAO<Station> {
    List<Station> getAllStations();

    List<Station> getStationsWithFilter(int offset, int limit, String search);

    int getCountStationWithSearch(String search);
}
