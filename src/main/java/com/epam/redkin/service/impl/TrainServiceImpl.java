package com.epam.redkin.service.impl;


import com.epam.redkin.model.entity.Train;
import com.epam.redkin.model.repository.TrainRepo;
import com.epam.redkin.service.TrainService;

import java.util.List;

public class TrainServiceImpl implements TrainService {

    private TrainRepo trainRepository;

    public TrainServiceImpl(TrainRepo trainRepository) {
        this.trainRepository = trainRepository;
    }


    @Override
    public Train getTrainById(int trainId) {
        return trainRepository.read(trainId);
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
