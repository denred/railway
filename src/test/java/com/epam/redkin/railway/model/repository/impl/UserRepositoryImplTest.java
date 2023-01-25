package com.epam.redkin.railway.model.repository.impl;

import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.Route;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {
    @Mock
    private DataSource mockDataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException {
        userRepository = new UserRepositoryImpl(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testCreateUserPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).commit();
        doNothing().when(mockConnection).close();
        doNothing().when(mockStatement).close();
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setObject(anyInt(), any());
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(Statement.RETURN_GENERATED_KEYS)).thenReturn(1);
        doNothing().when(mockResultSet).close();

        assertEquals(1, userRepository.create(createTestUser()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).commit();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());

        verify(mockStatement, times(7)).setString(anyInt(), anyString());
        verify(mockStatement, times(1)).setObject(anyInt(), any());
        verify(mockStatement, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement).executeUpdate();
        verify(mockStatement).close();

        verify(mockResultSet).next();
        verify(mockResultSet).getInt(Statement.RETURN_GENERATED_KEYS);
        verify(mockResultSet).close();
    }

    @Test
    void testCreateUserNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).commit();
        doNothing().when(mockConnection).close();
        doNothing().when(mockStatement).close();
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setObject(anyInt(), any());
        when(mockStatement.executeUpdate()).thenReturn(0);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(-1, userRepository.create(createTestUser()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).commit();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());

        verify(mockStatement, times(7)).setString(anyInt(), anyString());
        verify(mockStatement, times(1)).setObject(anyInt(), any());
        verify(mockStatement, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement).executeUpdate();
        verify(mockStatement).close();

        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(Statement.RETURN_GENERATED_KEYS);
        verify(mockResultSet, times(1)).close();
    }

    @Test
    void testCreateUserThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).rollback();
        doNothing().when(mockConnection).close();
        doNothing().when(mockStatement).close();
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setObject(anyInt(), any());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);


        assertThrows(DataBaseException.class, () -> userRepository.create(createTestUser()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).rollback();
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString(), anyInt());

        verify(mockStatement, times(7)).setString(anyInt(), anyString());
        verify(mockStatement, times(1)).setObject(anyInt(), any());
        verify(mockStatement, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement, times(1)).executeUpdate();
        verify(mockStatement).close();

        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getInt(Statement.RETURN_GENERATED_KEYS);
        verify(mockResultSet, times(0)).close();
    }


    @Test
    void testGetByIdUserThrowException() throws SQLException {
        when(mockResultSet.getString(anyString())).thenThrow(new DataBaseException());
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        doNothing().when(mockStatement).close();
        when(mockConnection.prepareStatement((String) any())).thenReturn(mockStatement);
        doNothing().when(mockConnection).close();
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        //verify and assert
        assertThrows(DataBaseException.class, () -> (new OrderRepositoryImpl(mockDataSource)).getById(1));
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
    void testGetByIdUserPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1);
        when(mockResultSet.getString(anyString())).thenReturn("test@mail.com", "123", "John", "Smith", "+44", "USER", "qwerty");
        when(mockResultSet.getObject(anyString(), eq(LocalDate.class))).thenReturn(LocalDate.of(1, 2, 3));
        when(mockResultSet.getBoolean(anyString())).thenReturn(true);

        assertEquals(createTestUser(), userRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(1)).getInt(anyString());
        verify(mockResultSet, times(7)).getString(anyString());
        verify(mockResultSet, times(1)).getObject(anyString(), eq(LocalDate.class));
        verify(mockResultSet, times(1)).getBoolean(anyString());
    }

    @Test
    void testGetByIdUserNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);


        assertNull(userRepository.getById(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
        verify(mockResultSet, times(0)).getBoolean(anyString());
    }


    @Test
    void updatePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).commit();
        doNothing().when(mockConnection).close();
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setObject(anyInt(), any());
        when(mockStatement.executeUpdate()).thenReturn(1);

        assertTrue(userRepository.update(createTestUser()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockConnection).commit();

        verify(mockStatement, times(7)).setString(anyInt(), anyString());
        verify(mockStatement, times(1)).setObject(anyInt(), any());
        verify(mockStatement, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement, times(1)).executeUpdate();
        verify(mockStatement).close();
    }

    @Test
    void updateNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).commit();
        doNothing().when(mockConnection).close();
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setObject(anyInt(), any());
        when(mockStatement.executeUpdate()).thenReturn(0);

        assertFalse(userRepository.update(createTestUser()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockConnection).commit();

        verify(mockStatement, times(7)).setString(anyInt(), anyString());
        verify(mockStatement, times(1)).setObject(anyInt(), any());
        verify(mockStatement, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement, times(1)).executeUpdate();
        verify(mockStatement).close();
    }

    @Test
    void updateThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).setAutoCommit(true);
        doNothing().when(mockConnection).close();
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setObject(anyInt(), any());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> userRepository.update(createTestUser()));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
        verify(mockConnection).close();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockConnection).rollback();

        verify(mockStatement, times(7)).setString(anyInt(), anyString());
        verify(mockStatement, times(1)).setObject(anyInt(), any());
        verify(mockStatement, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement, times(1)).executeUpdate();
        verify(mockStatement).close();
    }

    @Test
    void deletePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);
        userRepository.delete(1);

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

        assertThrows(DataBaseException.class, () -> userRepository.delete(1));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void getUserByEmailPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1);
        when(mockResultSet.getString(anyString())).thenReturn("test@mail.com", "123", "John", "Smith", "+44", "USER", "qwerty");
        when(mockResultSet.getObject(anyString(), eq(LocalDate.class))).thenReturn(LocalDate.of(1, 2, 3));
        when(mockResultSet.getBoolean(anyString())).thenReturn(true);

        assertEquals(createTestUser(), userRepository.getUserByEmail("email"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(1)).getInt(anyString());
        verify(mockResultSet, times(7)).getString(anyString());
        verify(mockResultSet, times(1)).getObject(anyString(), eq(LocalDate.class));
        verify(mockResultSet, times(1)).getBoolean(anyString());
    }

    @Test
    void getUserByEmailNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertNull(userRepository.getUserByEmail("email"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
        verify(mockResultSet, times(0)).getBoolean(anyString());
    }

    @Test
    void getUserByEmailThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> userRepository.getUserByEmail("email"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
        verify(mockResultSet, times(0)).getBoolean(anyString());
    }


    @Test
    void checkUserByEmailPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);

        assertTrue(userRepository.checkUserByEmail("john"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
    }

    @Test
    void checkUserByEmailNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertFalse(userRepository.checkUserByEmail("john"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
    }

    @Test
    void checkUserByEmailThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> userRepository.checkUserByEmail("john"));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement).executeQuery();
        verify(mockResultSet).next();
    }

    @Test
    void getUsersByRolePositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyString())).thenReturn(1);
        when(mockResultSet.getString(anyString())).thenReturn("test@mail.com", "123", "John", "Smith", "+44", "USER", "qwerty");
        when(mockResultSet.getObject(anyString(), eq(LocalDate.class))).thenReturn(LocalDate.of(1, 2, 3));
        when(mockResultSet.getBoolean(anyString())).thenReturn(true);

        assertEquals(1, userRepository.getUsersByRole(Role.USER.name(), 1, 1, new HashMap<>()).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet, times(1)).getInt(anyString());
        verify(mockResultSet, times(7)).getString(anyString());
        verify(mockResultSet, times(1)).getObject(anyString(), eq(LocalDate.class));
        verify(mockResultSet, times(1)).getBoolean(anyString());
    }

    @Test
    void getUsersByRoleNegative() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        assertEquals(0, userRepository.getUsersByRole(Role.USER.name(), 1, 1, new HashMap<>()).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
        verify(mockResultSet, times(0)).getBoolean(anyString());
    }

    @Test
    void getUsersByRoleThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setString(anyInt(), anyString());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> userRepository.getUsersByRole(Role.USER.name(), 1, 1, new HashMap<>()).size());
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setString(anyInt(), anyString());
        verify(mockStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getInt(anyString());
        verify(mockResultSet, times(0)).getString(anyString());
        verify(mockResultSet, times(0)).getObject(anyString(), eq(LocalDate.class));
        verify(mockResultSet, times(0)).getBoolean(anyString());
    }

    @Test
    void updateBlockedPositive() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenReturn(1);

        userRepository.updateBlocked(1, true);
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Test
    void updateBlockedThrowException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doNothing().when(mockStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(mockStatement).setInt(anyInt(), anyInt());
        when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(DataBaseException.class, () -> userRepository.updateBlocked(1, true));
        verify(mockDataSource).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).setBoolean(anyInt(), anyBoolean());
        verify(mockStatement).setInt(anyInt(), anyInt());
        verify(mockStatement).executeUpdate();
    }

    @Disabled
    @Test
    void updateRememberUserToken() {
    }
    @Disabled
    @Test
    void findUserByIdAndToken() {
    }
    @Disabled
    @Test
    void deleteRememberUserToken() {
    }
    @Disabled
    @Test
    void getUserCount() {
    }

    private User createTestUser() {
        return User.builder()
                .userId(1)
                .email("test@mail.com")
                .firstName("John")
                .lastName("Smith")
                .password("123")
                .phone("+44")
                .birthDate(LocalDate.of(1, 2, 3))
                .blocked(true)
                .role(Role.USER)
                .token("qwerty")
                .build();
    }
}