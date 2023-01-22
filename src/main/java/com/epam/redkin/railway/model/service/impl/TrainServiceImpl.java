package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.entity.Train;
import com.epam.redkin.railway.model.service.TrainService;
import com.epam.redkin.railway.model.repository.TrainRepository;

import java.sql.SQLException;
import java.util.List;

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
    public List<Train> getTrainListBySetRecords(int currentPage, int recordsPerPage) {
        List<Train> allRecords = trainRepository.getAllTrains();
        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getTrainListSize() {
        return trainRepository.getAllTrains().size();
    }

    @Override
    public void updateTrain(Train train) {
        try {
            trainRepository.update(train);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public List<Train> getAllTrainList() {
        return trainRepository.getAllTrains();
    }

    @Override
    public void removeTrain(int trainId) {
        trainRepository.delete(trainId);
    }

}
