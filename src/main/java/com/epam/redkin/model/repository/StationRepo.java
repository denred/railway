package com.epam.redkin.model.repository;

import com.epam.redkin.model.entity.Station;

import java.util.List;

/**
 * DAO for the {@link Station} objects.
 * Interface provides CRUD methods and:
 * - get list of station
 *
 * @author Denys Redkin
 */
public interface StationRepo extends EntityDAO<Station> {
    List<Station> getAllStations();
}
