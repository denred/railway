package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.repository.StationRepository;
import com.epam.redkin.railway.model.repository.TrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class StationRepositoryImplTest {
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
    void createStation() {
        StationRepository stationRepository = new StationRepositoryImpl(mockDataSource);
        try {
            when(mockDataSource.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
            when(mockStatement.executeUpdate()).thenReturn(1);
            when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
            doNothing().when(mockStatement).setString(anyInt(), anyString());
            when(mockStatement.executeUpdate()).thenReturn(1);
            when(mockResultSet.getInt(Statement.RETURN_GENERATED_KEYS)).thenReturn(1);

            assertEquals(1, stationRepository.create(Station.builder().build()));
            verify(mockConnection).prepareStatement(anyString(), anyInt());
            verify(mockStatement).setString(anyInt(), any());
            verify(mockStatement).executeUpdate();
            verify(mockResultSet).next();
            verify(mockResultSet).getInt(Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllStations() {
    }
}