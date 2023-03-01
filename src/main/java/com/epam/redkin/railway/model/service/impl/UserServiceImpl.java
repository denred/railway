package com.epam.redkin.railway.model.service.impl;

import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.exception.UnauthorizedException;
import com.epam.redkin.railway.model.exception.UserAlreadyExistException;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.model.repository.UserRepository;
import com.epam.redkin.railway.util.mail.EmailDistributorUtil;
import com.epam.redkin.railway.util.mail.EmailMessageLocalizationDispatcher;
import com.epam.redkin.railway.util.mail.EmailMessageType;
import com.epam.redkin.railway.util.factory.UtilFactory;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.epam.redkin.railway.util.constants.AppContextConstant.COOKIE_REMEMBER_USER_TOKEN_DIVIDER;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final int TOKEN_VALUE_COOKIE_INDEX = 0;
    private static final int USER_ID_COOKIE_INDEX = 1;
    private final UserRepository userRepository;
    private final EmailDistributorUtil emailDistributorUtil;
    private final EmailMessageLocalizationDispatcher emailLocalizationDispatcher;

    {
        emailDistributorUtil = UtilFactory.getInstance().getEmailDistributorUtil();
        emailLocalizationDispatcher = UtilFactory.getInstance().getEmailMessageLocalizationDispatcher();
    }

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User isValidUser(String email, String password) {
        try {
            User user = userRepository.getUserByEmail(email);
            if (user == null || user.getEmail() == null || user.isBlocked()) {
                throw new UnauthorizedException("You are not registered or you are blocked");
            }
            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new UnauthorizedException("Incorrect password or you are blocked");
            }
            return user;
        } catch (SQLException e) {
            throw new DataBaseException("Error checking user credentials for email: " + email, e);
        }
    }


    @Override
    public int registerUser(User user, String pageRootUrl) {
        LOGGER.info("Started method registerUser(user={} pageRootUrl={})", user, pageRootUrl);
        if (user == null || StringUtils.isBlank(pageRootUrl)) {
            LOGGER.error("Invalid input");
            throw new ServiceException("Invalid input");
        }

        boolean userExists = userRepository.checkUserByEmail(user.getEmail());
        if (userExists) {
            LOGGER.error("User already exists");
            throw new UserAlreadyExistException();
        }

        int userId = createNewUser(user);
        String token = generateToken(userId);

        sendRegistrationEmail(pageRootUrl, token, user.getEmail());

        return userId;
    }

    private int createNewUser(User user) {
        LOGGER.info("Create user {}", user);
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setBlocked(true);
        return userRepository.create(user);
    }

    private String generateToken(int userId) {
        return getUpdatedRememberUserToken(userId);
    }

    private void sendRegistrationEmail(String pageRootUrl, String token, String email) {
        String userLogInLink = constructLogInLink(pageRootUrl + Path.COMMAND_POST_REGISTRATION_ACCOUNT_APPROVAL, token);
        String messageTitle = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.TITLE_USER_REGISTRATION_LINK);
        String messageText = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.MESSAGE_USER_REGISTRATION_LINK, userLogInLink);
        emailDistributorUtil.addEmailToSendingQueue(messageTitle, messageText, email);
    }


    @Override
    public void updateBlocked(int idUser, boolean blockStatus) {
        userRepository.updateBlocked(idUser, blockStatus);
    }


    @Override
    public User read(int userId) {
        User user = userRepository.getById(userId);
        if (user == null) {
            UnauthorizedException e = new UnauthorizedException("You are not registered");
            LOGGER.error(e.getMessage());
            throw e;
        }
        return user;
    }

    @Override
    public List<User> getUserList(int offset, int limit, Map<String, String> search) {
        List<User> userList;
        try {
            userList = userRepository.getUsersByRole(Role.USER.name(), offset, limit, search);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }


    @Override
    public void sendLogInTokenIfForgetPassword(String email, String pageRootUrl) throws ServiceException {
        LOGGER.info("Started --> public void sendLogInTokenIfForgetPassword(String email, String pageRootUrl) --> " +
                "email: " + email + " url:" + pageRootUrl);
        if (StringUtils.isAnyBlank(email, pageRootUrl)) {
            LOGGER.error("Email or url are empty");
            throw new UnauthorizedException("Service error: Email or url are empty");
        }
        try {
            User user = userRepository.getUserByEmail(email);
            String token = getUpdatedRememberUserToken(user.getUserId());
            String userLogInLink = constructLogInLink(pageRootUrl + Path.COMMAND_POST_REGISTRATION_ACCOUNT_APPROVAL, token);
            String messageTitle = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.TITLE_FORGET_PASSWORD);
            String messageText = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.MESSAGE_FORGET_PASSWORD, userLogInLink);
            emailDistributorUtil.addEmailToSendingQueue(messageTitle, messageText, email);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        } catch (Exception e) {
            throw new UnauthorizedException("service.commonError", e);
        }
    }

    @Override
    public String getUpdatedRememberUserToken(int id) {
        String token = UUID.randomUUID().toString();
        userRepository.updateRememberUserToken(id, token);
        return token + COOKIE_REMEMBER_USER_TOKEN_DIVIDER + id;
    }

    @Override
    public User logInByToken(String token) {
        LOGGER.info("Starting execution of logInByToken");

        if (StringUtils.isBlank(token)) {
            LOGGER.warn("Token is blank");
            throw new ServiceException("Token is blank");
        }

        try {
            String[] tokenComponents = token.split(COOKIE_REMEMBER_USER_TOKEN_DIVIDER);

            int userId = Integer.parseInt(tokenComponents[USER_ID_COOKIE_INDEX]);
            String tokenValue = tokenComponents[TOKEN_VALUE_COOKIE_INDEX];

            User user = userRepository.findUserByIdAndToken(userId, tokenValue);

            if (user == null) {
                LOGGER.warn("Token is invalid or has already been used: {}", token);
                throw new ServiceException("Token is invalid or has already been used");
            }

            if (user.isBlocked()) {
                LOGGER.warn("User is blocked: {}", user.getEmail());
                throw new ServiceException("User is blocked");
            }

            LOGGER.info("Successfully logged in user with id {}", user.getUserId());
            return user;
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid user id in token: {}", token, e);
            throw new ServiceException("Invalid user id in token");
        }
    }


    @Override
    public void deleteRememberUserToken(int userId) {
        try {
            userRepository.deleteRememberUserToken(userId);
        } catch (DataBaseException e) {
            throw new DataBaseException(e.getMessage(), e);
        }
    }

    @Override
    public void postRegistrationApprovalByToken(String token) {
        LOGGER.info("Starting execution of postRegistrationApprovalByToken");

        if (StringUtils.isBlank(token)) {
            LOGGER.warn("Token is blank");
            throw new ServiceException("Token is blank");
        }

        String[] tokenComponents = token.split(COOKIE_REMEMBER_USER_TOKEN_DIVIDER);

        if (tokenComponents.length < 2) {
            LOGGER.warn("Token is invalid: {}", token);
            throw new ServiceException("Token is invalid");
        }

        int userId;

        try {
            userId = Integer.parseInt(tokenComponents[USER_ID_COOKIE_INDEX]);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid user id in token: {}", token, e);
            throw new ServiceException("Invalid user id in token");
        }

        try {
            userRepository.updateBlocked(userId, false);
        } catch (DataBaseException e) {
            LOGGER.error("Unable to update user blocked status: {}", userId, e);
            throw new ServiceException(e.getMessage());
        }

        LOGGER.info("Successfully approved registration for user with id {}", userId);
    }


    @Override
    public int getUserCount(Map<String, String> search) {
        LOGGER.info("Started the method getUserCount");
        int userCount;
        try {
            userCount = userRepository.getUserCount(search);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Cannot close the ResultSet", e);
        }
        LOGGER.info("The method getUserCount done, count of users: " + userCount);
        return userCount;
    }

    @Override
    public boolean updateUser(User newUser) {
        LOGGER.info("Started --> updateUser(User newUser) --> newUser= " + newUser);
        userRepository.update(newUser);
        return false;
    }

    private String constructLogInLink(String pageRootUrl, String token) {
        String link = pageRootUrl + '&' + AppContextConstant.COOKIE_REMEMBER_USER_TOKEN + '=' + token;
        String anchorText = "Approval";
        return "<a href='" + link + "'>" + anchorText + "</a>";
    }
}
