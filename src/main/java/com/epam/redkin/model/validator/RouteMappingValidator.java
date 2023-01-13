package com.epam.redkin.model.validator;


import com.epam.redkin.model.dto.MappingInfoDTO;
import com.epam.redkin.model.entity.RoutePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RouteMappingValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMappingValidator.class);
    private static final String STATION_ARRIVAL_DATE = "^(19|20)\\d\\d-(0[1-9]|1[012])-([012]\\d|3[01])T([01]\\d|2[0-3]):([0-5]\\d)$";
    private static final String STATION_DISPATCH_DATA = "^(19|20)\\d\\d-(0[1-9]|1[012])-([012]\\d|3[01])T([01]\\d|2[0-3]):([0-5]\\d)$";
    private static final String STATION_ORDER = "(?<![-\\d])(?<!\\d[.,])\\d*[0-9](?![.,]?\\d){1,2}";

    public static boolean contains(final List<MappingInfoDTO> array, final LocalDateTime localDateTime) {

        boolean result = false;

        for (MappingInfoDTO mappingInfoDto : array) {
            if (mappingInfoDto.getStationArrivalDate().isAfter(localDateTime)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void isValidRoutToStationMapping(RoutePoint routToStationMapping, List<MappingInfoDTO> mappingList) {

        Map<String, String> errors = new HashMap<>();

        String arrivalDate = String.valueOf(routToStationMapping.getArrival());
        String dispatchDate = String.valueOf(routToStationMapping.getDispatch());

        if (Objects.isNull(routToStationMapping.getArrival()) || !ValidatorUtils.isMatch(STATION_ARRIVAL_DATE, arrivalDate)) {
            errors.put("Incorrect format, type something like \"2020-04-06T17:11\"", arrivalDate);
        }
        if (Objects.isNull(routToStationMapping.getDispatch()) || !ValidatorUtils.isMatch(STATION_DISPATCH_DATA, String.valueOf(routToStationMapping.getDispatch()))) {
            errors.put("Incorrect format, type something like \"2020-04-06T17:11\"", String.valueOf(routToStationMapping.getDispatch()));
        }
        if (!ValidatorUtils.isMatch(STATION_ORDER, String.valueOf(routToStationMapping.getOrderId()))) {
            errors.put("Incorrect format, type something like \"123\"", String.valueOf(routToStationMapping.getOrderId()));
        }
        if (routToStationMapping.getArrival().isEqual(routToStationMapping.getDispatch())) {
            errors.put("Incorrect format, \"time should be different\"", arrivalDate + " = " + dispatchDate);
        }
        if (routToStationMapping.getArrival().isAfter(routToStationMapping.getDispatch())) {
            errors.put("Incorrect format, \"time should be different\"", arrivalDate + " > " + dispatchDate);
        }
        if (contains(mappingList, routToStationMapping.getArrival())) {
            errors.put("Incorrect format, \"time should be different\"", arrivalDate + " already exists");
        }
        ValidatorUtils.errorBuilder(errors, LOGGER);

    }


    public void isValidUpdateRoutToStationMapping(RoutePoint routToStationMapping) {

        Map<String, String> errors = new HashMap<>();

        String arrivalDate = String.valueOf(routToStationMapping.getArrival());
        String dispatchDate = String.valueOf(routToStationMapping.getDispatch());

        if (Objects.isNull(routToStationMapping.getArrival()) || !ValidatorUtils.isMatch(STATION_ARRIVAL_DATE, arrivalDate)) {
            errors.put("Incorrect format, type something like \"2020-04-06T17:11\"", arrivalDate);
        }
        if (Objects.isNull(routToStationMapping.getDispatch()) || !ValidatorUtils.isMatch(STATION_DISPATCH_DATA, String.valueOf(routToStationMapping.getDispatch()))) {
            errors.put("Incorrect format, type something like \"2020-04-06T17:11\"", String.valueOf(routToStationMapping.getDispatch()));
        }
        if (!ValidatorUtils.isMatch(STATION_ORDER, String.valueOf(routToStationMapping.getOrderId()))) {
            errors.put("Incorrect format, type something like \"123\"", String.valueOf(routToStationMapping.getOrderId()));
        }
        if (routToStationMapping.getArrival().isEqual(routToStationMapping.getDispatch())) {
            errors.put("Incorrect format, \"time should be different\"", arrivalDate + " = " + dispatchDate);
        }
        if (routToStationMapping.getArrival().isAfter(routToStationMapping.getDispatch())) {
            errors.put("Incorrect format, \"time should be different\"", arrivalDate + " > " + dispatchDate);
        }
        ValidatorUtils.errorBuilder(errors, LOGGER);
    }
}

