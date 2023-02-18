package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.TrainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrainRepositoryImpl implements TrainRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainRepositoryImpl.class);
    private final DataSource dataSource;

    public TrainRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Train train) {
        LOGGER.info("Started create() with train= " + train);
        int key = -1;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.ADD_TRAIN, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, train.getNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot add train into database " + e);
            throw new DataBaseException("Cannot add train into database", e);
        }
        LOGGER.info("Generated id= " + key);
        return key;
    }

    @Override
    public Train getById(int id) {
        LOGGER.info("Started method getById(int id) with id= " + id);
        Train train = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_TRAIN_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                train = getTrain(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot read train from database" + e);
            throw new DataBaseException("Cannot read train from database", e);
        }
        LOGGER.info("Extracted train: " + train);
        return train;
    }

    private Train getTrain(ResultSet resultSet) throws SQLException {
        return Train.builder()
                .number(resultSet.getString(Constants.NUMBER))
                .id(resultSet.getInt(Constants.ID))
                .build();
    }

    @Override
    public boolean update(Train train) {
        LOGGER.info("Started method update(Train train) with train: " + train);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_TRAIN)) {
            statement.setString(1, train.getNumber());
            statement.setInt(2, train.getId());
            boolean state = statement.executeUpdate() > 0;
            LOGGER.info("Train updated: " + state);
            return state;
        } catch (SQLException e) {
            LOGGER.error("Cannot update train: " + e);
            throw new DataBaseException("Cannot update train from database, train = " + train, e);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Started method delete(int id) with id= " + id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_TRAIN)) {
            statement.setInt(1, id);
            LOGGER.info("Method done with status= " + statement.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Cannot delete train from database: " + e);
            throw new DataBaseException("Cannot delete train from database, id = " + id, e);
        }
    }

    @Override
    public List<Train> getTrainList() {
        LOGGER.info("Started method  getAllTrains()");
        List<Train> trains = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_TRAINS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trains.add(getTrain(resultSet));
            }
            LOGGER.info("Extracted trains: " + trains);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract list of train from database: " + e);
            throw new DataBaseException("Cannot get list of train from database.", e);
        }
        return trains;
    }

    @Override
    public List<Train> getTrainListWithPagination(int offset, int limit, Map<String, String> search) {
        LOGGER.info(String.format("Started getTrainListWithPagination(offset=%s, limit=%s, search=%s)", offset, limit, search));
        List<Train> trainList = new ArrayList<>();
        String searchQuery = search.isEmpty() ? "" : buildSearchQuery(search);
        LOGGER.info("Search query: " + String.format(Constants.GET_ALL_TRAINS_BY_FILTER_AND_PAGINATION, searchQuery));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(Constants.GET_ALL_TRAINS_BY_FILTER_AND_PAGINATION, searchQuery))) {
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trainList.add(getTrain(resultSet));
            }
            LOGGER.info("Extracted trains: " + trainList);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract list of train from database: " + e);
            throw new DataBaseException("Cannot get list of train from database.", e);
        }
        return trainList;
    }

    @Override
    public int getTrainListSize(Map<String, String> search) {
        LOGGER.info(String.format("Started getTrainListSize(search=%s)", search));
        int count = 0;
        String searchQuery = search.isEmpty() ? "" : buildSearchQuery(search);
        LOGGER.info("Search query: " + String.format(Constants.GET_COUNT_TRAINS_BY_FILTER, searchQuery));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(Constants.GET_COUNT_TRAINS_BY_FILTER, searchQuery))) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(Constants.COUNT);
            }
            LOGGER.info("Extracted count of trains: " + count);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot get count of train from database: " + e);
            throw new DataBaseException("Cannot get count of train from database", e);
        }
        return count;
    }

    private String buildSearchQuery(Map<String, String> search) {
        StringBuilder stringBuilder = new StringBuilder("WHERE ");
        final int[] count = {0};
        search.forEach((key, value) -> {
            if (count[0] < 1) {
                stringBuilder.append(key).append(" REGEXP ").append("'").append(value).append("'");
                count[0]++;
            } else {
                stringBuilder.append(" AND ").append(key).append(" REGEXP ").append("'").append(value).append("'");
            }
        });
        return search.isEmpty() ? "" : stringBuilder.toString();
    }
}
