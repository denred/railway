package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.dto.CarDto;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.exception.IncorrectDataException;
import com.epam.redkin.model.repository.CarriageRepo;
import com.epam.redkin.model.connectionpool.DBManager;
import com.epam.redkin.model.entity.Carriage;
import com.epam.redkin.model.entity.CarriageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.redkin.model.repository.impl.Constants.*;

public class CarriageRepoImpl implements CarriageRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageRepoImpl.class);
    private final DataSource dataSource;

    public CarriageRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CarriageRepoImpl() {
        dataSource = ConnectionPools.getProcessing();
    }

    @Override
    public int create(Carriage carriage) {
        int key = -1;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_CARRIAGE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, carriage.getType().toString());
            statement.setString(2, carriage.getNumber());
            statement.setInt(3, carriage.getTrainId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
                carriage.setCarriageId(key);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException(
                    "Incorrect input data.\ncarriage = " + (carriage == null ? null : carriage.toString()));
        }
        return key;
    }

    @Override
    public Carriage read(int id) {
        Carriage carriage = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CARRIAGE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                carriage = getCarriage(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException("Can`t read carriage. ID = " + id, e);
        }
        return carriage;
    }

    @Override
    public boolean update(Carriage carriage) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CARRIAGE)) {
            statement.setString(1, carriage.getType().toString());
            statement.setString(2, carriage.getNumber());
            statement.setInt(3, carriage.getTrainId());
            statement.setInt(4, carriage.getCarriageId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CARRIAGE)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Carriage> getCarriagesByTrainId(int trainId) {
        List<Carriage> carriages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CARRIAGES_BY_TRAIN_ID)) {
            statement.setInt(1, trainId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                carriages.add(getCarriage(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carriages;
    }

    @Override
    public List<Carriage> getCarriagesByTrainIdAndType(int trainId, String type) {
        List<Carriage> carriages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CARRIAGES_BY_TRAIN_ID_AND_TYPE)) {
            statement.setInt(1, trainId);
            statement.setString(2, type);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                carriages.add(getCarriage(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carriages;
    }

    @Override
    public List<CarDto> getAllCarList() {
        List<CarDto> carDtoList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_CARRIAGE)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                carDtoList.add(extractCarDto(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can't get cars list.", e);
        }
        return carDtoList;
    }

    private Carriage getCarriage(ResultSet rs) throws SQLException {
        return new Carriage(rs.getInt("id"),
                CarriageType.valueOf(rs.getString("type")),
                rs.getString("number"),
                rs.getInt("train_id"));
    }

    private CarDto extractCarDto(ResultSet resultSet) {
        CarDto dto = new CarDto();
        try {
            dto.setCarId(resultSet.getInt("c.id"));
            dto.setCarType(CarriageType.valueOf(resultSet.getString("type")));
            dto.setCarNumber(resultSet.getString("c.number"));
            dto.setTrainId(resultSet.getInt("train_id"));
            dto.setTrainNumber(resultSet.getString("t.number"));
        } catch (IllegalArgumentException | SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Can`t extract CarDto", e);
        }
        return dto;
    }
}
