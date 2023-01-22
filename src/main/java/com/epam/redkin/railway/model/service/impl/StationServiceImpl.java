package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.repository.StationRepository;
import com.epam.redkin.railway.model.service.StationService;

import java.sql.SQLException;
import java.util.List;

public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;


    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    @Override
    public List<Station> getAllStationList() {
        return stationRepository.getAllStations();
    }

    @Override
    public List<Station> getStationListByCurrentPage(int currentPage, int recordsPerPage) {
        List<Station> allRecords = stationRepository.getAllStations();
        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getStationListSize() {
        return getAllStationList().size();
    }

    @Override
    public void updateStation(Station station) {
        try {
            stationRepository.update(station);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Station getStationById(int stationId) {
        return stationRepository.getById(stationId);
    }

    @Override
    public void removeStation(int stationId) {
        stationRepository.delete(stationId);
    }

    @Override
    public void addStation(Station station) {
        try {
            stationRepository.create(station);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
