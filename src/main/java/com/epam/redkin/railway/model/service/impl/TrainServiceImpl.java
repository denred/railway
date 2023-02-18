package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.repository.TrainRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TrainServiceImpl implements TrainService {

    private TrainRepository trainRepository;

    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }


    @Override
    public Train getTrainById(int trainId) {
        return trainRepository.getById(trainId);
    }

    @Override
    public List<Train> getTrainListWithPagination(int offset, int limit, Map<String, String> search) {
        return trainRepository.getTrainListWithPagination(offset, limit, search);
    }

    @Override
    public int getTrainListSize(Map<String, String> search) {
        return trainRepository.getTrainListSize(search);
    }

    @Override
    public void updateTrain(Train train) {
        trainRepository.update(train);
    }

    @Override
    public void addTrain(Train train) {
        try {
            trainRepository.create(train);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Train> getTrainList() {
        return trainRepository.getTrainList();
    }

    @Override
    public void removeTrain(int trainId) {
        trainRepository.delete(trainId);
    }

}
