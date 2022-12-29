package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.repository.TrainRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.redkin.model.repository.impl.Constants.*;

public class TrainRepoImpl implements TrainRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainRepoImpl.class);

    @Override
    public int create(Train train) {
        int key = -1;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_TRAIN, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, train.getNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    @Override
    public Train read(int id) {
        Train train = new Train();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_TRAIN_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                train.setId(rs.getInt("id"));
                train.setNumber(rs.getString("number"));
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException("Can`t read train. ID = " + id, e);
        }
        return train;
    }

    @Override
    public boolean update(Train train) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TRAIN)) {
            statement.setString(1, train.getNumber());
            statement.setInt(2, train.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TRAIN)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_TRAINS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Train train = new Train();
                train.setId(rs.getInt(1));
                train.setNumber(rs.getString(2));
                trains.add(train);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
        return trains;
    }
}
