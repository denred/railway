package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.exception.DAOException;
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
            throw new DAOException("Cannot add train into database", e);
        }
        LOGGER.info("Generated id= " + key);
        return key;
    }

    @Override
    public Train getById(int id) {
        Train train = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_TRAIN_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                train = getTrain(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException("Cannot read train from database, train id = " + id);
        }
        return train;
    }

    private Train getTrain(ResultSet rs) throws SQLException {
        return Train.builder()
                .number(rs.getString(Constants.NUMBER))
                .id(rs.getInt(Constants.ID))
                .build();
    }

    @Override
    public boolean update(Train train) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_TRAIN)) {
            statement.setString(1, train.getNumber());
            statement.setInt(2, train.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException("Cannot update train from database, train = " + train);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_TRAIN)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException("Cannot delete train from database, id = " + id);
        }
    }

    @Override
    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_TRAINS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                trains.add(getTrain(rs));
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException("Cannot get list of train from database.");
        }
        return trains;
    }
}
