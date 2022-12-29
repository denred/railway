package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Seat;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.SeatRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.redkin.model.repository.impl.Constants.*;

public class SeatRepoImpl implements SeatRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageRepoImpl.class);

    @Override
    public int create(Seat seat) {
        int key = -1;
        try (Connection connection = DBManager.getConnection();
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
            throw new RuntimeException(e);
        }
        return key;
    }

    @Override
    public Seat read(int id) {
        Seat seat = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_SEAT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                seat = getSeat(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException("Can`t read seat. ID = " + id, e);
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
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SEAT)) {
            statement.setString(1, seat.getSeatNumber());
            statement.setBoolean(2, seat.isBusy());
            statement.setInt(3, seat.getCarriageId());
            statement.setInt(4, seat.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SEAT)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteAllSeatsByCarId(int carrId) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SEATS_BY_CARRIAGE)) {
            statement.setInt(1, carrId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSeatsCarriage(int carriageId) {
        return extractCount(carriageId, GET_SEATS_IN_CARRIAGE);
    }

    @Override
    public int getSeatsCarriageBusy(int carriageId) {
        return extractCount(carriageId, GET_SEATS_IN_CARRIAGE_BUSY);
    }

    private int extractCount(int carriageId, String getSeatsInCarriageBusy) {
        int count = 0;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(getSeatsInCarriageBusy)) {
            statement.setInt(1, carriageId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int getSeatsTrainByTypes(int trainId, CarriageType type) {
        int count = 0;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_SEATS_IN_TRAIN_BY_TYPE)) {
            statement.setInt(1, trainId);
            statement.setString(2, type.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public List<Seat> getListSeatsByCarrId(int carriageId) {
        List<Seat> seats = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_LIST_SEATS_BY_CARRIAGE_ID)) {
            statement.setInt(1, carriageId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                seats.add(getSeat(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seats;
    }

    @Override
    public List<Seat> getListSeatsByIdBatch(List<String> seatsNumber) {
        List<Seat> seats = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     String.format(GET_SEAT_NUMBER_BY_ID_BATCH,
                             seatsNumber.stream()
                                     .collect(Collectors.joining(", "))))) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                seats.add(getSeat(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get seat by ID batch. ID = " + seatsNumber, e);
        }
        return seats;
    }

    @Override
    public void takeSeat(int seatId) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(TAKE_IN_SEAT)) {
            statement.setInt(1, seatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void takeOffSeat(int seatId) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(TAKE_OFF_SEAT)) {
            statement.setInt(1, seatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
