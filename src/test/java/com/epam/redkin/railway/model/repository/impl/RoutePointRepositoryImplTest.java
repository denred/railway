package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.RoutePoint;
import com.epam.redkin.railway.model.repository.RoutePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class RoutePointRepositoryImplTest {

    @Mock
    private DataSource mockDataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;


    @BeforeEach
    void setUp() {
        mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    void createCarriage() {
        RoutePointRepository routePointRepository = new RoutePointRepositoryImpl(mockDataSource);
        try {
            when(mockDataSource.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeUpdate()).thenReturn(1);
            when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
            doNothing().when(mockStatement).setObject(anyInt(), any(LocalDateTime.class));
            doNothing().when(mockStatement).setInt(anyInt(), anyInt());
            when(mockStatement.executeUpdate()).thenReturn(1);
            doNothing().when(mockConnection).commit();
            when(mockResultSet.getInt(Statement.RETURN_GENERATED_KEYS)).thenReturn(1);

            routePointRepository.create(RoutePoint.builder().dispatch(LocalDateTime.now()).arrival(LocalDateTime.now()).build());
            verify(mockConnection).prepareStatement(anyString());
            verify(mockStatement,times(2)).setObject(anyInt(), any(LocalDateTime.class));
            verify(mockStatement, times(3)).setInt(anyInt(), anyInt());
            verify(mockStatement).executeUpdate();
            verify(mockConnection).commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getRoutePointList() {
    }


    @Test
    void getRoutePointListByRouteId() {
    }

    @Test
    void updateRoutePointByStationId() {
    }

    @Test
    void deleteRoutePointByStationId() {
    }

    @Test
    void getMappingInfoListByRouteId() {
    }

    @Test
    void getMappingInfo() {
    }
}