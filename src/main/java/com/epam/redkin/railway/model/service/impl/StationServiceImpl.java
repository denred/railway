package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.entity.Station;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.repository.StationRepository;
import com.epam.redkin.railway.model.service.StationService;

import java.util.List;

public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;


    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    @Override
    public List<Station> getStations() {
        return stationRepository.getAllStations();
    }

    @Override
    public List<Station> getStationList(int currentPage, int recordsPerPage, String search) {
        return stationRepository.getStationsWithFilter(currentPage, recordsPerPage, search);
    }

    @Override
    public int getStationListSize(String search) {
        return stationRepository.getCountStationWithSearch(search);
    }

    @Override
    public void updateStation(Station station) {

            stationRepository.update(station);

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
        } catch (DataBaseException e) {
            throw new ServiceException("Failed to store station into database", e.getMessage());
        }
    }
}
