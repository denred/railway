package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.dto.RoutInfoDto;
import com.epam.redkin.model.dto.StationDto;
import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.RouteRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RouteRepoImpl implements RouteRepo, Constants {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteRepoImpl.class);

    @Override
    public int create(Route entity) {
        int key = -1;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ROUTE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getRouteName());
            statement.setInt(2, entity.getRouteNumber());
            statement.setInt(3, entity.getTrainId());
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
    public Route read(int id) {
        Route route;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_INFO_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            route = new Route();
            if (rs.next()) {
                route.setRouteId(rs.getInt("r.id"));
                route.setRouteName(rs.getString("r.name"));
                route.setRouteNumber(rs.getInt("r.number"));
                route.setTrainId(rs.getInt("r.train_id"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t read route. ID = " + id, e);
        }
        return route;
    }

    @Override
    public boolean update(Route entity) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTE)) {
            statement.setString(1, entity.getRouteName());
            statement.setInt(2, entity.getRouteNumber());
            statement.setInt(3, entity.getTrainId());
            statement.setInt(4, entity.getRouteId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t update rout. ID = " + entity.getRouteId(), e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ROUTE)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t delete routs. ID = " + id, e);
        }
    }

    @Override
    public List<RoutInfoDto> getAllRoutList() {
        List<RoutInfoDto> routes = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ROUTES)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                routes.add(extractRoutInfo(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get all rout list.", e);
        }
        return routes;
    }

    @Override
    public RoutInfoDto getRoutById(int routsId) {
        RoutInfoDto routInfoDto = new RoutInfoDto();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_INFO_BY_ID)) {
            statement.setInt(1, routsId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                routInfoDto = extractRoutInfo(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get route by id. Id = " + routsId, e);
        }
        return routInfoDto;
    }

    @Override
    public List<StationDto> getRouteListWithParameters(String departureStation, String arrivalStation) {
        List<StationDto> routes = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_LIST_WITH_PARAMETERS)) {
            statement.setString(1, departureStation);
            statement.setString(2, arrivalStation);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                routes.add(extractStationDto(rs));
            }
            //connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get station " + departureStation + " and station " + arrivalStation + " in rout list", e);
        }
        return routes;
    }

    private StationDto extractStationDto(ResultSet resultSet) {
        StationDto result = new StationDto();
        try {
            result.setStationId(resultSet.getInt("s.id"));
            result.setStation(resultSet.getString("station"));
            result.setOrder(resultSet.getInt("pnr"));
            result.setStationArrivalDate(resultSet.getObject("station_arrival", LocalDateTime.class));
            result.setStationDispatchData(resultSet.getObject("station_dispatch", LocalDateTime.class));
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

    private RoutInfoDto extractRoutInfo(ResultSet resultSet) {
        RoutInfoDto result = new RoutInfoDto();
        try {
            result.setRoutsId(resultSet.getInt("id"));
            result.setTrainId(resultSet.getInt("train_id"));
            result.setRoutName(resultSet.getString("name"));
            result.setTrainNumber(resultSet.getString("t.number"));
            result.setRoutNumber(String.valueOf(resultSet.getInt("r.number")));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t extract RoutInfoDto.", e);
        }
        return result;
    }
}
