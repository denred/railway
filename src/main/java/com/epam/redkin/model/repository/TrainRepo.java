package com.epam.redkin.model.repository;

import com.epam.redkin.model.entity.Train;

import java.util.List;

/**
 * DAO for the {@link Train} objects.
 * Interface provides CRUD methods and:
 * - get list of trains
 * - get train by his id
 *
 * @author Denys Redkin
 */
public interface TrainRepo extends EntityDAO<Train> {
    List<Train> getAllTrains();
}
