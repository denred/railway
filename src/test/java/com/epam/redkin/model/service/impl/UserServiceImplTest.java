package com.epam.redkin.model.service.impl;

import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.ServiceException;
import com.epam.redkin.model.repository.UserRepository;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {
    /**
     * Method under test: {@link UserServiceImpl#UserServiceImpl(UserRepository)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testConstructor() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserRepository userRepository = null;

        // Act
        UserServiceImpl actualUserServiceImpl = new UserServiceImpl(userRepository);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#isValidUser(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testIsValidUser() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        String email = "";
        String password = "";

        // Act
        User actualIsValidUserResult = userServiceImpl.isValidUser(email, password);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#registerUser(User, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterUser() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        User user = null;
        String pageRootUrl = "";

        // Act
        int actualRegisterUserResult = userServiceImpl.registerUser(user, pageRootUrl);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#updateBlocked(int, boolean)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateBlocked() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        int idUser = 0;
        boolean blockStatus = false;

        // Act
        userServiceImpl.updateBlocked(idUser, blockStatus);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserInfo(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserInfo() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        String userRole = "";

        // Act
        List<User> actualUserInfo = userServiceImpl.getUserInfo(userRole);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#read(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRead() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        int userId = 0;

        // Act
        User actualReadResult = userServiceImpl.read(userId);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserListByCurrentRecordAndRecordsPerPage(int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserListByCurrentRecordAndRecordsPerPage() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        int currentPage = 0;
        int recordsPerPage = 0;

        // Act
        List<User> actualUserListByCurrentRecordAndRecordsPerPage = userServiceImpl
                .getUserListByCurrentRecordAndRecordsPerPage(currentPage, recordsPerPage);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserListSize()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserListSize() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;

        // Act
        int actualUserListSize = userServiceImpl.getUserListSize();

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#sendLogInTokenIfForgetPassword(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSendLogInTokenIfForgetPassword() throws ServiceException {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        String email = "";
        String pageRootUrl = "";

        // Act
        userServiceImpl.sendLogInTokenIfForgetPassword(email, pageRootUrl);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#getUpdatedRememberUserToken(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUpdatedRememberUserToken() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        int id = 0;

        // Act
        String actualUpdatedRememberUserToken = userServiceImpl.getUpdatedRememberUserToken(id);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#logInByToken(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogInByToken() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        String token = "";

        // Act
        User actualLogInByTokenResult = userServiceImpl.logInByToken(token);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteRememberUserToken(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteRememberUserToken() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        int userId = 0;

        // Act
        userServiceImpl.deleteRememberUserToken(userId);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserServiceImpl#postRegistrationApprovalByToken(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testPostRegistrationApprovalByToken() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of UserServiceImpl.
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
        UserServiceImpl userServiceImpl = null;
        String token = "";

        // Act
        userServiceImpl.postRegistrationApprovalByToken(token);

        // Assert
        // TODO: Add assertions on result
    }
}

