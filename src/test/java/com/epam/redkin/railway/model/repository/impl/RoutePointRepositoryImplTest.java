package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.dto.MappingInfoDTO;
import com.epam.redkin.railway.model.entity.RoutePoint;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.RoutePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoutePointRepositoryImplTest {

    @Mock
    private DataSource mockDataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;
    private RoutePointRepository routePointRepository;


    @BeforeEach
    void setUp() throws SQLException {
        routePointRepository = new RoutePointRepositoryImpl(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void createCarriagePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockConnection).commit();
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockStatement).setObject(anyInt(), any(LocalDateTime.class));
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        routePointRepository.create(RoutePoint.builder()
                .dispatch(LocalDateTime.now())
                .arrival(LocalDateTime.now())
                .build());
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).commit();
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).close();
        verify(mockConnection, times(0)).rollback();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).close();
        verify(mockStatement, times(2)).setObject(anyInt(), any(LocalDateTime.class));
        verify(mockStatement, times(3)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void createCarriageThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockStatement).setObject(anyInt(), any(LocalDateTime.class));
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routePointRepository
                .create(RoutePoint.builder().dispatch(LocalDateTime.now())
                        .arrival(LocalDateTime.now())
                        .build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection, times(0)).commit();
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).close();
        verify(mockConnection).rollback();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).close();
        verify(mockStatement, times(2)).setObject(anyInt(), any(LocalDateTime.class));
        verify(mockStatement, times(3)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void getRoutePointListPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2, 3, 4, 5, 6);
        when(mockResultSet.getObject(anyString(), eq(LocalDateTime.class))).thenReturn(
                LocalDateTime.of(2022, 12, 31, 23, 33),
                LocalDateTime.of(2022, 12, 30, 23, 33),
                LocalDateTime.of(2022, 12, 29, 23, 33),
                LocalDateTime.of(2022, 12, 28, 23, 33)
        );

        assertEquals(2, routePointRepository.getRoutePointList().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(6)).getInt(anyString());
        verify(mockResultSet, times(4)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getRoutePointListNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, routePointRepository.getRoutePointList().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getRoutePointListThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routePointRepository.getRoutePointList());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }


    @Test
    void getRoutePointListByRouteIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2, 3, 4, 5, 6);
        when(mockResultSet.getObject(anyString(), eq(LocalDateTime.class))).thenReturn(
                LocalDateTime.of(2022, 12, 31, 23, 33),
                LocalDateTime.of(2022, 12, 30, 23, 33),
                LocalDateTime.of(2022, 12, 29, 23, 33),
                LocalDateTime.of(2022, 12, 28, 23, 33)
        );

        assertEquals(2, routePointRepository.getRoutePointListByRouteId(1).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(6)).getInt(anyString());
        verify(mockResultSet, times(4)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getRoutePointListByRouteIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, routePointRepository.getRoutePointListByRouteId(1).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getRoutePointListByRouteIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routePointRepository.getRoutePointListByRouteId(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void updateRoutePointByStationIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setObject(anyInt(), any());
        when(mockStatement.executeUpdate()).thenReturn(1);

        assertTrue(routePointRepository.updateRoutePointByStationId(RoutePoint.builder().build(), 1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(4)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(2)).setObject(anyInt(), any());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateRoutePointByStationIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setObject(anyInt(), any());
        when(mockStatement.executeUpdate()).thenReturn(0);

        assertFalse(routePointRepository.updateRoutePointByStationId(RoutePoint.builder().build(), 1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(4)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(2)).setObject(anyInt(), any());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateRoutePointByStationIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setObject(anyInt(), any());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routePointRepository.updateRoutePointByStationId(RoutePoint.builder().build(), 1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(4)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(2)).setObject(anyInt(), any());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteRoutePointByStationIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        assertTrue(routePointRepository.deleteRoutePointByStationId(1, 1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteRoutePointByStationIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(0);

        assertFalse(routePointRepository.deleteRoutePointByStationId(1, 1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteRoutePointByStationIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routePointRepository.deleteRoutePointByStationId(1, 1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void getMappingInfoListByRouteIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2, 3, 4);
        when(mockResultSet.getString(anyString())).thenReturn("A", "B");
        when(mockResultSet.getObject(anyString(), eq(LocalDateTime.class))).thenReturn(
                LocalDateTime.of(2022, 12, 31, 23, 33),
                LocalDateTime.of(2022, 12, 30, 23, 33),
                LocalDateTime.of(2022, 12, 29, 23, 33),
                LocalDateTime.of(2022, 12, 28, 23, 33)
        );

        assertEquals(2, routePointRepository.getMappingInfoListByRouteId(1).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(6)).getInt(anyString());
        verify(mockResultSet, times(2)).getString(anyString());
        verify(mockResultSet, times(4)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getMappingInfoListByRouteIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, routePointRepository.getMappingInfoListByRouteId(1).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getMappingInfoListByRouteIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routePointRepository.getMappingInfoListByRouteId(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getMappingInfoPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2, 3, 4);
        when(mockResultSet.getString(anyString())).thenReturn("A", "B");
        when(mockResultSet.getObject(anyString(), eq(LocalDateTime.class))).thenReturn(
                LocalDateTime.of(2022, 12, 31, 23, 33),
                LocalDateTime.of(2022, 12, 30, 23, 33),
                LocalDateTime.of(2022, 12, 29, 23, 33),
                LocalDateTime.of(2022, 12, 28, 23, 33)
        );

        assertEquals(MappingInfoDTO.builder()
                .stationId("1")
                .routsId("2")
                .stationArrivalDate(LocalDateTime.of(2022, 12, 31, 23, 33))
                .stationDispatchData(LocalDateTime.of(2022, 12, 30, 23, 33))
                .station("A")
                .order(3)
                .build(), routePointRepository.getMappingInfo(1, 1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(3)).getInt(anyString());
        verify(mockResultSet, times(1)).getString(anyString());
        verify(mockResultSet, times(2)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getMappingInfoNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);


        assertNull(routePointRepository.getMappingInfo(1, 1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getMappingInfoThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routePointRepository.getMappingInfo(1, 1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }

}