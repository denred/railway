package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.RoutePoint;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.RoutePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoutePointRepositoryImpl implements RoutePointRepository, Constants {
    private final static Logger LOGGER = LoggerFactory.getLogger(RoutePointRepositoryImpl.class);
    private final DataSource dataSource;

    public RoutePointRepositoryImpl() {
        dataSource = ConnectionPools.getProcessing();
    }

    public RoutePointRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<RoutePoint> getRoutePointList() {
        List<RoutePoint> routePoints = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ROUTE_MAPPING)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                routePoints.add(extractRoutePoint(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get all route list to station mapping.", e);
        }
        return routePoints;
    }


    @Override
    public void create(RoutePoint entity) {
        try (Connection connection = dataSource.getConnection();
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
    public List<RoutePoint> getRoutePointListByRouteId(int id) {
        List<RoutePoint> routePoints = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROUT_MAPPING_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                routePoints.add(extractRoutePoint(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't read route in route to station mapping. ID = " + id, e);
        }
        return routePoints;
    }

    @Override
    public boolean updateRoutePointByStationId(RoutePoint entity, int stationId) {
        try (Connection connection = dataSource.getConnection();
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
    public boolean deleteRoutePointByStationId(int routeId, int stationId) {
        try (Connection connection = dataSource.getConnection();
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
    public List<MappingInfoDTO> getMappingInfoListByRouteId(int routeId) {
        List<MappingInfoDTO> mappingInfoDTOS = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_MAPPING_BY_ROUTE_ID)) {
            statement.setInt(1, routeId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                mappingInfoDTOS.add(extractStationInfo(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t get all route list to station mapping by ID. ID = " + routeId, e);
        }
        return mappingInfoDTOS;
    }

    @Override
    public MappingInfoDTO getMappingInfo(int routeId, int stationId) {
        MappingInfoDTO mapping = new MappingInfoDTO();
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

    private RoutePoint extractRoutePoint(ResultSet resultSet) throws SQLException {
        return new RoutePoint(resultSet.getInt("station_id"),
                resultSet.getInt("route_id"),
                resultSet.getObject("station_arrival", LocalDateTime.class),
                resultSet.getObject("station_dispatch", LocalDateTime.class),
                resultSet.getInt("station_order")
        );
    }

    private MappingInfoDTO extractStationInfo(ResultSet resultSet) throws SQLException {
        MappingInfoDTO result = new MappingInfoDTO();

        result.setStationId(String.valueOf(resultSet.getInt("station_id")));
        result.setRoutsId(String.valueOf(resultSet.getInt("route_id")));
        result.setStationArrivalDate(resultSet.getObject("station_arrival", LocalDateTime.class));
        result.setStationDispatchData(resultSet.getObject("station_dispatch", LocalDateTime.class));
        result.setStation(resultSet.getString("station"));
        result.setOrder(resultSet.getInt("station_order"));

        return result;
    }
}
