package com.epam.redkin.service.impl;


import com.epam.redkin.model.dto.RoutInfoDto;
import com.epam.redkin.model.dto.RoutsOrderDto;
import com.epam.redkin.model.dto.StationDto;
import com.epam.redkin.model.entity.CarriageType;
import com.epam.redkin.model.entity.Route;
import com.epam.redkin.model.repository.RouteRepo;
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
    private final RouteRepo routeRepo;
    private final SeatService seatService;


    public RoutServiceImpl(RouteRepo routsRepository, SeatService seatService, CarService carService) {
        this.routeRepo = routsRepository;
        this.carService = carService;
        this.seatService = seatService;
    }


    @Override
    public void addRout(Route route) {
        routeRepo.create(route);
    }

    @Override
    public List<RoutsOrderDto> getRouteListWithParameters(String departureStation, String arrivalStation,
                                                          LocalDateTime departureDate) {
        List<StationDto> stations = routeRepo.getRouteListWithParameters(departureStation, arrivalStation);
        Map<Integer, List<StationDto>> routToStationMap = new HashMap<>();

        for (StationDto stationDto : stations) {
            List<StationDto> routStations = routToStationMap.computeIfAbsent(stationDto.getRoutsId(), k -> new ArrayList<>());
            routStations.add(stationDto);
        }

        List<RoutsOrderDto> result = new ArrayList<>();

        for (List<StationDto> stationDtos : routToStationMap.values()) {
            StationDto departure = null;
            StationDto arrival = null;

            for (StationDto station : stationDtos) {
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
                result.add(toRoutsOrderDto(stationDtos));
            }
        }

        LOGGER.debug("======================================");
        LOGGER.debug("RESULT: " + result);

        return result;
    }


    @Override
    public RoutInfoDto getRoutById(int routsId) {
        return routeRepo.getRoutById(routsId);
    }

    @Override
    public void updateRout(Route route) {
        routeRepo.update(route);
    }

    @Override
    public void removeRout(int routsId) {
        routeRepo.delete(routsId);
    }

    @Override
    public List<RoutInfoDto> getAllRoutList() {
        return routeRepo.getAllRoutList();
    }

    private RoutsOrderDto toRoutsOrderDto(List<StationDto> stationDtos) {
        RoutsOrderDto routsOrderDto = new RoutsOrderDto();
        routsOrderDto.setStations(stationDtos);
        StationDto stationDto = stationDtos.get(0);
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

