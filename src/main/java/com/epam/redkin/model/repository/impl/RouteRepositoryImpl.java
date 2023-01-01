package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.dto.StationDTO;
import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.RouteRepository;
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

    public RouteRepositoryImpl() {
        dataSource = ConnectionPools.getProcessing();
    }

    public RouteRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Route entity) {
        int key = -1;
        try (Connection connection = dataSource.getConnection();
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
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot add route into database, route = " + entity);
        }
        return key;
    }

    @Override
    public Route read(int id) {
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
        return new Route(rs.getInt("r.id"), rs.getInt("r.train_id"),
                rs.getInt("r.number"), rs.getString("r.name"));
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
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ROUTE)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
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
    public RouteInfoDTO getRouteInfoDTOByRouteId(int routsId) {
        RouteInfoDTO routeInfoDto = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_INFO_BY_ID)) {
            statement.setInt(1, routsId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                routeInfoDto = extractRouteInfoDTO(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get route by id = " + routsId, e);
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

    private RouteInfoDTO extractRouteInfoDTO(ResultSet resultSet) {
        RouteInfoDTO result = new RouteInfoDTO();
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
