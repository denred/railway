package com.epam.redkin.railway.model.service.impl;


import com.epam.redkin.railway.model.dto.RouteInfoDTO;
import com.epam.redkin.railway.model.dto.RouteOrderDTO;
import com.epam.redkin.railway.model.dto.StationDTO;
import com.epam.redkin.railway.model.entity.CarriageType;
import com.epam.redkin.railway.model.entity.Route;
import com.epam.redkin.railway.model.exception.DataBaseException;
import com.epam.redkin.railway.model.exception.IncorrectDataException;
import com.epam.redkin.railway.model.exception.ServiceException;
import com.epam.redkin.railway.model.repository.RouteRepository;
import com.epam.redkin.railway.model.service.RouteService;
import com.epam.redkin.railway.model.service.SeatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

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
        } catch (DataBaseException e) {
            throw new ServiceException("Failed to store route into database", e.getMessage());
        }
    }

    @Override
    public List<RouteOrderDTO> getRouteOrderDtoList(String departureStation, String arrivalStation,
                                                    LocalDateTime departureDate, String onlyFreeSeats) {
        Map<Integer, List<StationDTO>> routToStationMap = new HashMap<>();
        List<StationDTO> stations = routeRepository.getStationDTOListWithParameters(departureStation, arrivalStation);

        stations.forEach(stationDTO -> routToStationMap.computeIfAbsent(stationDTO.getRoutsId(), k -> new ArrayList<>()).add(stationDTO));

        List<RouteOrderDTO> result = new ArrayList<>();

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
                result.add(getOrderDTO(stationDTOS));
            }
        }

        fillAvailableSeats(result);
        if (StringUtils.isBlank(onlyFreeSeats)) {
            result = result.stream()
                    .filter(routeOrderDTO -> routeOrderDTO
                            .getAvailableSeats()
                            .values().stream()
                            .anyMatch(k -> k > 0))
                    .collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public void fillAvailableSeats(List<RouteOrderDTO> routeOrderDTOList) {
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
    public List<RouteOrderDTO> getSearchRoutePagination(String departureStation, String arrivalStation, LocalDateTime departureDate, int offset, int limit, String onlyFreeSeats) {
        List<RouteOrderDTO> routeOrderDTO = getRouteOrderDtoList(departureStation, arrivalStation, departureDate, onlyFreeSeats);
        return routeOrderDTO.subList(offset, Math.min(limit, routeOrderDTO.size()));
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


    private RouteOrderDTO getOrderDTO(List<StationDTO> stationDTOS) {
        RouteOrderDTO routeOrderDto = new RouteOrderDTO();
        routeOrderDto.setStations(stationDTOS);
        StationDTO stationDto = stationDTOS.get(0);
        routeOrderDto.setRoutName(stationDto.getRoutName());
        routeOrderDto.setRoutNumber(stationDto.getRoutNumber());
        routeOrderDto.setRoutsId(stationDto.getRoutsId());
        routeOrderDto.setTrainId(stationDto.getTrainId());
        routeOrderDto.setTrainNumber(stationDto.getTrainNumber());
        int countFirstClassSeats = seatService.getCountSeatByCarType(stationDto.getTrainId(), CarriageType.FIRST_CLASS);
        int countSecondClassSeats = seatService.getCountSeatByCarType(stationDto.getTrainId(), CarriageType.SECOND_CLASS);
        routeOrderDto.setFirstClassFreeSeatsCount(countFirstClassSeats);
        routeOrderDto.setSecondClassFreeSeatsCount(countSecondClassSeats);

        return routeOrderDto;
    }
}

