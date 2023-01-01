package com.epam.redkin.service.impl;


import com.epam.redkin.model.dto.RouteInfoDTO;
import com.epam.redkin.model.dto.RoutsOrderDto;
import com.epam.redkin.model.dto.StationDTO;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.repository.RouteRepository;
import com.epam.redkin.service.CarService;
import com.epam.redkin.service.RoutService;
import com.epam.redkin.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("FieldCanBeLocal")
public class RoutServiceImpl implements RoutService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoutServiceImpl.class);
    private final CarService carService;
    private final RouteRepository routeRepository;
    private final SeatService seatService;


    public RoutServiceImpl(RouteRepository routsRepository, SeatService seatService, CarService carService) {
        this.routeRepository = routsRepository;
        this.carService = carService;
        this.seatService = seatService;
    }


    @Override
    public void addRout(Route route) {
        routeRepository.create(route);
    }

    @Override
    public List<RoutsOrderDto> getRouteListWithParameters(String departureStation, String arrivalStation,
                                                          LocalDateTime departureDate) {
        List<StationDTO> stations = routeRepository.getStationDTOListWithParameters(departureStation, arrivalStation);
        Map<Integer, List<StationDTO>> routToStationMap = new HashMap<>();

        for (StationDTO stationDto : stations) {
            List<StationDTO> routStations = routToStationMap.computeIfAbsent(stationDto.getRoutsId(), k -> new ArrayList<>());
            routStations.add(stationDto);
        }

        List<RoutsOrderDto> result = new ArrayList<>();

        for (List<StationDTO> stationDTOS : routToStationMap.values()) {
            StationDTO departure = null;
            StationDTO arrival = null;

            for (StationDTO station : stationDTOS) {
                if (station.getStation().equalsIgnoreCase(departureStation)) {
                    departure = station;
                }

                if (station.getStation().equalsIgnoreCase(arrivalStation)) {
                    arrival = station;
                }
            }

            if (Objects.isNull(departure) || Objects.isNull(arrival)) {
                continue;
            }

            if (departure.getOrder() > arrival.getOrder() || departure.getOrder() == arrival.getOrder()) {
                LOGGER.error("/////////////////--=====...BUG...=====--/////////////");
                LOGGER.debug("departure: " + departure.getOrder() + " arrival: " + arrival.getOrder());
                continue;
            }

            if (departure.getStationDispatchData().isAfter(departureDate) || departure.getStationDispatchData()
                    .isEqual(departureDate)) {
                result.add(toRoutsOrderDto(stationDTOS));
            }
        }

        LOGGER.debug("======================================");
        LOGGER.debug("RESULT: " + result);

        return result;
    }


    @Override
    public RouteInfoDTO getRoutById(int routsId) {
        return routeRepository.getRouteInfoDTOByRouteId(routsId);
    }

    @Override
    public void updateRout(Route route) {
        routeRepository.update(route);
    }

    @Override
    public void removeRout(int routsId) {
        routeRepository.delete(routsId);
    }

    @Override
    public List<RouteInfoDTO> getAllRoutList() {
        return routeRepository.getAllRouteInfoDTOList();
    }

    private RoutsOrderDto toRoutsOrderDto(List<StationDTO> stationDTOS) {
        RoutsOrderDto routsOrderDto = new RoutsOrderDto();
        routsOrderDto.setStations(stationDTOS);
        StationDTO stationDto = stationDTOS.get(0);
        routsOrderDto.setRoutName(stationDto.getRoutName());
        routsOrderDto.setRoutNumber(stationDto.getRoutNumber());
        routsOrderDto.setRoutsId(stationDto.getRoutsId());
        routsOrderDto.setTrainId(stationDto.getTrainId());
        routsOrderDto.setTrainNumber(stationDto.getTrainNumber());
        int coutnFirstClassSeats = seatService.getCountSeatByCarType(stationDto.getTrainId(), CarriageType.FIRST_CLASS);
        int countSecondClassSeats = seatService.getCountSeatByCarType(stationDto.getTrainId(), CarriageType.SECOND_CLASS);
        routsOrderDto.setFirstClassFreeSeatsCount(coutnFirstClassSeats);
        routsOrderDto.setSecondClassFreeSeatsCount(countSecondClassSeats);

        return routsOrderDto;
    }
}

