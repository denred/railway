package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.TrainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.redkin.model.repository.impl.Constants.*;

public class TrainRepositoryImpl implements TrainRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainRepositoryImpl.class);
    private final DataSource dataSource;

    public TrainRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TrainRepositoryImpl() {
        dataSource = ConnectionPools.getProcessing();
    }

    @Override
    public int create(Train train) {
        int key = -1;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_TRAIN, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, train.getNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(
                    "Cannot add train into database, train number = " + (train == null ? null : train.getNumber()));
        }
        return key;
    }

    @Override
    public Train read(int id) {
        Train train = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_TRAIN_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                train = getTrain(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot read train from database, train id = " + id);
        }
        return train;
    }

    private Train getTrain(ResultSet rs) throws SQLException {
        return new Train(rs.getInt("id"), rs.getString("number"));
    }

    @Override
    public boolean update(Train train) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TRAIN)) {
            statement.setString(1, train.getNumber());
            statement.setInt(2, train.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot update train from database, train = " + train);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TRAIN)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot delete train from database, id = " + id);
        }
    }

    @Override
    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_TRAINS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                trains.add(getTrain(rs));
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot get list of train from database.");
        }
        return trains;
    }
}
