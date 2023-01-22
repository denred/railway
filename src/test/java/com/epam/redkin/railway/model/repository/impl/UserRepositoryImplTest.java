package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.builder.UserBuilder;
import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.DAOException;
import com.epam.redkin.railway.model.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest {
    @Mock DataSource mockDataSource = mock(DataSource.class);
    @Mock Connection mockConnection = mock(Connection.class);
    @Mock PreparedStatement mockStatement = mock(PreparedStatement.class);
    @Mock ResultSet mockResultSet = mock(ResultSet.class);
    private UserRepository userRepository;
    int userId = 10;

    @BeforeEach
    void setUp() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockDataSource.getConnection(anyString(), anyString())).thenReturn(mockConnection);
        doNothing().when(mockConnection).commit();
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setObject(anyInt(), (Object) anyString());
        doNothing().when(mockStatement).setObject(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(Statement.RETURN_GENERATED_KEYS)).thenReturn(userId);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreateUser() throws SQLException {
        userRepository = new UserRepositoryImpl(mockDataSource);
        userRepository.create(createTestUser());
        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockStatement, times(7)).setString(anyInt(), anyString());
        verify(mockStatement, times(1)).executeUpdate();
        verify(mockConnection, times(1)).commit();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(Statement.RETURN_GENERATED_KEYS);
    }

    @Test
    public void testCreateWithException() throws SQLException {
        userRepository = new UserRepositoryImpl(mockDataSource);
        assertThrows(DAOException.class, () -> userRepository.create(new User()));
        //verify and assert
        verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockStatement, times(0)).setString(anyInt(), anyString());
        verify(mockStatement, times(0)).executeUpdate();
        verify(mockConnection, times(0)).commit();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(Statement.RETURN_GENERATED_KEYS);

    }

    @Test
    void testGetByIdUser() throws SQLException {
        when(mockResultSet.getString(anyString())).thenThrow(new DAOException());
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        doNothing().when(mockStatement).close();
        when(mockConnection.prepareStatement((String) any())).thenReturn(mockStatement);
        doNothing().when(mockConnection).close();
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        //verify and assert
        assertThrows(DAOException.class, () -> (new OrderRepositoryImpl(mockDataSource)).getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockConnection).close();
        verify(mockStatement).executeQuery();
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).close();
        verify(mockResultSet).next();
        verify(mockResultSet).getString(anyString());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void checkUserByEmail() {
    }

    @Test
    void getUsersByRole() {
    }

    @Test
    void updateBlocked() {
    }

    @Test
    void updateRememberUserToken() {
    }

    @Test
    void findUserByIdAndToken() {
    }

    @Test
    void deleteRememberUserToken() {
    }

    @Test
    void getUserCount() {
    }

    private User createTestUser() {
        return new UserBuilder()
                .setEmail("test@mail.com")
                .setFirstName("John")
                .setLastName("Smith")
                .setPassword("123")
                .setPhone("+380441233344")
                .setBirthDate(LocalDate.now())
                .setBlocked(true)
                .setRole(Role.USER)
                .setLogInToken("qwerty")
                .build();
    }
}