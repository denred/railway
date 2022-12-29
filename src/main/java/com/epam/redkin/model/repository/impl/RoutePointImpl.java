package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.dto.MappingInfoDto;
import com.epam.redkin.model.entity.RoutePoint;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.RoutePointRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoutePointImpl implements RoutePointRepo, Constants {
    private final static Logger LOGGER = LoggerFactory.getLogger(RoutePointImpl.class);

    @Override
    public List<RoutePoint> getAllRoutToStationMappingList() {
        List<RoutePoint> routePoints = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ROUTE_MAPPING)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                routePoints.add(extract(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get all route list to station mapping.", e);
        }
        return routePoints;
    }


    @Override
    public void create(RoutePoint entity) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ROUTE_MAPPINGS)) {
            statement.setInt(1, entity.getStationId());
            statement.setInt(2, entity.getRouteId());
            statement.setObject(3, entity.getArrival());
            statement.setObject(4, entity.getDispatch());
            statement.setInt(5, entity.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t create route to station mapping.", e);
        }
    }

    @Override
    public RoutePoint read(int id) {
        RoutePoint routePoint;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROUT_MAPPING_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            routePoint = extract(rs);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't read route in route to station mapping. ID = " + id, e);
        }
        return routePoint;
    }

    @Override
    public boolean update(RoutePoint entity, int stationId) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTE_MAPPING)) {
            statement.setInt(1, entity.getStationId());
            statement.setObject(2, entity.getArrival());
            statement.setObject(3, entity.getDispatch());
            statement.setInt(4, entity.getOrderId());
            statement.setInt(5, entity.getRouteId());
            statement.setInt(6, stationId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't update route to station mapping. ID = " + entity.getStationId(), e);
        }
    }

    @Override
    public boolean delete(int routeId, int stationId) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ROUTE_MAPPING)) {
            statement.setInt(1, routeId);
            statement.setInt(2, stationId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t delete station in route to station mapping. ID route = " + routeId + " ID station = " + stationId, e);
        }
    }

    @Override
    public List<MappingInfoDto> getAllRoutToStationMappingListById(int routeId) {
        List<MappingInfoDto> mappingInfoDtos = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_MAPPING_BY_ROUTE_ID)) {
            statement.setInt(1, routeId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                mappingInfoDtos.add(extractStationInfo(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t get all route list to station mapping by ID. ID = " + routeId, e);
        }
        return mappingInfoDtos;
    }

    @Override
    public MappingInfoDto getMappingInfo(int routeId, int stationId) {
        MappingInfoDto mapping = new MappingInfoDto();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_MAPPING_BY_STATION_AND_ROUTE_ID)) {
            statement.setInt(1, routeId);
            statement.setInt(2, stationId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                mapping = extractStationInfo(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t get mapping information. route ID = " + routeId + " station ID = " + stationId, e);
        }
        return mapping;
    }

    private RoutePoint extract(ResultSet resultSet) {
        RoutePoint routePoint = new RoutePoint();
        try {
            routePoint.setStationId(resultSet.getInt("station_id"));
            routePoint.setRouteId(resultSet.getInt("route_id"));
            routePoint.setArrival(resultSet.getObject("station_arrival", LocalDateTime.class));
            routePoint.setDispatch(resultSet.getObject("station_dispatch", LocalDateTime.class));
            routePoint.setOrderId(resultSet.getInt("pnr"));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t extract stop station in the route.", e);
        }
        return routePoint;
    }

    private MappingInfoDto extractStationInfo(ResultSet resultSet) {
        MappingInfoDto result = new MappingInfoDto();
        try {
            result.setStationId(String.valueOf(resultSet.getInt("station_id")));
            result.setRoutsId(String.valueOf(resultSet.getInt("route_id")));
            result.setStationArrivalDate(resultSet.getObject("station_arrival", LocalDateTime.class));
            result.setStationDispatchData(resultSet.getObject("station_dispatch", LocalDateTime.class));
            result.setStation(resultSet.getString("station"));
            result.setOrder(resultSet.getInt("pnr"));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t extract MappingInfoDto.", e);
        }
        return result;
    }
}
