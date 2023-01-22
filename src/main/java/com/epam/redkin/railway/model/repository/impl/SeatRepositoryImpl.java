package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.exception.DAOException;
import com.epam.redkin.railway.model.repository.SeatRepository;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatRepositoryImpl implements SeatRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatRepositoryImpl.class);
    private final DataSource dataSource;

    public SeatRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Seat seat) {
        LOGGER.info("Started create() with seat= " + seat);
        int key = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            statement = connection.prepareStatement(Constants.ADD_SEAT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, seat.getSeatNumber());
            statement.setBoolean(2, seat.isBusy());
            statement.setInt(3, seat.getCarriageId());
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Transaction done");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Connection error: " + ex);
                throw new DAOException("Connection error: " + seat, ex);
            }
            LOGGER.error(e.getClass() + " in method create: " + e);
            throw new DAOException("Cannot add seat into database ", e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    DbUtils.close(resultSet);
                    DbUtils.close(statement);
                    DbUtils.close(connection);
                    LOGGER.info("Connection closed");
                }
            } catch (SQLException e) {
                LOGGER.error("Connection closing error: " + e);
                throw new DAOException("Connection closing error: ", e);
            }
        }
        LOGGER.info("Generated id= " + key);
        return key;
    }

    @Override
    public Seat getById(int id) {
        LOGGER.info("Started getById() with id= " + id);
        Seat seat = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_SEAT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                seat = getSeat(resultSet);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot get seat by id: " + e);
            throw new DAOException("Cannot read seat from database, seat_id = " + id, e);
        }
        LOGGER.info("The method getById() done with seat= " + seat);
        return seat;
    }

    private Seat getSeat(ResultSet resultSet) throws SQLException {
        return Seat.builder()
                .id(resultSet.getInt(Constants.ID))
                .carriageId(resultSet.getInt(Constants.CARRIAGE_ID))
                .seatNumber(resultSet.getString(Constants.SEAT_NUMBER))
                .busy(resultSet.getBoolean(Constants.BUSY))
                .build();
    }


    @Override
    public boolean update(Seat seat) {
        LOGGER.info("Started the method update() with seat= " + seat);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_SEAT)) {
            statement.setString(1, seat.getSeatNumber());
            statement.setBoolean(2, seat.isBusy());
            statement.setInt(3, seat.getCarriageId());
            statement.setInt(4, seat.getId());
            boolean stateUpdate = statement.executeUpdate() > 0;
            LOGGER.info("The method update() done with status= " + stateUpdate);
            return stateUpdate;
        } catch (SQLException e) {
            LOGGER.error("Cannot update the seat " + e);
            throw new DAOException("Cannot update seat from database, seat = " + seat, e);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Started method delete() with id= " + id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_SEAT)) {
            statement.setInt(1, id);
            LOGGER.info("The method delete() done, with row count= " + statement.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Cannot delete seat: " + e);
            throw new DAOException("Cannot delete seat from database, id = " + id, e);
        }
    }

    @Override
    public boolean deleteAllSeatsByCarriageId(int carriageId) {
        LOGGER.info("Started the method deleteAllSeatsByCarriageId() with carriageId= " + carriageId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_SEATS_BY_CARRIAGE_ID)) {
            statement.setInt(1, carriageId);
            int state = statement.executeUpdate();
            LOGGER.info("The method deleteAllSeatsByCarriageId() done with row count= " + state);
            return state > 0;
        } catch (SQLException e) {
            LOGGER.error("Cannot delete seats= " + e);
            throw new DAOException("Cannot delete seats from database, carriage_id = " + carriageId, e);
        }
    }

    @Override
    public int getSeatsCountByCarriageId(int carriageId) {
        LOGGER.info("Started the method getSeatsCountByCarriageId() with carriageId= " + carriageId);
        return extractCount(carriageId, Constants.GET_SEATS_COUNT_IN_CARRIAGE);
    }

    @Override
    public int getBusySeatsCountByCarriageId(int carriageId) {
        LOGGER.info("Started the method getBusySeatsCountByCarriageId() with carriageId= " + carriageId);
        return extractCount(carriageId, Constants.GET_BUSY_SEATS_COUNT_IN_CARRIAGE);
    }

    private int extractCount(int carriageId, String query) {
        int count = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carriageId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            LOGGER.info("Seats count= " + count);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract seats: " + e);
            throw new DAOException("Cannot extract seats count from database, carriage_id = " + carriageId, e);
        }
        return count;
    }

    @Override
    public int getSeatsCountByTrainIdAndByTypes(int trainId, CarriageType type) {
        LOGGER.info("Started the method getSeatsCountByTrainIdAndByTypes() with trainId= " + trainId
                + " and Carriage type= " + type);
        int count = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_SEATS_IN_TRAIN_BY_TYPE)) {
            statement.setInt(1, trainId);
            statement.setString(2, type.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
            LOGGER.info("Seats count= " + count);
        } catch (SQLException | NullPointerException e) {
            LOGGER.error("Cannot extract seats: " + e);
            throw new DAOException("Cannot extract seats count from database", e);
        }
        return count;
    }

    @Override
    public List<Seat> getListSeatsByCarriageId(int carriageId) {
        LOGGER.info("Started the method getListSeatsByCarriageId(int carriageId) with carriageId= " + carriageId);
        List<Seat> seats = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_LIST_SEATS_BY_CARRIAGE_ID)) {
            statement.setInt(1, carriageId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                seats.add(getSeat(resultSet));
            }
            LOGGER.info("Extracted seats: " + seats);
        } catch (SQLException e) {
            LOGGER.error("Cannot extract seats: " + e);
            throw new DAOException("Cannot extract list of seats from database", e);
        }
        return seats;
    }

    @Override
    public List<Seat> getListSeatsByIdBatch(List<String> seatsId) {
        LOGGER.info("Started the method getListSeatsByIdBatch(List<String> seatsId) with\n seatsId= " + seatsId);
        List<Seat> seats = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     String.format(Constants.GET_SEATS_LIST_BY_ID_BATCH,
                             String.join(", ", seatsId)))) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                seats.add(getSeat(rs));
            }
            LOGGER.info("Seats: " + seats);
        } catch (SQLException e) {
            LOGGER.error("Cannot get seats by id batch: " + e);
            throw new DAOException("Can't get seat by ID batch ", e);
        }
        return seats;
    }

    @Override
    public void reservedSeat(int seatId) {
        LOGGER.info("Started method reservedSeat(int seatId) with seatId= " + seatId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.TAKE_IN_SEAT)) {
            statement.setInt(1, seatId);
            LOGGER.info("The method done. Reserved count seat: " + statement.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Can't reserved seat: " + e);
            throw new DAOException("Can't reserved seat. seat_id = " + seatId, e);
        }
    }

    @Override
    public void clearSeat(int seatId) {
        LOGGER.info("Started method with clearSeat(int seatId) with seatId= " + seatId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.TAKE_OFF_SEAT)) {
            statement.setInt(1, seatId);
            LOGGER.info("Clear count seats: " + statement.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Can't clear seat: " + e);
            throw new DAOException("Can't clear seat. seat_id = " + seatId, e);
        }
    }
}
