package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.dto.CarriageDTO;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.repository.CarriageRepository;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.redkin.railway.model.repository.impl.Constants.COUNT;

public class CarriageRepositoryImpl implements CarriageRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageRepositoryImpl.class);
    private final DataSource dataSource;

    public CarriageRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Carriage carriage) {
        LOGGER.info("Started public int create(Carriage carriage), carriage= " + carriage);
        int key = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(Constants.ADD_CARRIAGE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, carriage.getType().toString());
            statement.setString(2, carriage.getNumber());
            statement.setInt(3, carriage.getTrainId());
            statement.setInt(4, carriage.getSeatCount());
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Transaction done");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
                carriage.setCarriageId(key);
            }
        } catch (SQLException | NullPointerException e) {
            try {
                connection.rollback();
            } catch (SQLException | NullPointerException ex) {
                LOGGER.error("Connection error: " + ex);
                throw new DataBaseException("Connection error: ", ex);
            }
            LOGGER.error("Cannot add carriage: " + e);
            throw new DataBaseException("Cannot add carriage ", e);
        } finally {
            try {
                connection.setAutoCommit(true);
                DbUtils.close(resultSet);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException | NullPointerException e) {
                LOGGER.error("Connection closing error: " + e);
            }
        }
        LOGGER.info("Generated id= " + key);
        return key;
    }

    @Override
    public Carriage getById(int id) {
        LOGGER.info("Started public Carriage getById(int id), id= " + id);
        Carriage carriage = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_CARRIAGE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                carriage = getCarriage(resultSet);
            }
            LOGGER.info("Extracted Carriage: " + carriage);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract carriage: " + e);
            throw new DataBaseException("Cannot extract carriage with carriageId= " + id);
        }
        return carriage;
    }

    @Override
    public boolean update(Carriage carriage) {
        LOGGER.info("Started update({})", carriage);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_CARRIAGE)) {
            statement.setString(1, carriage.getType().toString());
            statement.setString(2, carriage.getNumber());
            statement.setInt(3, carriage.getTrainId());
            statement.setInt(4, carriage.getSeatCount());
            statement.setInt(5, carriage.getCarriageId());
            int rowCount = statement.executeUpdate();
            boolean state = rowCount > 0;
            LOGGER.info("Carriage updated: {}", state);
            return state;
        } catch (SQLException e) {
            LOGGER.error("Cannot update carriage: " + e);
            throw new DataBaseException("Cannot update carriage", e);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Started public void delete(int id), id= " + id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_CARRIAGE)) {
            statement.setInt(1, id);
            LOGGER.info("Carriage deleted: " + (statement.executeUpdate() > 0));
        } catch (SQLException e) {
            LOGGER.error("Cannot delete carriage: " + e);
            throw new DataBaseException("Cannot delete carriage from database with carriage_id = " + id, e);
        }
    }

    @Override
    public List<Carriage> getCarriagesByTrainId(int trainId) {
        LOGGER.info("Started public List<Carriage> getCarriagesByTrainId(int trainId), trainId= " + trainId);
        List<Carriage> carriages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_CARRIAGES_BY_TRAIN_ID)) {
            statement.setInt(1, trainId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carriages.add(getCarriage(resultSet));
            }
            LOGGER.info("Extracted List<Carriage>: " + carriages);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract List<Carriage>: " + e);
            throw new DataBaseException("Cannot read list of carriages from database with train_id = " + trainId, e);
        }
        return carriages;
    }

    @Override
    public List<Carriage> getCarriagesByTrainIdAndType(int trainId, String type) {
        LOGGER.info("Started public List<Carriage> getCarriagesByTrainIdAndType(int trainId, String type), " +
                "trainId= " + trainId + " type= " + type);
        List<Carriage> carriages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_CARRIAGES_BY_TRAIN_ID_AND_TYPE)) {
            statement.setInt(1, trainId);
            statement.setString(2, type);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carriages.add(getCarriage(resultSet));
            }
            LOGGER.info("Extracted List<Carriage>: " + carriages);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract List<Carriage>: " + e);
            throw new DataBaseException("Cannot extract list of carriages, train_id= " + trainId
                    + " and type= " + type, e);
        }
        return carriages;
    }

    @Override
    public List<CarriageDTO> getCarriageDTOList() {
        LOGGER.info("Started getCarriageDTOList()");
        List<CarriageDTO> carriageDTOList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_CARRIAGE)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carriageDTOList.add(extractCarriageDTO(resultSet));
            }
            LOGGER.info("Extracted List<CarriageDTO>: " + carriageDTOList);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract List<CarriageDTO>: " + e);
            throw new DataBaseException("Cannot extract List<CarriageDTO>", e);
        }
        return carriageDTOList;
    }

    @Override
    public List<CarriageDTO> getCarriageDTOListPagination(int offset, int limit, Map<String, String> search) {
        LOGGER.info(String.format("Started getCarriageDTOListPagination(offset=%s, limit=%s, search=%s)",
                offset, limit, search));
        String searchQuery = search.isEmpty() ? "" : buildSearchQuery(search);
        LOGGER.info("Search query: " + String.format(Constants.GET_ALL_CARRIAGE_WITH_FILTER_AND_PAGINATION, searchQuery));
        List<CarriageDTO> carriageDTOList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(Constants.GET_ALL_CARRIAGE_WITH_FILTER_AND_PAGINATION, searchQuery))) {
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carriageDTOList.add(extractCarriageDTO(resultSet));
            }
            LOGGER.info("Extracted List<CarriageDTO>: " + carriageDTOList);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract List<CarriageDTO>: " + e);
            throw new DataBaseException("Cannot extract List<CarriageDTO>", e);
        }
        return carriageDTOList;
    }

    @Override
    public int getCountCarriagesByFilter(Map<String, String> search) {
        LOGGER.info(String.format("Started getCountCarriagesByFilter(search=%s)", search));
        String searchQuery = buildSearchQuery(search);
        LOGGER.info("Search query: " + String.format(Constants.GET_CARRIAGE_COUNT_WITH_FILTER, searchQuery));
        int count = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(Constants.GET_CARRIAGE_COUNT_WITH_FILTER, searchQuery))) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(COUNT);
            }
            LOGGER.info("Number of records: " + count);
        } catch (SQLException e) {
            LOGGER.error("Cannot get number of CarriageDTO: " + e);
            throw new DataBaseException("Cannot get number of CarriageDTO", e);
        }
        return count;
    }

    @Override
    public Carriage getCarriageByNumber(String carriageNumber) {
        LOGGER.info(String.format("Started getCarriageByNumber(carriageNumber=%s)", carriageNumber));
        Carriage carriage = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_CARRIAGE_BY_NUMBER)) {
            statement.setString(1, carriageNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                carriage = getCarriage(resultSet);
            }
            LOGGER.info("Extracted Carriage: " + carriage);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract carriage: " + e);
            throw new DataBaseException("Cannot extract carriage with carriageNumber= " + carriageNumber);
        }
        return carriage;
    }

    @Override
    public int getSeatCountByTrainAndCarriageType(int trainId, CarriageType carriageType) {
        LOGGER.info("Started getSeatCountByTrainAndCarriageType({}, {})", trainId, carriageType);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_SEAT_COUNT_BY_TRAIN_AND_CARRIAGE_TYPE)) {
            statement.setInt(1, trainId);
            statement.setString(2, carriageType.toString());
            ResultSet resultSet = statement.executeQuery();
            int seatCount = 0;
            if (resultSet.next()) {
                seatCount = resultSet.getInt(COUNT);
            }
            LOGGER.info("Seat count retrieved: {}", seatCount);
            return seatCount;
        } catch (SQLException e) {
            LOGGER.error("Cannot retrieve seat count: " + e);
            throw new DataBaseException("Cannot retrieve seat count", e);
        }
    }

    @Override
    public int getBookedSeatsCount(int trainId, int routeId, CarriageType carriageType) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_BOOKED_SEATS_COUNT_BY_TRAIN_AND_ROUTE_AND_CARRIAGE_TYPE)) {
            statement.setInt(1, trainId);
            statement.setInt(2, routeId);
            statement.setString(3, carriageType.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to get booked seats count: {}", e.getMessage());
            throw new DataBaseException("Failed to get booked seats count", e);
        }
        return 0;
    }

    @Override
    public List<CarriageDTO> getCarriageDTOsByType(String type) {
        LOGGER.info("Started getCarriageDTOsByType({})", type);
        List<CarriageDTO> carriageDTOList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_CARRIAGE_BY_TYPE)) {
            statement.setString(1, type);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carriageDTOList.add(extractCarriageDTO(resultSet));
            }
            LOGGER.info("Retrieved CarriageDTOs: " + carriageDTOList);
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve CarriageDTOs: " + e);
            throw new DataBaseException("Failed to retrieve CarriageDTOs", e);
        }
        return carriageDTOList;
    }

    private Carriage getCarriage(ResultSet rs) throws SQLException {
        return Carriage.builder()
                .carriageId(rs.getInt(Constants.ID))
                .type(CarriageType.valueOf(rs.getString(Constants.TYPE)))
                .number(rs.getString(Constants.NUMBER))
                .trainId(rs.getInt(Constants.TRAIN_ID))
                .seatCount(rs.getInt(Constants.NUMBER_SEATS))
                .build();
    }


    private CarriageDTO extractCarriageDTO(ResultSet rs) throws SQLException {
        return CarriageDTO.builder()
                .carId(rs.getInt(Constants.CARRIAGE_ID))
                .carriageType(CarriageType.valueOf(rs.getString(Constants.TYPE)))
                .carNumber(rs.getString(Constants.CARRIAGE_NUMBER))
                .trainId(rs.getInt(Constants.TRAIN_ID))
                .trainNumber(rs.getString(Constants.TRAIN_NUMBER))
                .seats(rs.getInt(Constants.NUMBER_SEATS))
                .build();
    }

    private String buildSearchQuery(Map<String, String> search) {
        StringBuilder stringBuilder = new StringBuilder("WHERE ");
        final int[] count = {0};
        search.forEach((key, value) -> {
            if (count[0] < 1) {
                stringBuilder.append(key).append(" REGEXP ").append("'").append(value).append("'");
                count[0]++;
            } else {
                stringBuilder.append(" AND ").append(key).append(" REGEXP ").append("'").append(value).append("'");
            }
        });
        return search.isEmpty() ? "" : stringBuilder.toString();
    }
}
