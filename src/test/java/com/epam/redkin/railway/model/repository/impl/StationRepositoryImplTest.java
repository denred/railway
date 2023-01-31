package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StationRepositoryImplTest {
    @Mock
    private DataSource mockDataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;
    private StationRepository stationRepository;


    @BeforeEach
    void setUp() throws SQLException {
        mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        stationRepository = new StationRepositoryImpl(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void createStationPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(Statement.RETURN_GENERATED_KEYS)).thenReturn(1);

        assertEquals(1, stationRepository.create(Station.builder().station("StationA").build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeUpdate();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(Statement.RETURN_GENERATED_KEYS);
    }

    @Test
    void createStationNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeUpdate()).thenReturn(0);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(-1, stationRepository.create(Station.builder().station("StationA").build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeUpdate();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(Statement.RETURN_GENERATED_KEYS);
    }

    @Test
    void createStationThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> stationRepository.create(Station.builder().station("StationA").build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString(), anyInt());
        verify(mockStatement, times(0)).setString(anyInt(), anyString());
        verify(mockStatement, times(0)).executeUpdate();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(Statement.RETURN_GENERATED_KEYS);
    }

    @Test
    void getByIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1);
        when(mockResultSet.getString(anyString())).thenReturn("StationA");

        assertEquals(Station.builder().station("StationA").id(1).build(), stationRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(anyString());
        verify(mockResultSet).getString(anyString());
    }

    @Test
    void getByIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertNull(stationRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
    }

    @Test
    void getByIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> stationRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(0)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(0)).executeQuery();
        verify(mockResultSet, times(0)).next();
    }

    @Test
    void updatePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeUpdate()).thenReturn(1);

        assertTrue(stationRepository.update(Station.builder().station("StationA").id(1).build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeUpdate()).thenReturn(0);

        assertFalse(stationRepository.update(Station.builder().station("StationA").id(1).build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> stationRepository.update(Station.builder().station("StationA").id(1).build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(0)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(0)).setString(anyInt(), anyString());
        verify(mockStatement, times(0)).executeUpdate();
    }

    @Test
    void deletePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        stationRepository.delete(1);
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(0);

        stationRepository.delete(1);
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> stationRepository.delete(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(0)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(0)).executeUpdate();
    }

    @Test
    void getAllStationsPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1);
        when(mockResultSet.getString(anyString())).thenReturn("StationB");

        assertEquals(2, stationRepository.getAllStations().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
    }

    @Test
    void getAllStationsNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, stationRepository.getAllStations().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getAllStationsThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> stationRepository.getAllStations());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(0)).executeQuery();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getStationsWithFilterPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2);
        when(mockResultSet.getString(anyString())).thenReturn("StationA", "StationB");

        assertEquals(2, stationRepository.getStationsWithFilter(0, 5, "anyString").size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
    }

    @Test
    void getStationsWithFilterNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, stationRepository.getStationsWithFilter(0, 5, "anyString").size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getStationsWithFilterThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> stationRepository.getStationsWithFilter(0, 5, "anyString").size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getCountStationWithSearchPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(15);

        assertEquals(15, stationRepository.getCountStationWithSearch("anyString"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(anyString());
    }

    @Test
    void getCountStationWithSearchNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, stationRepository.getCountStationWithSearch("anyString"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
    }

    @Test
    void getCountStationWithSearchThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> stationRepository.getCountStationWithSearch("anyString"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
    }
}