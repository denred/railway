package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.redkin.model.repository.impl.Constants.*;

public class StationRepositoryImpl implements StationRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationRepositoryImpl.class);
    private final DataSource dataSource;

    public StationRepositoryImpl() {
        dataSource = ConnectionPools.getProcessing();
    }

    public StationRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Station station) {
        int key = -1;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_STATION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, station.getStation());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot add station into database, station = " + station);
        }
        return key;
    }

    @Override
    public Station read(int id) {
        Station station = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STATION_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                station = extractStation(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot read station from database, station_id = " + id);
        }
        return station;
    }

    private Station extractStation(ResultSet rs) throws SQLException {
        return new Station(rs.getInt("id"), rs.getString("station"));
    }

    @Override
    public boolean update(Station station) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATION)) {
            statement.setString(1, station.getStation());
            statement.setInt(2, station.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot update station, station = " + station);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STATION)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot delete station, station_id = " + id);
        }
    }

    @Override
    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STATIONS)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                stations.add(extractStation(rs));
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot get list of stations");
        }
        return stations;
    }
}
