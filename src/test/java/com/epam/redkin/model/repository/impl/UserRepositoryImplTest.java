package com.epam.redkin.model.repository.impl;

import static org.mockito.Mockito.mock;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.DataBaseException;

import java.sql.ResultSet;
import java.util.List;
import javax.sql.DataSource;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

class UserRepositoryImplTest {
    /**
     * Method under test: {@link UserRepositoryImpl#UserRepositoryImpl(DataSource)}
     */
    @Test
    void testConstructor() {
        // TODO: Complete this test.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     UserRepositoryImpl.dataSource

        // Arrange
        DataSource dataSource = mock(DataSource.class);

        // Act
        new UserRepositoryImpl(dataSource);
    }

    /**
     * Method under test: {@link UserRepositoryImpl#UserRepositoryImpl()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testConstructor2() {
        // TODO: Complete this test.
        //   Reason: R006 Static initializer failed.
        //   The static initializer of
        //   com.epam.redkin.model.connectionpool.ConnectionPools
        //   threw com.zaxxer.hikari.pool.HikariPool$PoolInitializationException while trying to load it.
        //   Make sure the static initializer of ConnectionPools
        //   can be executed without throwing exceptions.
        //   Exception: com.zaxxer.hikari.pool.HikariPool$PoolInitializationException: Failed to initialize pool: Could not create connection to database server.
        //       at com.zaxxer.hikari.pool.HikariPool.throwPoolInitializationException(HikariPool.java:596)
        //       at com.zaxxer.hikari.pool.HikariPool.checkFailFast(HikariPool.java:582)
        //       at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:100)
        //       at com.zaxxer.hikari.HikariDataSource.<init>(HikariDataSource.java:81)
        //       at com.epam.redkin.model.connectionpool.ConnectionPools.<clinit>(ConnectionPools.java:13)
        //       at com.epam.redkin.model.repository.impl.UserRepositoryImpl.<init>(UserRepositoryImpl.java:25)

        // Arrange and Act
        // TODO: Populate arranged inputs
        UserRepositoryImpl actualUserRepositoryImpl = new UserRepositoryImpl();

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#create(User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreate() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        User user = null;

        // Act
        int actualCreateResult = userRepositoryImpl.create(user);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#read(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRead() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        int id = 0;

        // Act
        User actualReadResult = userRepositoryImpl.read(id);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#update(User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdate() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        User user = null;

        // Act
        boolean actualUpdateResult = userRepositoryImpl.update(user);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#delete(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDelete() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        int id = 0;

        // Act
        boolean actualDeleteResult = userRepositoryImpl.delete(id);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#getUserByEmail(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserByEmail() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        String email = "";

        // Act
        User actualUserByEmail = userRepositoryImpl.getUserByEmail(email);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#checkUserByEmail(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCheckUserByEmail() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        String email = "";

        // Act
        boolean actualCheckUserByEmailResult = userRepositoryImpl.checkUserByEmail(email);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#getUsersByRole(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUsersByRole() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        String role = "";

        // Act
        List<User> actualUsersByRole = userRepositoryImpl.getUsersByRole(role);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#updateBlocked(int, boolean)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateBlocked() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        int id = 0;
        boolean status = false;

        // Act
        userRepositoryImpl.updateBlocked(id, status);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#updateRememberUserToken(int, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateRememberUserToken() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        int id = 0;
        String token = "";

        // Act
        userRepositoryImpl.updateRememberUserToken(id, token);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#findUserByIdAndToken(int, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindUserByIdAndToken() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        int userId = 0;
        String token = "";

        // Act
        User actualFindUserByIdAndTokenResult = userRepositoryImpl.findUserByIdAndToken(userId, token);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#deleteRememberUserToken(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteRememberUserToken() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        int userId = 0;

        // Act
        userRepositoryImpl.deleteRememberUserToken(userId);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserRepositoryImpl#closeResultSet(ResultSet)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCloseResultSet() throws DataBaseException {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserRepositoryImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserRepositoryImpl userRepositoryImpl = null;
        ResultSet resultSet = null;

        // Act
        userRepositoryImpl.closeResultSet(resultSet);

        // Assert
        // TODO: Add assertions on result
    }
}

