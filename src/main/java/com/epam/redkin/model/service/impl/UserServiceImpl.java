package com.epam.redkin.model.service.impl;

import com.epam.redkin.model.entity.Role;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.*;
import com.epam.redkin.model.repository.UserRepository;
import com.epam.redkin.model.service.UserService;
import com.epam.redkin.util.EmailDistributorUtil;
import com.epam.redkin.util.EmailMessageLocalizationDispatcher;
import com.epam.redkin.util.EmailMessageType;
import com.epam.redkin.util.factory.UtilFactory;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static com.epam.redkin.web.controller.Path.*;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());
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
        User user = userRepository.getUserByEmail(email);
        if (user.getEmail() != null && !user.isBlocked()) {
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
    public int registr(User user) {
        boolean exist = userRepository.checkUserByEmail(user.getEmail());
        if (!exist) {
            return userRepository.create(user);
        } else {
            throw new UserAlreadyExistException();
        }
    }

    @Override
    public void updateBlocked(int idUser, boolean blockStatus) {
        userRepository.updateBlocked(idUser, blockStatus);
    }

    @Override
    public List<User> getUserInfo(String userRole) {
        return userRepository.getUsersByRole(userRole);
    }

    @Override
    public User read(int userId) {
        User user = userRepository.read(userId);
        if (user == null) {
            UnauthorizedException e = new UnauthorizedException("You are not registered");
            LOGGER.error(e.getMessage());
            throw e;
        }
        return user;
    }

    @Override
    public List<User> getUserListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage) {
        List<User> allRecords = userRepository.getUsersByRole(Role.USER.name());
        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getUserListSize() {
        return userRepository.getUsersByRole(Role.USER.name()).size();
    }

    @Override
    public void sendLogInTokenIfForgetPassword(String email, String pageRootUrl) throws ServiceException {
        if (StringUtils.isAnyBlank(email, pageRootUrl)) {
            throw new UnauthorizedException("service.commonError");
        }
        try {
            User user = userRepository.getUserByEmail(email);
            String token = getUpdatedRememberUserToken(user.getUserId());
            String userLogInLink = constructLogInLink(pageRootUrl, token);
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
        if (token == null) {
            LOGGER.warn("token is null");
            throw new ServiceException("service.commonError");
        }
        try {
            String[] tokenComponents = token.split(COOKIE_REMEMBER_USER_TOKEN_DIVIDER);
            int userId = Integer.parseInt(tokenComponents[USER_ID_COOKIE_INDEX]);
            String tokenValue = tokenComponents[TOKEN_VALUE_COOKIE_INDEX];
            User user = userRepository.findUserByIdAndToken(userId, tokenValue);
            if (user != null) {
                if (user.isBlocked()) {
                    throw new ServiceException("validation.user.login.isBanned");
                }
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
        } catch (DataBaseException e) {
            throw new DataBaseException(e.getMessage(), e);
        }
    }

    private String constructLogInLink(String pageRootUrl, String token) {
        return pageRootUrl + '?' + COMMAND + '=' + COMMAND_FORGET_PASSWORD_LOGIN
                + '&' + COOKIE_REMEMBER_USER_TOKEN + '=' + token;
    }


}
