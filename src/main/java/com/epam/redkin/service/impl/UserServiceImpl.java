package com.epam.redkin.service.impl;

import com.epam.redkin.model.entity.Role;
import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.entity.User;
import com.epam.redkin.model.exception.UnauthorizedException;
import com.epam.redkin.model.exception.UserAlreadyExistException;
import com.epam.redkin.model.repository.UserRepository;
import com.epam.redkin.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
