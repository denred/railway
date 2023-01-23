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
        LOGGER.info("Started create() with route= " + route);
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
        } catch (SQLException e) {
            assert connection != null;
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Connection error: " + ex);
                throw new DataBaseException("Connection error: ", ex);
            }
            LOGGER.error(e.getClass() + " in method create: " + e);
            throw new DataBaseException("Cannot add route into database", e);
        } finally {
            try {
                assert connection != null;
                connection.setAutoCommit(true);
                DbUtils.close(resultSet);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException e) {
                LOGGER.error("Connection closing error: " + e);
                throw new DataBaseException("Connection closing error: ", e);
            }
        }
        LOGGER.info("Generated id= " + key);
        return key;
    }

    @Override
    public Route getById(int id) {
        Route route = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_INFO_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                route = extractRoute(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t read route. ID = " + id, e);
        }
        return route;
    }


    private Route extractRoute(ResultSet rs) throws SQLException {
        return Route.builder()
                .routeId(rs.getInt(Constants.ROUTE_ID))
                .trainId(rs.getInt(Constants.TRAIN_ID))
                .routeName(rs.getString(Constants.ROUTE_NAME))
                .routeNumber(rs.getInt(Constants.ROUTE_NUMBER))
                .build();
    }

    @Override
    public boolean update(Route entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTE)) {
            statement.setString(1, entity.getRouteName());
            statement.setInt(2, entity.getRouteNumber());
            statement.setInt(3, entity.getTrainId());
            statement.setInt(4, entity.getRouteId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t update route. route = " + entity);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ROUTE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t delete route. id = " + id);
        }
    }

    @Override
    public List<RouteInfoDTO> getAllRouteInfoDTOList() {
        List<RouteInfoDTO> routes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ROUTES)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                routes.add(extractRouteInfoDTO(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get all rout info list.", e);
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
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                routeInfoDto = extractRouteInfoDTO(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get route by id = " + routeId, e);
        }
        return routeInfoDto;
    }

    @Override
    public List<StationDTO> getStationDTOListWithParameters(String departureStation, String arrivalStation) {
        List<StationDTO> routes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_LIST_WITH_PARAMETERS)) {
            statement.setString(1, departureStation);
            statement.setString(2, arrivalStation);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                routes.add(extractStationDto(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get station " + departureStation + " and station " + arrivalStation + " in rout list", e);
        }
        return routes;
    }

    private StationDTO extractStationDto(ResultSet resultSet) {
        StationDTO result = new StationDTO();
        try {
            result.setStationId(resultSet.getInt("s.id"));
            result.setStation(resultSet.getString("station"));
            result.setOrder(resultSet.getInt("station_order"));
            result.setStationArrivalDateTime(resultSet.getObject("station_arrival", LocalDateTime.class));
            result.setStationDispatchDateTime(resultSet.getObject("station_dispatch", LocalDateTime.class));
            result.setRoutName(resultSet.getString("r.name"));
            result.setRoutNumber(resultSet.getInt("r.number"));
            result.setRoutsId(resultSet.getInt("r.id"));
            result.setTrainId(resultSet.getInt("train_id"));
            result.setTrainNumber(resultSet.getString("t.number"));
            return result;
        } catch (SQLException | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t extract StationDto.", e);
        }

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
