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
    public List<Train> getAllTrains() {
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
}
