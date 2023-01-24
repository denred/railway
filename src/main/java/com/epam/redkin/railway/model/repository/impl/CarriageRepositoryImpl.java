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
                throw new DataBaseException("Connection closing error: ", e);
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
        LOGGER.info("Started public boolean update(Carriage carriage), carriage: " + carriage);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_CARRIAGE)) {
            statement.setString(1, carriage.getType().toString());
            statement.setString(2, carriage.getNumber());
            statement.setInt(3, carriage.getTrainId());
            statement.setInt(4, carriage.getCarriageId());
            boolean state = statement.executeUpdate() > 0;
            LOGGER.info("Carriage updated: " + state);
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
        LOGGER.info("Started public List<CarriageDTO> getAllCarriageDTOList()");
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

    private Carriage getCarriage(ResultSet rs) throws SQLException {
        return Carriage.builder()
                .carriageId(rs.getInt(Constants.ID))
                .type(CarriageType.valueOf(rs.getString(Constants.TYPE)))
                .number(rs.getString(Constants.NUMBER))
                .trainId(rs.getInt(Constants.TRAIN_ID))
                .build();
    }


    private CarriageDTO extractCarriageDTO(ResultSet rs) throws SQLException {
        return CarriageDTO.builder()
                .carId(rs.getInt(Constants.CARRIAGE_ID))
                .carriageType(CarriageType.valueOf(rs.getString(Constants.TYPE)))
                .carNumber(rs.getString(Constants.CARRIAGE_NUMBER))
                .trainId(rs.getInt(Constants.TRAIN_ID))
                .trainNumber(rs.getString(Constants.TRAIN_NUMBER))
                .build();
    }
}
