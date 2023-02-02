package com.epam.redkin.railway.model.service;


import com.epam.redkin.railway.model.entity.Train;

import java.util.List;

public interface TrainService {


    List<Train> getTrainList();

    void addTrain(Train train);


    void removeTrain(int trainId);


    void updateTrain(Train train);


    Train getTrainById(int trainId);

    List<Train> getTrainListBySetRecords(int currentPage, int recordsPerPage);

    int getTrainListSize();
}

