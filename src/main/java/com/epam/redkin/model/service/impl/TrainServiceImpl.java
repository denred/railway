package com.epam.redkin.model.service.impl;


import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.repository.TrainRepository;
import com.epam.redkin.model.service.TrainService;

import java.util.List;

public class TrainServiceImpl implements TrainService {

    private TrainRepository trainRepository;

    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }


    @Override
    public Train getTrainById(int trainId) {
        return trainRepository.read(trainId);
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
        trainRepository.update(train);
    }

    @Override
    public void addTrain(Train train) {
        trainRepository.create(train);
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
