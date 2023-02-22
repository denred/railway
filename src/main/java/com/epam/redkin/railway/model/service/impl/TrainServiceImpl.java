package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.repository.TrainRepository;

import java.util.List;
import java.util.Map;

public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }


    @Override
    public Train getTrainById(int trainId) {
        try {
            return trainRepository.getById(trainId);
        } catch (DataBaseException e) {
            throw new ServiceException("Failed to get train by trainId", e.getMessage());
        }
    }

    @Override
    public List<Train> getTrainListWithPagination(int offset, int limit, Map<String, String> search) {
        try {
            return trainRepository.getTrainListWithPagination(offset, limit, search);
        } catch (DataBaseException e) {
            throw new ServiceException("Failed to get train list with pagination and search query", e.getMessage());
        }
    }

    @Override
    public int getTrainListSize(Map<String, String> search) {
        try {
            return trainRepository.getTrainListSize(search);
        } catch (DataBaseException e) {
            throw new ServiceException("Failed to get train list size", e.getMessage());
        }
    }

    @Override
    public void updateTrain(Train train) {
        try {
            trainRepository.update(train);
        } catch (DataBaseException e) {
            throw new ServiceException("Failed update train", e.getMessage());
        }
    }

    @Override
    public void addTrain(Train train) {
        try {
            trainRepository.create(train);
        } catch (DataBaseException e) {
            throw new ServiceException("Failed to store train into database", e.getMessage());
        }
    }

    @Override
    public List<Train> getTrainList() {
        try {
            return trainRepository.getTrainList();
        } catch (DataBaseException e) {
            throw new ServiceException("Failed to get train list from database", e.getMessage());
        }
    }

    @Override
    public void removeTrain(int trainId) {
        try {
            trainRepository.delete(trainId);
        } catch (DataBaseException e) {
            throw new ServiceException("Failed to delete train from database", e.getMessage());
        }
    }
}
