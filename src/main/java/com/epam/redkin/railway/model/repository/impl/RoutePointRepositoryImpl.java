package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.entity.RoutePoint;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.RoutePointRepository;
import org.apache.commons.dbutils.DbUtils;
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

    public RoutePointRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<RoutePoint> getRoutePointList() {
        LOGGER.info("Started --> public List<RoutePoint> getRoutePointList()");
        List<RoutePoint> routePoints = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ROUTE_MAPPING)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                routePoints.add(extractRoutePoint(resultSet));
            }
            LOGGER.info("List<RoutePoint>: " + routePoints);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract List<RoutePoint>: " + e);
            throw new DataBaseException("Can't get all route list to station mapping.", e);
        }
        return routePoints;
    }


    @Override
    public void create(RoutePoint routePoint) {
        LOGGER.info("Started create() with route point= " + routePoint);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(ADD_ROUTE_MAPPINGS);
            statement.setInt(1, routePoint.getStationId());
            statement.setInt(2, routePoint.getRouteId());
            statement.setObject(3, routePoint.getArrival());
            statement.setObject(4, routePoint.getDispatch());
            statement.setInt(5, routePoint.getOrderId());
            LOGGER.info("Route point created: " + (statement.executeUpdate() > 0));
            connection.commit();
            LOGGER.info("Transaction done");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException | NullPointerException ex) {
                LOGGER.error("Connection error: " + ex);
                throw new DataBaseException("Connection error: ", ex);
            }
            LOGGER.error("Can`t create route to station mapping " + e);
            throw new DataBaseException("Can`t create route to station mapping.", e);
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
    }

    @Override
    public List<RoutePoint> getRoutePointListByRouteId(int id) {
        LOGGER.info("Started --> public List<RoutePoint> getRoutePointListByRouteId(int id) --> with id= " + id);
        List<RoutePoint> routePoints = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROUT_MAPPING_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                routePoints.add(extractRoutePoint(resultSet));
            }
            LOGGER.info("List<RoutePoint>: " + routePoints);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract List<RoutePoint>: " + e);
            throw new DataBaseException("Can't read route in route to station mapping. id= " + id, e);
        }
        return routePoints;
    }

    @Override
    public boolean updateRoutePointByStationId(RoutePoint routePoint, int stationId) {
        LOGGER.info("Started --> [updateRoutePointByStationId(RoutePoint entity, int stationId)] --> with routePoint= "
                + routePoint + ", stationId= " + stationId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTE_MAPPING)) {
            statement.setInt(1, routePoint.getStationId());
            statement.setObject(2, routePoint.getArrival());
            statement.setObject(3, routePoint.getDispatch());
            statement.setInt(4, routePoint.getOrderId());
            statement.setInt(5, routePoint.getRouteId());
            statement.setInt(6, stationId);
            boolean state = statement.executeUpdate() > 0;
            LOGGER.info("update status: " + state);
            return state;
        } catch (SQLException e) {
            LOGGER.error("Cannot update RoutePoint by Station id: " + e);
            throw new DataBaseException("Can't update route to station mapping. id = " + routePoint.getStationId(), e);
        }
    }

    @Override
    public boolean deleteRoutePointByStationId(int routeId, int stationId) {
        LOGGER.info("Started public boolean deleteRoutePointByStationId(int routeId, int stationId), routeId= "
                + routeId + " stationId= " + stationId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ROUTE_MAPPING)) {
            statement.setInt(1, routeId);
            statement.setInt(2, stationId);
            boolean state = statement.executeUpdate() > 0;
            LOGGER.info("Deleted RoutePoint: " + state);
            return state;
        } catch (SQLException e) {
            LOGGER.error("Can`t delete station in route to station mapping: " + e);
            throw new DataBaseException("Can`t delete station in route to station mapping. routeId= " + routeId + " stationId= " + stationId, e);
        }
    }

    @Override
    public List<MappingInfoDTO> getMappingInfoListByRouteId(int routeId) {
        LOGGER.info("Started public List<MappingInfoDTO> getMappingInfoListByRouteId(int routeId), routeId= " + routeId);
        List<MappingInfoDTO> mappingInfoDTOS = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_MAPPING_BY_ROUTE_ID)) {
            statement.setInt(1, routeId);
            ResultSet resultSets = statement.executeQuery();
            while (resultSets.next()) {
                mappingInfoDTOS.add(extractStationInfo(resultSets));
            }
            LOGGER.info("Extracted List<MappingInfoDTO>: " + mappingInfoDTOS);
        } catch (SQLException e) {
            LOGGER.error("Can`t get all route list to station mapping by id: " + e);
            throw new DataBaseException("Can`t get all route list to station mapping by id. id= " + routeId, e);
        }
        return mappingInfoDTOS;
    }

    @Override
    public List<MappingInfoDTO> getMappingInfoListByRouteIdAndPagination(int routeId, int offset, int limit) {
        LOGGER.info(String.format("Started --> getMappingInfoListByRouteIdAndPagination(routeId= %s, offset= %s, limit= %s)", routeId, offset, limit));
        List<MappingInfoDTO> mappingInfoDTOS = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_MAPPING_BY_ROUTE_ID_AND_PAGINATION)) {
            statement.setInt(1, routeId);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
            ResultSet resultSets = statement.executeQuery();
            while (resultSets.next()) {
                mappingInfoDTOS.add(extractStationInfo(resultSets));
            }
            LOGGER.info("Extracted List<MappingInfoDTO>: " + mappingInfoDTOS);
        } catch (SQLException e) {
            LOGGER.error("Can`t get route list to station mapping by id: " + e);
            throw new DataBaseException("Can`t get route list to station mapping by id. id= " + routeId, e);
        }
        return mappingInfoDTOS;
    }

    @Override
    public int getRouteStationCount(int routeId) {
        LOGGER.info("Started getRouteStationCount()");
        int count = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_MAPPING_SIZE_BY_ROUTE_ID_AND_PAGINATION)) {
            statement.setInt(1, routeId);
            ResultSet resultSets = statement.executeQuery();
            if (resultSets.next()) {
                count = resultSets.getInt(COUNT);
            }
            LOGGER.info("Stations in route: " + count);
        } catch (SQLException e) {
            LOGGER.error("Can`t get count stations in route: " + e);
            throw new DataBaseException("CCan`t get count stations in route", e);
        }
        return count;
    }

    @Override
    public MappingInfoDTO getMappingInfo(int routeId, int stationId) {
        LOGGER.info("Started public MappingInfoDTO getMappingInfo(int routeId, int stationId), " +
                "routeId= " + routeId + " stationId= " + stationId);
        MappingInfoDTO mapping = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROUTE_MAPPING_BY_STATION_AND_ROUTE_ID)) {
            statement.setInt(1, routeId);
            statement.setInt(2, stationId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                mapping = extractStationInfo(resultSet);
            }
            LOGGER.info("Extracted MappingInfoDTO: " + mapping);
        } catch (SQLException e) {
            LOGGER.error("Can`t get mapping information: " + e);
            throw new DataBaseException("Can`t get mapping information. " +
                    "routeId= " + routeId + " stationId= " + stationId, e);
        }
        return mapping;
    }

    private RoutePoint extractRoutePoint(ResultSet resultSet) throws SQLException {
        return RoutePoint.builder()
                .stationId(resultSet.getInt(Constants.STATION_ID))
                .routeId(resultSet.getInt(Constants.ROUTE_ID))
                .arrival(resultSet.getObject(Constants.STATION_ARRIVAL_DATE, LocalDateTime.class))
                .dispatch(resultSet.getObject(Constants.STATION_DISPATCH_DATE, LocalDateTime.class))
                .orderId(resultSet.getInt(Constants.STATION_ORDER))
                .build();
    }

    private MappingInfoDTO extractStationInfo(ResultSet resultSet) throws SQLException {
        return MappingInfoDTO.builder()
                .stationId(String.valueOf(resultSet.getInt(Constants.STATION_ID)))
                .routsId(String.valueOf(resultSet.getInt(Constants.ROUTE_ID)))
                .stationArrivalDate(resultSet.getObject(Constants.STATION_ARRIVAL_DATE, LocalDateTime.class))
                .stationDispatchData(resultSet.getObject(Constants.STATION_DISPATCH_DATE, LocalDateTime.class))
                .station(resultSet.getString(Constants.STATION))
                .order(resultSet.getInt(Constants.STATION_ORDER))
                .build();
    }
}
