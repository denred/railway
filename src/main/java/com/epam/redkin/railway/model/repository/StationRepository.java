package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.entity.Station;

import java.util.List;

/**
 * DAO for the {@link Station} objects.
 * Interface provides CRUD methods and:
 * - get list of station
 *
 * @author Denys Redkin
 */
public interface StationRepository extends EntityDAO<Station> {
    List<Station> getAllStations();
}
