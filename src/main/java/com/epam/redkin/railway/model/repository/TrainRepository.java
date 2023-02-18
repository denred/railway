package com.epam.redkin.railway.model.repository;

import com.epam.redkin.railway.model.entity.Train;

import java.util.List;
import java.util.Map;

/**
 * DAO for the {@link Train} objects.
 * Interface provides CRUD methods and:
 * - get list of trains
 * - get train by his id
 *
 * @author Denys Redkin
 */
public interface TrainRepository extends EntityDAO<Train> {
    List<Train> getTrainList();

    List<Train> getTrainListWithPagination(int offset, int limit, Map<String, String> search);

    int getTrainListSize(Map<String, String> search);
}
