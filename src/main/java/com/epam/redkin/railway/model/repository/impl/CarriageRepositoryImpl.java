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

public class CarriageRepositoryImpl implements CarriageRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarriageRepositoryImpl.class);
    private final DataSource dataSource;

    public CarriageRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Carriage carriage) {
        LOGGER.info("Started create() with carriage= " + carriage);
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
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Transaction done");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                key = resultSet.getInt(1);
                carriage.setCarriageId(key);
            }
        } catch (SQLException | NullPointerException e) {
            assert connection != null;
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Connection error: " + ex);
                throw new DataBaseException("Connection error: ", ex);
            }
            LOGGER.error(e.getClass() + " in method create: " + e);
            throw new DataBaseException("Cannot add carriage ", e);
        } finally {
            try {
                assert connection != null;
                connection.setAutoCommit(true);
                DbUtils.close(resultSet);
                DbUtils.close(statement);
                DbUtils.close(connection);
                LOGGER.info("Connection closed");
            } catch (SQLException e) {
                LOGGER.error("Connection closing error: " + e);
                throw new DataBaseException("Connection closing error: ", e);
            }
        }
        LOGGER.info("Generated id= " + key);
        return key;
    }

    @Override
    public Carriage getById(int id) {
        Carriage carriage = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_CARRIAGE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                carriage = getCarriage(rs);
            }
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot read carriage from database with carriage_id = " + id);
        }
        return carriage;
    }

    @Override
    public boolean update(Carriage carriage) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_CARRIAGE)) {
            statement.setString(1, carriage.getType().toString());
            statement.setString(2, carriage.getNumber());
            statement.setInt(3, carriage.getTrainId());
            statement.setInt(4, carriage.getCarriageId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot update carriage  = " + (carriage == null ? null : carriage.toString()));
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_CARRIAGE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot delete carriage from database with carriage_id = " + id);
        }
    }

    @Override
    public List<Carriage> getCarriagesByTrainId(int trainId) {
        List<Carriage> carriages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_CARRIAGES_BY_TRAIN_ID)) {
            statement.setInt(1, trainId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                carriages.add(getCarriage(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot read list of carriages from database with train_id = " + trainId);
        }
        return carriages;
    }

    @Override
    public List<Carriage> getCarriagesByTrainIdAndType(int trainId, String type) {
        List<Carriage> carriages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_CARRIAGES_BY_TRAIN_ID_AND_TYPE)) {
            statement.setInt(1, trainId);
            statement.setString(2, type);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                carriages.add(getCarriage(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot read list of carriages from database with train_id = " + trainId
                    + " and type = " + type);
        }
        return carriages;
    }

    @Override
    public List<CarriageDTO> getAllCarriageDTOList() {
        List<CarriageDTO> carriageDTOList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_CARRIAGE)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                carriageDTOList.add(extractCarriageDTO(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot extract carriage transfer object list.", e);
        }
        return carriageDTOList;
    }

    private Carriage getCarriage(ResultSet rs) throws SQLException {
        return Carriage.builder()
                .carriageId(rs.getInt(Constants.ID))
                .type(CarriageType.valueOf(rs.getString(Constants.TYPE)))
                .number(rs.getString(Constants.NUMBER))
                .trainId(rs.getInt(Constants.TRAIN_ID))
                .build();
    }


    private CarriageDTO extractCarriageDTO(ResultSet rs) throws SQLException {
        CarriageDTO dto = new CarriageDTO();

        dto.setCarId(rs.getInt("c.id"));
        dto.setCarriageType(CarriageType.valueOf(rs.getString("type")));
        dto.setCarNumber(rs.getString("c.number"));
        dto.setTrainId(rs.getInt("train_id"));
        dto.setTrainNumber(rs.getString("t.number"));

        return dto;
    }
}
