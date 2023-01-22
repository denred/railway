package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.exception.DAOException;
import com.epam.redkin.railway.model.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationRepositoryImpl implements StationRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationRepositoryImpl.class);
    private final DataSource dataSource;

    public StationRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Station station) {
        LOGGER.info("Started create() with station= " + station);
        int key = -1;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.ADD_STATION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, station.getStation());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot add station into database " + e);
            throw new DAOException("Cannot add station into database", e);
        }
        LOGGER.info("Generated id= " + key);
        return key;
    }

    @Override
    public Station getById(int id) {
        Station station = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_STATION_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                station = extractStation(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException("Cannot read station from database, station_id = " + id);
        }
        return station;
    }

    private Station extractStation(ResultSet rs) throws SQLException {
        return Station.builder()
                .id(rs.getInt(Constants.ID))
                .station(rs.getString(Constants.STATION))
                .build();
    }

    @Override
    public boolean update(Station station) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_STATION)) {
            statement.setString(1, station.getStation());
            statement.setInt(2, station.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException("Cannot update station, station = " + station);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_STATION)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException("Cannot delete station, station_id = " + id);
        }
    }

    @Override
    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_STATIONS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                stations.add(extractStation(rs));
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException("Cannot get list of stations");
        }
        return stations;
    }
}
