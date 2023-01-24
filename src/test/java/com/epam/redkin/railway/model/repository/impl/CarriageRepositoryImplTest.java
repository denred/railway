package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.CarriageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CarriageRepositoryImplTest {
    @Mock
    private DataSource mockDataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    private CarriageRepository carriageRepository;


    @BeforeEach
    void setUp() throws SQLException {
        carriageRepository = new CarriageRepositoryImpl(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void createCarriagePositive() throws SQLException {
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);
        doNothing().when(mockConnection).commit();
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(Statement.RETURN_GENERATED_KEYS)).thenReturn(1);
        doNothing().when(mockResultSet).close();
        doNothing().when(mockStatement).close();
        doNothing().when(mockConnection).close();

        assertEquals(1, carriageRepository.create(Carriage.builder().type(CarriageType.FIRST_CLASS).build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).commit();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());

        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
        verify(mockStatement).close();

        verify(mockResultSet).next();
        verify(mockResultSet).getInt(Statement.RETURN_GENERATED_KEYS);
        verify(mockResultSet).close();
    }

    @Test
    void createCarriageThrowException() throws SQLException {
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);
        doNothing().when(mockStatement).close();
        doNothing().when(mockConnection).close();

        assertThrows(DataBaseException.class, () -> carriageRepository.create(Carriage.builder().type(CarriageType.FIRST_CLASS).build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection, times(0)).commit();
        verify(mockConnection).rollback();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
        verify(mockStatement).close();

        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(Statement.RETURN_GENERATED_KEYS);
        verify(mockResultSet, times(0)).close();
    }

    @Test
    void getByIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2);
        when(mockResultSet.getString(anyString())).thenReturn(CarriageType.FIRST_CLASS.name(), "08");

        assertEquals(Carriage.builder().carriageId(1)
                        .type(CarriageType.FIRST_CLASS)
                        .number("08")
                        .trainId(2)
                        .build(),
                carriageRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
    }

    @Test
    void getByIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertNull(carriageRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getByIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> carriageRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void updatePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        assertTrue(carriageRepository.update(Carriage.builder().type(CarriageType.FIRST_CLASS).build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(1)).setString(anyInt(), anyString());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(0);

        assertFalse(carriageRepository.update(Carriage.builder().type(CarriageType.FIRST_CLASS).build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(1)).setString(anyInt(), anyString());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> carriageRepository.update(Carriage.builder().type(CarriageType.FIRST_CLASS).build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(1)).setString(anyInt(), anyString());
        verify(mockStatement).executeUpdate();
    }


    @Test
    void deletePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        carriageRepository.delete(1);
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> carriageRepository.delete(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void getCarriagesByTrainIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2);
        when(mockResultSet.getString(anyString())).thenReturn(CarriageType.FIRST_CLASS.name(), "08", CarriageType.FIRST_CLASS.name(), "08");

        assertEquals(2,
                carriageRepository.getCarriagesByTrainId(1).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(4)).getString(anyString());
    }

    @Test
    void getCarriagesByTrainIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0,
                carriageRepository.getCarriagesByTrainId(1).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getCarriagesByTrainIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> carriageRepository.getCarriagesByTrainId(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getCarriagesByTrainIdAndTypePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2);
        when(mockResultSet.getString(anyString())).thenReturn(CarriageType.FIRST_CLASS.name(), "08", CarriageType.FIRST_CLASS.name(), "08");

        assertEquals(2,
                carriageRepository.getCarriagesByTrainIdAndType(2, CarriageType.FIRST_CLASS.name()).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(4)).getString(anyString());
    }

    @Test
    void getCarriagesByTrainIdAndTypeNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0,
                carriageRepository.getCarriagesByTrainIdAndType(2, CarriageType.FIRST_CLASS.name()).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getCarriagesByTrainIdAndTypeThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () ->
                carriageRepository.getCarriagesByTrainIdAndType(2, CarriageType.FIRST_CLASS.name()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getAllCarriageDTOListPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2);
        when(mockResultSet.getString(anyString())).thenReturn(
                CarriageType.FIRST_CLASS.name(), "08", "122",
                CarriageType.FIRST_CLASS.name(), "09", "122");

        assertEquals(2,
                carriageRepository.getCarriageDTOList().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(6)).getString(anyString());
    }

    @Test
    void getAllCarriageDTOListNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0,
                carriageRepository.getCarriageDTOList().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getAllCarriageDTOListThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> carriageRepository.getCarriageDTOList());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }
}