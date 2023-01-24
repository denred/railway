package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.entity.Route;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class RouteRepositoryImplTest {
    @Mock
    private DataSource mockDataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;
    private RouteRepository routeRepository;


    @BeforeEach
    void setUp() throws SQLException {
        routeRepository = new RouteRepositoryImpl(mockDataSource);
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

        assertEquals(1, routeRepository.create(Route.builder().routeName("Central Route").build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).commit();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());

        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
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

        assertThrows(DataBaseException.class, () -> routeRepository.create(Route.builder().routeName("Central Route").build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection, times(0)).commit();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());

        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
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
        when(mockResultSet.getInt(anyString())).thenReturn(1);
        when(mockResultSet.getString(anyString())).thenReturn("RouteA");

        assertEquals(Route.builder().routeName("RouteA").trainId(1).routeId(1).routeNumber(1).build(), routeRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(3)).getInt(anyString());
        verify(mockResultSet).getString(anyString());
    }

    @Test
    void getByIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertNull(routeRepository.getById(1));
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
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routeRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(0)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(0)).executeQuery();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void updatePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeUpdate()).thenReturn(1);

        assertTrue(routeRepository.update(Route.builder().routeName("RouteA").build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(3)).setInt(anyInt(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeUpdate()).thenReturn(0);

        assertFalse(routeRepository.update(Route.builder().routeName("RouteA").build()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(3)).setInt(anyInt(), anyInt());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routeRepository.update(Route.builder().routeName("RouteA").build()));
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

        routeRepository.delete(1);
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

        routeRepository.delete(1);
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routeRepository.delete(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(0)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(0)).executeUpdate();
    }

    @Test
    void getAllRouteInfoDTOListPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1);
        when(mockResultSet.getString(anyString())).thenReturn("RouteName");


        assertEquals(2, routeRepository.getRouteInfoDTOList().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(4)).getInt(anyString());
        verify(mockResultSet, times(6)).getString(anyString());
    }

    @Test
    void getAllRouteInfoDTOListNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);


        assertEquals(0, routeRepository.getRouteInfoDTOList().size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getAllRouteInfoDTOListThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routeRepository.getRouteInfoDTOList());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(0)).executeQuery();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getRouteInfoDTOByRouteIdPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1);
        when(mockResultSet.getString(anyString())).thenReturn("A", "B", "C");


        assertEquals(RouteInfoDTO.builder()
                .routNumber("C")
                .routName("A")
                .trainNumber("B")
                .trainId(1)
                .routsId(1)
                .build(), routeRepository.getRouteInfoDTOByRouteId(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(2)).getInt(anyString());
        verify(mockResultSet, times(3)).getString(anyString());
    }

    @Test
    void getRouteInfoDTOByRouteIdNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertNull(routeRepository.getRouteInfoDTOByRouteId(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getRouteInfoDTOByRouteIdThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routeRepository.getRouteInfoDTOByRouteId(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(0)).setInt(anyInt(), anyInt());
        verify(mockStatement, times(0)).executeQuery();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
    }

    @Test
    void getStationDTOListWithParametersPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1, 2, 3, 4, 5);
        when(mockResultSet.getString(anyString())).thenReturn("A", "B", "C");
        when(mockResultSet.getObject(anyString(), eq(LocalDateTime.class))).thenReturn(LocalDateTime.now());

        assertEquals(2, routeRepository
                .getStationDTOListWithParameters("A", "B").size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setString(anyInt(), anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(10)).getInt(anyString());
        verify(mockResultSet, times(6)).getString(anyString());
        verify(mockResultSet, times(4)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getStationDTOListWithParametersNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, routeRepository
                .getStationDTOListWithParameters("A", "B").size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setString(anyInt(), anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }

    @Test
    void getStationDTOListWithParametersThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> routeRepository
                .getStationDTOListWithParameters("A", "B"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(0)).setString(anyInt(), anyString());
        verify(mockStatement, times(0)).executeQuery();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDateTime.class));
    }
}