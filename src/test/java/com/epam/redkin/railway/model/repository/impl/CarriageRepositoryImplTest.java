package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.Carriage;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Seat;
import com.epam.redkin.railway.model.repository.CarriageRepository;
import com.epam.redkin.railway.model.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class CarriageRepositoryImplTest {
    @Mock
    private DataSource mockDataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;
    @InjectMocks
    private CarriageRepositoryImpl underTest;


    @BeforeEach
    void setUp() {
        mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    void createCarriage() {
        CarriageRepository carriageRepository = new CarriageRepositoryImpl(mockDataSource);
        try {
            when(mockDataSource.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
            when(mockStatement.executeUpdate()).thenReturn(1);
            when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
            doNothing().when(mockStatement).setInt(anyInt(), anyInt());
            doNothing().when(mockStatement).setString(anyInt(), any());
            when(mockStatement.executeUpdate()).thenReturn(1);
            doNothing().when(mockConnection).commit();
            when(mockResultSet.getInt(Statement.RETURN_GENERATED_KEYS)).thenReturn(1);

            assertEquals(1, carriageRepository.create(Carriage.builder().type(CarriageType.FIRST_CLASS).build()));
            verify(mockConnection).prepareStatement(anyString(), anyInt());
            verify(mockStatement).setString(anyInt(), anyString());
            verify(mockStatement).setInt(anyInt(), anyInt());
            verify(mockStatement).executeUpdate();
            verify(mockConnection).commit();
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
    void getCarriagesByTrainId() {
    }

    @Test
    void getCarriagesByTrainIdAndType() {
    }

    @Test
    void getAllCarriageDTOList() {
    }
}