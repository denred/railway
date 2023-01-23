package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.dto.RoutsOrderDTO;
import com.epam.redkin.railway.model.dto.StationDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Route;
import com.epam.redkin.railway.model.repository.RouteRepository;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.model.service.SeatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.redkin.railway.util.constants.AppContextConstant.*;

@SuppressWarnings("FieldCanBeLocal")
public class RouteServiceImpl implements RouteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteServiceImpl.class);
    private final RouteRepository routeRepository;
    private final SeatService seatService;


    public RouteServiceImpl(RouteRepository routsRepository, SeatService seatService) {
        this.routeRepository = routsRepository;
        this.seatService = seatService;
    }


    @Override
    public void addRout(Route route) {
        try {
            routeRepository.create(route);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RoutsOrderDTO> getRouteListWithParameters(String departureStation, String arrivalStation,
                                                          LocalDateTime departureDate) {
        List<StationDTO> stations = routeRepository.getStationDTOListWithParameters(departureStation, arrivalStation);
        Map<Integer, List<StationDTO>> routToStationMap = new HashMap<>();

        for (StationDTO stationDto : stations) {
            List<StationDTO> routStations = routToStationMap.computeIfAbsent(stationDto.getRoutsId(), k -> new ArrayList<>());
            routStations.add(stationDto);
        }

        List<RoutsOrderDTO> result = new ArrayList<>();

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
                continue;
            }

            if (departure.getStationDispatchDateTime().isAfter(departureDate) || departure.getStationDispatchDateTime()
                    .isEqual(departureDate)) {
                result.add(toRoutsOrderDto(stationDTOS));
            }
        }
        return result;
    }

    @Override
    public void fillAvailableSeats(List<RoutsOrderDTO> routeOrderDTOList) {
        LOGGER.info("Started the method fillAvailableSeats");
        List<CarriageType> carriageTypeList = new ArrayList<>(EnumSet.allOf(CarriageType.class));
        routeOrderDTOList.forEach(route -> {
            HashMap<CarriageType, Integer> availableSeats = new HashMap<>();
            carriageTypeList.forEach(type -> {
                int count = seatService.getCountSeatByCarType(route.getTrainId(), type);
                if (count > 0) {
                    availableSeats.put(type, count);
                }
            });
            route.setAvailableSeats(availableSeats);
        });
    }


    @Override
    public RouteInfoDTO getRoutById(int routsId) {
        return routeRepository.getRouteInfoDTOByRouteId(routsId);
    }

    @Override
    public void updateRoute(Route route) {
        try {
            routeRepository.update(route);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeRout(int routsId) {
        routeRepository.delete(routsId);
    }

    @Override
    public List<RouteInfoDTO> getAllRouteList() {
        return routeRepository.getRouteInfoDTOList();
    }

    @Override
    public List<RouteInfoDTO> getRouteListByCurrentRecordAndRecordsPerPage(int currentPage, int recordsPerPage, String filter, String filterValue) {
        List<RouteInfoDTO> allRecords;

        if(StringUtils.isNoneBlank(filter, filterValue)) {
            switch (filter) {
                case FILTER_TRAIN -> allRecords = routeRepository.getRouteInfoDTOList().stream()
                        .filter(routeDto -> routeDto.getTrainNumber().toLowerCase().contains(filterValue.toLowerCase()))
                        .collect(Collectors.toList());
                case FILTER_ROUTE_NUMBER -> allRecords = routeRepository.getRouteInfoDTOList().stream()
                        .filter(routeDto -> routeDto.getRoutNumber().toLowerCase().contains(filterValue.toLowerCase()))
                        .collect(Collectors.toList());
                case FILTER_ROUTE_NAME -> allRecords = routeRepository.getRouteInfoDTOList().stream()
                        .filter(routeDto -> routeDto.getRoutName().toLowerCase().contains(filterValue.toLowerCase()))
                        .collect(Collectors.toList());
                default -> allRecords = routeRepository.getRouteInfoDTOList();
            }
            return allRecords;
        }else{
            allRecords = routeRepository.getRouteInfoDTOList();
        }

        return allRecords.subList(currentPage, Math.min(recordsPerPage, allRecords.size()));
    }

    @Override
    public int getRouteListSize() {
        return routeRepository.getRouteInfoDTOList().size();
    }

    private RoutsOrderDTO toRoutsOrderDto(List<StationDTO> stationDTOS) {
        RoutsOrderDTO routsOrderDto = new RoutsOrderDTO();
        routsOrderDto.setStations(stationDTOS);
        StationDTO stationDto = stationDTOS.get(0);
        routsOrderDto.setRoutName(stationDto.getRoutName());
        routsOrderDto.setRoutNumber(stationDto.getRoutNumber());
        routsOrderDto.setRoutsId(stationDto.getRoutsId());
        routsOrderDto.setTrainId(stationDto.getTrainId());
        routsOrderDto.setTrainNumber(stationDto.getTrainNumber());
        int countFirstClassSeats = seatService.getCountSeatByCarType(stationDto.getTrainId(), CarriageType.FIRST_CLASS);
        int countSecondClassSeats = seatService.getCountSeatByCarType(stationDto.getTrainId(), CarriageType.SECOND_CLASS);
        routsOrderDto.setFirstClassFreeSeatsCount(countFirstClassSeats);
        routsOrderDto.setSecondClassFreeSeatsCount(countSecondClassSeats);

        return routsOrderDto;
    }
}

