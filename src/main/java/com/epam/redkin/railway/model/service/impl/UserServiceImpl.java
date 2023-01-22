package com.epam.redkin.railway.model.service.impl;

import com.epam.redkin.railway.model.entity.Role;
import com.epam.redkin.railway.model.entity.User;
import com.epam.redkin.railway.model.exception.DAOException;
import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.exception.UnauthorizedException;
import com.epam.redkin.railway.model.exception.UserAlreadyExistException;
import com.epam.redkin.railway.model.service.UserService;
import com.epam.redkin.railway.util.constants.AppContextConstant;
import com.epam.redkin.railway.web.controller.Path;
import com.epam.redkin.railway.model.repository.UserRepository;
import com.epam.redkin.railway.util.EmailDistributorUtil;
import com.epam.redkin.railway.util.EmailMessageLocalizationDispatcher;
import com.epam.redkin.railway.util.EmailMessageType;
import com.epam.redkin.railway.util.factory.UtilFactory;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final int TOKEN_VALUE_COOKIE_INDEX = 0;
    private static final int USER_ID_COOKIE_INDEX = 1;
    private final UserRepository userRepository;
    private final EmailDistributorUtil emailDistributorUtil;
    private final EmailMessageLocalizationDispatcher emailLocalizationDispatcher;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        emailDistributorUtil = UtilFactory.getInstance().getEmailDistributorUtil();
        emailLocalizationDispatcher = UtilFactory.getInstance().getEmailMessageLocalizationDispatcher();
    }


    @Override
    public User isValidUser(String email, String password) {
        User user;
        try {
            user = userRepository.getUserByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user.getEmail() != null && !user.isBlocked()) {
            System.out.println(user.getPassword());
            System.out.println(password);

            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        } else {
            UnauthorizedException e = new UnauthorizedException("You are not registered or you are blocked");
            LOGGER.error(e.getMessage());
            throw e;
        }
        UnauthorizedException e = new UnauthorizedException("Incorrect password or you are blocked");
        LOGGER.error(e.getMessage());
        throw e;
    }

    @Override
    public int registerUser(User user, String pageRootUrl) {
        int id;
        if (user == null || StringUtils.isBlank(pageRootUrl)) {
            LOGGER.error("service.commonError");
            throw new ServiceException("service.commonError");
        }

        try {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            user.setBlocked(true);
            boolean exist = userRepository.checkUserByEmail(user.getEmail());
            if (!exist) {
                id = userRepository.create(user);
            } else {
                LOGGER.error("User already exist.");
                throw new UserAlreadyExistException();
            }

            user = userRepository.getUserByEmail(user.getEmail());
            String token = getUpdatedRememberUserToken(user.getUserId());

            String userLogInLink = constructLogInLink(pageRootUrl + Path.COMMAND_POST_REGISTRATION_ACCOUNT_APPROVAL, token);
            String messageTitle = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.TITLE_USER_REGISTRATION_LINK);
            emailDistributorUtil.addEmailToSendingQueue(messageTitle, userLogInLink, user.getEmail());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
            throw new ServiceException("service.commonError", e.getMessage());
        }
        return id;
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
        if (StringUtils.isAnyBlank(email, pageRootUrl)) {
            throw new UnauthorizedException("service.commonError");
        }
        try {
            User user = userRepository.getUserByEmail(email);
            String token = getUpdatedRememberUserToken(user.getUserId());
            String userLogInLink = constructLogInLink(pageRootUrl + Path.COMMAND_POST_REGISTRATION_ACCOUNT_APPROVAL, token);
            String messageTitle = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.TITLE_FORGET_PASSWORD);
            emailDistributorUtil.addEmailToSendingQueue(messageTitle, userLogInLink, email);
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
        return token + AppContextConstant.COOKIE_REMEMBER_USER_TOKEN_DIVIDER + id;
    }

    @Override
    public User logInByToken(String token) {
        LOGGER.info("logInByToken started");
        if (token == null) {
            LOGGER.warn("token is null");
            throw new ServiceException("service.commonError");
        }
        try {
            String[] tokenComponents = token.split(AppContextConstant.COOKIE_REMEMBER_USER_TOKEN_DIVIDER);
            int userId = Integer.parseInt(tokenComponents[USER_ID_COOKIE_INDEX]);
            String tokenValue = tokenComponents[TOKEN_VALUE_COOKIE_INDEX];
            User user = userRepository.findUserByIdAndToken(userId, tokenValue);
            if (user != null) {
                if (user.isBlocked()) {
                    throw new ServiceException("validation.user.login.isBanned");
                }
                LOGGER.info("done");
                return user;
            }
            LOGGER.warn(String.format("Cant use token %s for log in", token));
            throw new ServiceException("service.commonError");
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteRememberUserToken(int userId) {
        try {
            userRepository.deleteRememberUserToken(userId);
        } catch (DAOException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void postRegistrationApprovalByToken(String token) {
        if (StringUtils.isBlank(token)) {
            LOGGER.info("invalid input token");
            throw new ServiceException("service.commonError");
        }
        String[] tokenComponents = token.split(AppContextConstant.COOKIE_REMEMBER_USER_TOKEN_DIVIDER);
        int userId = Integer.parseInt(tokenComponents[USER_ID_COOKIE_INDEX]);
        try {
            userRepository.updateBlocked(userId, false);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int getUserCount(Map<String, String> search) {
        LOGGER.info("Started the method getUserCount");
        int userCount;
        try {
            userCount = userRepository.getUserCount(search);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException("Cannot close the ResultSet", e);
        }
        LOGGER.info("The method getUserCount done, count of users: " + userCount);
        return userCount;
    }

    private String constructLogInLink(String pageRootUrl, String token) {
        return pageRootUrl + '&' + AppContextConstant.COOKIE_REMEMBER_USER_TOKEN + '=' + token;
    }
}
