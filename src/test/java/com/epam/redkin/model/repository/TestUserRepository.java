package com.epam.redkin.model.repository;

import com.epam.redkin.model.connectionpool.ConnectionPools;
import com.epam.redkin.model.entity.Role;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.DataBaseException;
import com.epam.redkin.model.repository.impl.UserRepositoryImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserRepository implements TestConstants {
    private static Connection connection;
    private UserRepository userRepository;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = ConnectionPools.getTransactional().getConnection();
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void init() throws SQLException {
        userRepository = new UserRepositoryImpl(ConnectionPools.getTransactional());
        connection.createStatement().executeUpdate(CREATE_USER_TABLE);
        connection.createStatement().executeUpdate(INSERT_USER1);
        connection.createStatement().executeUpdate(INSERT_USER2);
        connection.createStatement().executeUpdate(INSERT_USER3);
    }

    @AfterEach
    public void remove() throws SQLException {
        connection.createStatement().executeUpdate(REMOVE_USER_TABLE);
    }


    @RepeatedTest(100)
    void testCreateAndReadUser() {
        String email = RandomStringUtils.random(245, true, true) + "@mail.com";
        String password = RandomStringUtils.random(64, true, true);
        String firstName = RandomStringUtils.random(60, true, false);
        String lastName = RandomStringUtils.random(60, true, false);
        String phone = "+" + RandomStringUtils.random(19, true, false);
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate birthDate = LocalDate.ofEpochDay(randomDay);
        Role role = Role.values()[new Random().nextInt(Role.values().length)];
        boolean blocked = (new Random()).nextBoolean();

        User actual = new User(email, password, firstName, lastName, phone, birthDate, role, blocked);
        int id = userRepository.create(actual);
        User expected = userRepository.read(id);

        assertEquals(expected, actual);
    }


    static Stream<Arguments> arguments = Stream.of(
            Arguments.of(null, "password", "firstName", "lastName", "+387770984344", LocalDate.now(), Role.USER, false),
            Arguments.of("email@email", null, "firstName", "lastName", "+387770984344", LocalDate.now(), Role.USER, false),
            Arguments.of("email@email", "password", null, "lastName", "+387770984344", LocalDate.now(), Role.USER, false),
            Arguments.of("email@email", "password", "firstName", null, "+387770984344", LocalDate.now(), Role.USER, false),
            Arguments.of("email@email", "password", "firstName", "lastName", null, LocalDate.now(), Role.USER, false),
            Arguments.of("email@email", "password", "firstName", "lastName", "+387770984344", null, Role.USER, false)
    );

    @ParameterizedTest
    @VariableSource("arguments")
    void testCreateNullUserParameter(String email, String password, String firstName, String lastName, String phone, LocalDate birthDate, Role role, boolean blocked) {
        User user = new User(email, password, firstName, lastName, phone, birthDate, role, blocked);
        assertThrows(DataBaseException.class, () -> userRepository.create(user));
    }

    @Test
    void testCreateExistUser() {
        User user = new User("null@email", "password", "firstName", "lastName", "+387770984344", LocalDate.now(), Role.USER, false);
        int id = userRepository.create(user);
        assertNotEquals(id, -1);
        assertThrows(DataBaseException.class, () -> userRepository.create(user));
    }

    @Test
    void testReadNotExistUser() {
        assertNull(userRepository.read(Integer.MAX_VALUE));
    }

    @Test
    void testUpdateUser() {
        Stream.of(1, 2, 3).forEach(i -> {
            User actual = userRepository.read(i);
            actual.setBlocked(!actual.isBlocked());
            actual.setEmail(actual.getEmail() + "ee");
            actual.setPassword(actual.getPassword() + "ee");
            actual.setPhone(actual.getPhone() + "22");
            actual.setFirstName(actual.getFirstName() + "12d");
            actual.setLastName(actual.getLastName() + "12d");
            long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate birthDate = LocalDate.ofEpochDay(randomDay);
            actual.setBirthDate(birthDate);

            assertTrue(userRepository.update(actual));
            assertEquals(userRepository.read(actual.getUserId()), actual);
        });
    }

    @Test
    void testDeleteUser() {
        Stream.of(1, 2, 3).forEach(i -> {
            assertTrue(userRepository.delete(i));
            assertNull(userRepository.read(i));
        });
    }

    @Test
    void testDeleteNotExistUser() {
        assertFalse(userRepository.delete(108));
    }

    @Test
    void testGetUserByEmail() {
        Stream.of(1, 2, 3).forEach(i -> {
            User actual = userRepository.read(i);
            String email = actual.getEmail();
            User expected = userRepository.getUserByEmail(email);
            assertEquals(expected, actual);
        });
    }

    @Test
    void testGetUserByEmailWithNotExistEmail() {
        assertNull(userRepository.getUserByEmail("1234user@com.com"));
    }

    @Test
    void testGetUserByEmailWithNullValue() {
        assertNull(userRepository.getUserByEmail(null));
    }

    @Test
    void testCheckUserByEmail() {
        Stream.of(1, 2, 3).forEach(i -> {
            User actual = userRepository.read(i);
            String email = actual.getEmail();
            assertTrue(userRepository.checkUserByEmail(email));
        });
    }


    @Test
    void testGetUsersByRole() {
        List<User> actual = List.of(userRepository.read(1), userRepository.read(2), userRepository.read(3));
        List<User> expected = userRepository.getUsersByRole(actual.get(0).getRole().name());

        assertEquals(expected.size(), 3);
        assertArrayEquals(expected.stream()
                        .sorted(Comparator.comparingInt(User::getUserId))
                        .toArray(),
                actual.stream()
                        .sorted(Comparator.comparingInt(User::getUserId))
                        .toArray());
    }

    @Test
    void testGetUserByRoleWithNullValue() {
        assertThrows(DataBaseException.class, () -> userRepository.getUsersByRole(null));
    }

    @Test
    void testUpdateUserBlockStatus() {
        Stream.of(1, 2, 3).forEach(i -> {
            User user = userRepository.read(i);
            int userId = user.getUserId();
            boolean status = user.isBlocked();
            userRepository.updateBlocked(userId, !status);
            assertNotEquals(userRepository.read(userId).isBlocked(), status);
        });
    }

}
