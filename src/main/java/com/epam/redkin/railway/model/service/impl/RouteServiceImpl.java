package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.dto.RoutsOrderDTO;
import com.epam.redkin.railway.model.dto.StationDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Route;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.repository.RouteRepository;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.model.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

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
    public void addRoute(Route route) {
        try {
            routeRepository.create(route);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RoutsOrderDTO> getRouteOrderDtoList(String departureStation, String arrivalStation,
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
    public LocalDateTime getDepartureDate(String startDate, String startTime) {
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
        } catch (DateTimeParseException e) {
            LOGGER.error("Incorrect date entered. Date or time format: " + e);
            throw new IncorrectDataException("Incorrect data entered. Date or time format", e);
        }
        return departureDate;
    }


    @Override
    public RouteInfoDTO getRouteInfoById(int routsId) {
        return routeRepository.getRouteInfoDTOByRouteId(routsId);
    }

    @Override
    public void updateRoute(Route route) {

        routeRepository.update(route);

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
    public List<RouteInfoDTO> getRouteInfoListWithFilter(int offset, int limit, Map<String, String> search) {
        return routeRepository.getRouteInfoDTOListWithFilter(offset, limit, search);
    }

    @Override
    public int getRouteListSize(Map<String, String> search) {
        return routeRepository.getRouteInfoDTOListCount(search);
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

