package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.StationRepository;
import org.apache.commons.lang3.StringUtils;
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
        LOGGER.info("Started create(Station station) with station= " + station);
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
            throw new DataBaseException("Cannot add station into database", e);
        }
        LOGGER.info("Generated id= " + key);
        return key;
    }

    @Override
    public Station getById(int id) {
        LOGGER.info("Started method getById(int id) with id= " + id);
        Station station = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_STATION_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                station = extractStation(resultSet);
            }
            LOGGER.info("Extracted station: " + station);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract station: " + e);
            throw new DataBaseException("Cannot extract station from database, station_id = " + id, e);
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
        LOGGER.info("Started method update(Station station) with station: " + station);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_STATION)) {
            statement.setString(1, station.getStation());
            statement.setInt(2, station.getId());
            boolean state = statement.executeUpdate() > 0;
            LOGGER.info("Station updated: " + state);
            return state;
        } catch (SQLException e) {
            LOGGER.error("Cannot update station: " + e);
            throw new DataBaseException("Cannot update station, station= " + station, e);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Started method delete(int id) with id= " + id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_STATION)) {
            statement.setInt(1, id);
            LOGGER.info("Station deleted: " + statement.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Cannot delete station: " + e);
            throw new DataBaseException("Cannot delete station, station_id = " + id, e);
        }
    }

    @Override
    public List<Station> getAllStations() {
        LOGGER.info("Started method getAllStations()");
        List<Station> stations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_STATIONS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stations.add(extractStation(resultSet));
            }
            LOGGER.info("Extracted stations: " + stations);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot get list of stations: " + e);
            throw new DataBaseException("Cannot get list of stations", e);
        }
        return stations;
    }

    @Override
    public List<Station> getStationsWithFilter(int offset, int limit, String search) {
        LOGGER.info("Started [List<Station> getStationsWithFilter(int offset, int limit, String search)] --> " +
                "offset: " + offset + " limit: " + limit + " search: " + search);
        String searchQuery = StringUtils.isBlank(search)  ? "" : "WHERE station REGEXP '" + search + "'";
        List<Station> stations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(Constants.GET_STATIONS_SEARCH, searchQuery))) {
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stations.add(extractStation(resultSet));
            }
            LOGGER.info("Extracted stations: " + stations);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot get list of stations: " + e);
            throw new DataBaseException("Cannot get list of stations", e);
        }
        return stations;
    }

    @Override
    public int getCountStationWithSearch(String search) {
        LOGGER.info("Started [int getCountStationWithSearch(String search)] --> search: " + search);
        String searchQuery = StringUtils.isBlank(search) ? "" : "WHERE station REGEXP '" + search + "'";
        int countStation = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(Constants.GET_COUNT_STATIONS_SEARCH, searchQuery))) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                countStation = resultSet.getInt(Constants.COUNT);
            }
            LOGGER.info("Stations count: " + countStation);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot get count of stations: " + e);
            throw new DataBaseException("Cannot get count of stations", e);
        }
        return countStation;
    }

}
