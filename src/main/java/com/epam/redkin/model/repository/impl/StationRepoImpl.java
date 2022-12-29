package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.repository.StationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.redkin.model.repository.impl.Constants.*;

public class StationRepoImpl implements StationRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationRepoImpl.class);

    @Override
    public int create(Station station) {
        int key = -1;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_STATION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, station.getStation());
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
    public Station read(int id) {
        Station station = new Station();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STATION_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                station.setId(rs.getInt("id"));
                station.setStation(rs.getString("station"));
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException("Can`t read station. ID = " + id, e);
        }
        return station;
    }

    @Override
    public boolean update(Station station) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATION)) {
            statement.setString(1, station.getStation());
            statement.setInt(2, station.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STATION)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STATIONS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Station station = new Station();
                station.setId(rs.getInt(1));
                station.setStation(rs.getString(2));
                stations.add(station);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
        return stations;
    }
}
