package com.epam.redkin.service;

import com.epam.redkin.model.entity.Station;

import java.util.List;

public interface StationService {


    List<Station> getAllStationList();

    List<Station> getStationListByCurrentPage(int currentPage, int recordsPerPage);
    int getStationListSize();


    void addStation(Station station);


    void removeStation(int stationId);


    void updateStation(Station station);


    Station getStationById(int stationId);

}

