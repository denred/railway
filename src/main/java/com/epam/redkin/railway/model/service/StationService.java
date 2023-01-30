package com.epam.redkin.railway.model.service;

import com.epam.redkin.railway.model.entity.Station;

import java.util.List;

public interface StationService {


    List<Station> getAllStationList();

    List<Station> getStationList(int offset, int limit, String search);
    int getStationListSize(String search);


    void addStation(Station station);


    void removeStation(int stationId);


    void updateStation(Station station);


    Station getStationById(int stationId);

}

