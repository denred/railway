package com.epam.redkin.railway.model.service;


import com.epam.redkin.railway.model.entity.Train;

import java.util.List;
import java.util.Map;

public interface TrainService {


    List<Train> getTrainList();

    void addTrain(Train train);


    void removeTrain(int trainId);


    void updateTrain(Train train);


    Train getTrainById(int trainId);

    List<Train> getTrainListWithPagination(int offset, int limit, Map<String, String> search);

    int getTrainListSize(Map<String, String> search);
}

