package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.redkin.model.repository.impl.Constants.*;

public class SeatRepositoryImpl implements SeatRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageRepositoryImpl.class);
    private final DataSource dataSource;

    public SeatRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SeatRepositoryImpl() {
        dataSource = ConnectionPools.getProcessing();
    }

    @Override
    public int create(Seat seat) {
        int key = -1;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_SEAT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, seat.getSeatNumber());
            statement.setBoolean(2, seat.isBusy());
            statement.setInt(3, seat.getCarriageId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot add seat into database, seat = " + seat);
        }
        return key;
    }

    @Override
    public Seat read(int id) {
        Seat seat = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_SEAT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                seat = getSeat(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot read seat from database, seat_id = " + id);
        }
        return seat;
    }

    private Seat getSeat(ResultSet rs) throws SQLException {
        return new Seat(rs.getInt("id"),
                rs.getInt("carriage_id"),
                rs.getString("seat_number"),
                rs.getBoolean("busy"));
    }


    @Override
    public boolean update(Seat seat) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SEAT)) {
            statement.setString(1, seat.getSeatNumber());
            statement.setBoolean(2, seat.isBusy());
            statement.setInt(3, seat.getCarriageId());
            statement.setInt(4, seat.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot update seat from database, seat = " + seat);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SEAT)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot delete seat from database, id = " + id);
        }
    }

    @Override
    public boolean deleteAllSeatsByCarriageId(int carriageId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SEATS_BY_CARRIAGE_ID)) {
            statement.setInt(1, carriageId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot delete seats from database, carriage_id = " + carriageId);
        }
    }

    @Override
    public int getSeatsCountByCarriageId(int carriageId) {
        return extractCount(carriageId, GET_SEATS_COUNT_IN_CARRIAGE);
    }

    @Override
    public int getBusySeatsCountByCarriageId(int carriageId) {
        return extractCount(carriageId, GET_BUSY_SEATS_COUNT_IN_CARRIAGE);
    }

    private int extractCount(int carriageId, String query) {
        int count = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carriageId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot extract seats count from database, carriage_id = " + carriageId);
        }
        return count;
    }

    @Override
    public int getSeatsCountByTrainIdAndByTypes(int trainId, CarriageType type) {
        int count = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_SEATS_IN_TRAIN_BY_TYPE)) {
            statement.setInt(1, trainId);
            statement.setString(2, type.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot extract seats count from database, train_id = " + trainId + " type = "
                    + type.name());
        }
        return count;
    }

    @Override
    public List<Seat> getListSeatsByCarriageId(int carriageId) {
        List<Seat> seats = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_LIST_SEATS_BY_CARRIAGE_ID)) {
            statement.setInt(1, carriageId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                seats.add(getSeat(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot extract list of seats from database, carriage_id = " + carriageId);
        }
        return seats;
    }

    @Override
    public List<Seat> getListSeatsByIdBatch(List<String> seatsId) {
        List<Seat> seats = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     String.format(GET_SEATS_LIST_BY_ID_BATCH,
                             String.join(", ", seatsId)))) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                seats.add(getSeat(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get seat by ID batch. ID = " + seatsId, e);
        }
        return seats;
    }

    @Override
    public void reservedSeat(int seatId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TAKE_IN_SEAT)) {
            statement.setInt(1, seatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't reserved seat. seat_id = " + seatId);
        }
    }

    @Override
    public void clearSeat(int seatId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TAKE_OFF_SEAT)) {
            statement.setInt(1, seatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't clear seat. seat_id = " + seatId);
        }
    }
}
