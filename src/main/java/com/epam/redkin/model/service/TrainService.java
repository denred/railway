package com.epam.redkin.model.service;


import com.epam.redkin.model.entity.Train;

import java.util.List;

public interface TrainService {


    List<Train> getAllTrainList();

    void addTrain(Train train);


    void removeTrain(int trainId);


    void updateTrain(Train train);


    Train getTrainById(int trainId);

    List<Train> getTrainListBySetRecords(int currentPage, int recordsPerPage);

    int getTrainListSize();
}

