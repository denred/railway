package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatRepositoryImplTest {
    @Mock
    private DataSource mockDataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;
    private SeatRepository seatRepository;


    @BeforeEach
    void setUp() throws SQLException {
        mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        seatRepository = new SeatRepositoryImpl(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void createSeatPositiveScenario() throws SQLException {
        doNothing().when(mockConnection).commit();
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).setAutoCommit(false);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(Statement.RETURN_GENERATED_KEYS)).thenReturn(1);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setString(anyInt(), anyString());

        assertEquals(1, seatRepository.create(Seat.builder().seatNumber("01").build()));
        verify(mockConnection).prepareStatement(anyString(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
        verify(mockConnection).commit();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(Statement.RETURN_GENERATED_KEYS);
    }

    @Test
    void createSeatNegativeScenario() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).setAutoCommit(false);
        when(mockStatement.executeUpdate()).thenReturn(1);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> seatRepository.create(Seat.builder().seatNumber("").build()));
        verify(mockConnection).prepareStatement(anyString(), anyInt());
        verify(mockStatement, times(1)).setString(anyInt(), anyString());
        verify(mockStatement, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement, times(1)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(1)).executeUpdate();
        verify(mockConnection, times(0)).commit();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(Statement.RETURN_GENERATED_KEYS);
        verify(mockConnection).rollback();
        verify(mockConnection).close();
    }

    @Test
    void getByIdPositiveScenario() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(10);
        when(mockResultSet.getString(anyString())).thenReturn("07");
        when(mockResultSet.getBoolean(anyString())).thenReturn(true);
        Seat seat = Seat.builder().seatNumber("07").busy(true).carriageId(10).id(10).build();

        assertEquals(seatRepository.getById(10), seat);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getString(anyString());
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet).getBoolean(anyString());
    }

    @Test
    void getByIdNegativeScenario() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertNull(seatRepository.getById(1));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());
    }

    @Test
    void updatePositiveScenario() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        when(mockStatement.executeUpdate()).thenReturn(1);

        assertTrue(seatRepository.update(Seat.builder().seatNumber("11").build()));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateNegativeScenario() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        when(mockStatement.executeUpdate()).thenReturn(0);

        assertFalse(seatRepository.update(Seat.builder().seatNumber("11").build()));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> seatRepository.update(Seat.builder().seatNumber("11").build()));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deletePositiveScenario() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        seatRepository.delete(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteNegativeScenario() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(0);

        seatRepository.delete(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteAllSeatsByCarriageIdPositiveScenario() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        seatRepository.deleteAllSeatsByCarriageId(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteAllSeatsByCarriageIdNegativeScenario() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(0);

        seatRepository.deleteAllSeatsByCarriageId(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void getSeatsCountByCarriageIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(10);

        seatRepository.getSeatsCountByCarriageId(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(anyString());
    }

    @Test
    void getBusySeatsCountByCarriageIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(10);

        seatRepository.getBusySeatsCountByCarriageId(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(anyString());
    }

    @Test
    void getSeatsCountByCarriageIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        seatRepository.getSeatsCountByCarriageId(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
    }

    @Test
    void getBusySeatsCountByCarriageIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        seatRepository.getBusySeatsCountByCarriageId(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
    }


    @Test
    void getSeatsCountByTrainIdAndByTypesPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(10);

        seatRepository.getSeatsCountByTrainIdAndByTypes(1, CarriageType.FIRST_CLASS);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(anyString());
    }

    @Test
    void getSeatsCountByTrainIdAndByTypesNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        seatRepository.getSeatsCountByTrainIdAndByTypes(1, CarriageType.FIRST_CLASS);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
    }

    @Test
    void getSeatsCountByTrainIdAndByTypesException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());

        assertThrows(DataBaseException.class, () -> seatRepository.getSeatsCountByTrainIdAndByTypes(1, null));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement, times(0)).executeQuery();
        verify(mockResultSet, times(0)).next();
    }

    @Test
    void getListSeatsByCarriageIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(10);
        when(mockResultSet.getString(anyString())).thenReturn("04");
        when(mockResultSet.getBoolean(anyString())).thenReturn(Boolean.FALSE);


        seatRepository.getListSeatsByCarriageId(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
        verify(mockResultSet, times(2)).getBoolean(anyString());
    }

    @Test
    void getListSeatsByCarriageIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        seatRepository.getListSeatsByCarriageId(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());
    }

    @Test
    void getListSeatsByCarriageIdWithException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> seatRepository.getListSeatsByCarriageId(1));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getBoolean(anyString());
    }

    @Test
    void getListSeatsByIdBatchPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(10);
        when(mockResultSet.getString(anyString())).thenReturn("04");
        when(mockResultSet.getBoolean(anyString())).thenReturn(Boolean.FALSE);


        seatRepository.getListSeatsByIdBatch(List.of("1", "2", "3"));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
        verify(mockResultSet, times(2)).getBoolean(anyString());
    }

    @Test
    void getListSeatsByIdBatchNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        seatRepository.getListSeatsByIdBatch(List.of("1", "2", "3"));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
    }

    @Test
    void getListSeatsByIdBatchWithException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> seatRepository.getListSeatsByIdBatch(List.of("1", "2", "3")));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
    }

    @Test
    void reservedSeatPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).commit();
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE,Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(10);
        when(mockResultSet.getString(anyString())).thenReturn("07");
        when(mockResultSet.getBoolean(anyString())).thenReturn(false);
        when(mockStatement.executeUpdate()).thenReturn(1);

        seatRepository.reservedSeat(1);
        verify(mockConnection, times(2)).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).commit();
        verify(mockConnection, times(2)).close();
        verify(mockStatement, times(2)).close();
        verify(mockStatement).executeUpdate();
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getString(anyString());
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet).getBoolean(anyString());
    }

    @Test
    void reservedSeatWithException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).rollback();
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE,Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(10);
        when(mockResultSet.getString(anyString())).thenReturn("07");
        when(mockResultSet.getBoolean(anyString())).thenReturn(true);

        assertThrows(DataBaseException.class, () -> seatRepository.reservedSeat(1));
        verify(mockConnection, times(2)).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).rollback();
        verify(mockConnection, times(2)).close();
        verify(mockStatement, times(2)).close();
        verify(mockStatement, times(0)).executeUpdate();
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getString(anyString());
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet).getBoolean(anyString());
    }

    @Test
    void clearSeatPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        seatRepository.clearSeat(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void clearSeatNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(0);

        seatRepository.clearSeat(1);
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void clearSeatWithException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> seatRepository.clearSeat(1));
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }
}