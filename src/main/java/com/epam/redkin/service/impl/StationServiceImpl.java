package com.epam.redkin.service.impl;


import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.repository.StationRepo;
import com.epam.redkin.service.StationService;

import java.util.List;

public class StationServiceImpl implements StationService {

    private StationRepo stationRepository;


    public StationServiceImpl(StationRepo stationRepository) {
        this.stationRepository = stationRepository;
    }


    @Override
    public List<Station> getAllStationList() {
        return stationRepository.getAllStations();
    }

    @Override
    public void updateStation(Station station) {
        stationRepository.update(station);
    }

    @Override
    public Station getStationById(int stationId) {
        return stationRepository.read(stationId);
    }

    @Override
    public void removeStation(int stationId) {
        stationRepository.delete(stationId);
    }

    @Override
    public void addStation(Station station) {
        stationRepository.create(station);
    }
}
