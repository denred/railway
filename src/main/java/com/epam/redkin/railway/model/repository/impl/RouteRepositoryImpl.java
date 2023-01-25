package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.dto.StationDTO;
import com.epam.redkin.railway.model.entity.Route;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.RouteRepository;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RouteRepositoryImpl implements RouteRepository, Constants {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteRepositoryImpl.class);
    private final DataSource dataSource;

    public RouteRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Route route) {
        LOGGER.info("Started method create(Route route) with route= " + route);
        int key = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(ADD_ROUTE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, route.getRouteName());
            statement.setInt(2, route.getRouteNumber());
            statement.setInt(3, route.getTrainId());
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Transaction done");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException | NullPointerException e) {
            try {
                connection.rollback();
            } catch (SQLException | NullPointerException ex) {
                LOGGER.error("Connection error: " + ex);
                throw new DataBaseException("Connection error: ", ex);
            }
            LOGGER.error(e.getClass() + " in method create: " + e);
            throw new DataBaseException("Cannot add route into database", e);
        } finally {
            try {
                connection.setAutoCommit(true);
                DbUtils.close(resultSet);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException | NullPointerException e) {
                LOGGER.error("Connection closing error: " + e);
                throw new DataBaseException("Connection closing error: ", e);
            }
        }
        LOGGER.info("Generated id= " + key);
        return key;
    }

    @Override
    public Route getById(int id) {
        LOGGER.info("Started method getById(int id) with id= " + id);
        Route route = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_INFO_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                route = extractRoute(resultSet);
            }
            LOGGER.info("Extracted route: " + route);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract route: " + e);
            throw new DataBaseException("Can`t extract route", e);
        }
        return route;
    }


    private Route extractRoute(ResultSet resultSet) throws SQLException {
        return Route.builder()
                .routeId(resultSet.getInt(Constants.ROUTE_ID))
                .trainId(resultSet.getInt(Constants.TRAIN_ID))
                .routeName(resultSet.getString(Constants.ROUTE_NAME))
                .routeNumber(resultSet.getInt(Constants.ROUTE_NUMBER))
                .build();
    }

    @Override
    public boolean update(Route route) {
        LOGGER.info("Started method update(Route route) with route: " + route);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTE)) {
            statement.setString(1, route.getRouteName());
            statement.setInt(2, route.getRouteNumber());
            statement.setInt(3, route.getTrainId());
            statement.setInt(4, route.getRouteId());
            boolean state = statement.executeUpdate() > 0;
            LOGGER.info("Route updated: " + state);
            return state;
        } catch (SQLException e) {
            LOGGER.error("Can`t update route: " + e);
            throw new DataBaseException("Can`t update route", e);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Started method delete(int id) with id= " + id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ROUTE)) {
            statement.setInt(1, id);
            LOGGER.info("Route deleted: " + (statement.executeUpdate() > 0));
        } catch (SQLException e) {
            LOGGER.error("Cannot delete route: " + e);
            throw new DataBaseException("Can`t delete route. id = " + id, e);
        }
    }

    @Override
    public List<RouteInfoDTO> getRouteInfoDTOList() {
        LOGGER.info("Started method getRouteInfoDTOList()");
        List<RouteInfoDTO> routes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ROUTES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                routes.add(extractRouteInfoDTO(resultSet));
            }
            LOGGER.info("\nExtracted List of RouteInfoDTO: " + routes);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract RouteInfoDTO list: " + e);
            throw new DataBaseException("Cannot extract RouteInfoDTO list", e);
        }
        return routes;
    }

    @Override
    public RouteInfoDTO getRouteInfoDTOByRouteId(int routeId) {
        LOGGER.info("Started method getRouteInfoDTOByRouteId(int routsId) with routeId= " + routeId);
        RouteInfoDTO routeInfoDto = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_INFO_BY_ID)) {
            statement.setInt(1, routeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                routeInfoDto = extractRouteInfoDTO(resultSet);
            }
            LOGGER.info("Extracted RouteInfoDTO: " + routeInfoDto);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract RouteInfoDTO: " + e);
            throw new DataBaseException("Cannot extract RouteInfoDTO", e);
        }
        return routeInfoDto;
    }

    @Override
    public List<StationDTO> getStationDTOListWithParameters(String departureStation, String arrivalStation) {
        LOGGER.info("\nStarted method getStationDTOListWithParameters(String departureStation, String arrivalStation)\n" +
                "with departureStation= " + departureStation + " and arrivalStation= " + arrivalStation);
        List<StationDTO> routes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_LIST_WITH_PARAMETERS)) {
            statement.setString(1, departureStation);
            statement.setString(2, arrivalStation);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                routes.add(extractStationDto(resultSet));
            }
            LOGGER.info("\nExtracted StationDTO List: " + routes);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract StationDTO list: " + e);
            throw new DataBaseException("Cannot extract StationDTO list", e);
        }
        return routes;
    }

    private StationDTO extractStationDto(ResultSet resultSet) throws SQLException {
        return StationDTO.builder()
                .stationId(resultSet.getInt(Constants.STATION_ID))
                .station(resultSet.getString(Constants.STATION))
                .order(resultSet.getInt(Constants.STATION_ORDER))
                .stationArrivalDateTime(resultSet.getObject(Constants.STATION_ARRIVAL_DATE, LocalDateTime.class))
                .stationDispatchDateTime(resultSet.getObject(Constants.STATION_DISPATCH_DATE, LocalDateTime.class))
                .routName(resultSet.getString(Constants.ROUTE_NAME))
                .routNumber(resultSet.getInt(Constants.ROUTE_NUMBER))
                .routsId(resultSet.getInt(Constants.ROUTE_ID))
                .trainId(resultSet.getInt(Constants.TRAIN_ID))
                .trainNumber(resultSet.getString(Constants.TRAIN_NUMBER))
                .build();
    }

    private RouteInfoDTO extractRouteInfoDTO(ResultSet resultSet) throws SQLException {
        return RouteInfoDTO.builder()
                .routsId(resultSet.getInt(Constants.ROUTE_ID))
                .trainId(resultSet.getInt(Constants.TRAIN_ID))
                .routName(resultSet.getString(Constants.ROUTE_NAME))
                .trainNumber(resultSet.getString(Constants.TRAIN_NUMBER))
                .routNumber(resultSet.getString(Constants.ROUTE_NUMBER))
                .build();
    }
}
