package com.epam.redkin.railway.model.repository.impl;

public interface Constants {
    // Query

    /* CARRIAGE */
    String ADD_CARRIAGE = "INSERT INTO carriage (type, number, train_id) VALUES (?,?,?)";
    String GET_CARRIAGE_BY_ID = "SELECT * FROM carriage WHERE id = ?";
    String UPDATE_CARRIAGE = "UPDATE carriage SET type = ?, number = ?, train_id = ? WHERE id = ?";
    String DELETE_CARRIAGE = "DELETE FROM carriage WHERE id = ?";
    String GET_CARRIAGES_BY_TRAIN_ID = "SELECT * FROM carriage WHERE train_id = ?";
    String GET_CARRIAGES_BY_TRAIN_ID_AND_TYPE = "SELECT * FROM carriage" +
            " JOIN seat ON carriage.id=seat.carriage_id " +
            "WHERE carriage.train_id = ? AND type = ? AND busy = false ORDER BY carriage.id";
    String GET_ALL_CARRIAGE = "SELECT c.id as carriage_id, " +
            "type, " +
            "c.number as carriage_number, " +
            "train_id, " +
            "t.number as train_number " +
            "FROM carriage as c LEFT OUTER JOIN train as t\n" +
            "ON train_id = t.id ORDER BY t.number, c.number";

    /* SEAT */
    String ADD_SEAT = "INSERT INTO seat (seat_number, busy, carriage_id) VALUES (?,?,?)";
    String GET_SEAT_BY_ID = "SELECT * FROM seat WHERE id = ?";
    String UPDATE_SEAT = "UPDATE seat SET seat_number = ?, busy = ?, carriage_id = ? WHERE id = ?";
    String DELETE_SEAT = "DELETE FROM seat WHERE id = ?";
    String DELETE_SEATS_BY_CARRIAGE_ID = "DELETE FROM seat WHERE carriage_id = ?";
    String GET_SEATS_COUNT_IN_CARRIAGE = "SELECT COUNT(*) as count FROM seat WHERE carriage_id = ? AND busy = false";
    String GET_BUSY_SEATS_COUNT_IN_CARRIAGE = "SELECT COUNT(*) as count FROM seat WHERE carriage_id = ? AND busy = TRUE";
    String GET_SEATS_IN_TRAIN_BY_TYPE = "SELECT COUNT(seat_number) as count FROM seat " +
            "JOIN carriage ON carriage.id = seat.carriage_id " +
            "JOIN train ON train.id = carriage.train_id " +
            "WHERE train_id = ? AND carriage.type = ? AND busy = FALSE";
    String GET_LIST_SEATS_BY_CARRIAGE_ID = "SELECT * FROM seat WHERE carriage_id = ? AND busy = FALSE";
    String GET_SEATS_LIST_BY_ID_BATCH = "SELECT * FROM seat WHERE id IN (%s)";
    String TAKE_IN_SEAT = "UPDATE seat SET busy = true WHERE id = ?";
    String TAKE_OFF_SEAT = "UPDATE seat SET busy = 0 WHERE seat_number = ?";

    /* STATION */
    String ADD_STATION = "INSERT INTO station (station) VALUES (?)";
    String GET_STATION_BY_ID = "SELECT * FROM station WHERE id = ?";
    String UPDATE_STATION = "UPDATE station SET station = ? WHERE id = ?";
    String DELETE_STATION = "DELETE FROM station WHERE id = ?";
    String GET_ALL_STATIONS = "SELECT * FROM station";
    String GET_STATIONS_SEARCH = "SELECT * FROM station %s ORDER BY station LIMIT ? , ?";
    String GET_COUNT_STATIONS_SEARCH = "SELECT COUNT(station) as count FROM station %s";

    /* TRAIN */
    String ADD_TRAIN = "INSERT INTO train (number) VALUES (?)";
    String GET_TRAIN_BY_ID = "SELECT * FROM train WHERE id = ?";
    String UPDATE_TRAIN = "UPDATE train SET number = ? WHERE id = ?";
    String DELETE_TRAIN = "DELETE FROM train WHERE id = ?";
    String GET_ALL_TRAINS = "SELECT * FROM train";

    /* USER */

    String ADD_USER = "INSERT INTO user (email, password, first_name, last_name, phone, birth_date, role, blocked, log_in_token) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";
    String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    String UPDATE_USER = "UPDATE user SET email = ?, password = ?, first_name = ?, last_name = ?, phone = ?, " +
            "birth_date = ?, role = ?, blocked = ?, log_in_token = ? WHERE id = ?";
    String DELETE_USER = "DELETE FROM user WHERE id = ?";
    String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ? LIMIT 1";
    String GET_USERS_BY_ROLE = "SELECT * FROM user WHERE role = ? %s ORDER BY id LIMIT ? , ?";
    String UPDATE_BLOCKED = "UPDATE user SET blocked = ? WHERE id = ?";
    String UPDATE_USER_LOG_IN_TOKEN_BY_ID = "UPDATE user SET user.log_in_token = (?) WHERE user.id = (?)";
    String FIND_USER_BY_ID_AND_TOKEN = "SELECT * FROM user WHERE id = (?) AND log_in_token = (?)";
    String UPDATE_USER_LOG_IN_TOKEN_TO_NULL = "UPDATE user SET log_in_token = NULL WHERE id = (?)";
    String GET_COUNT_OF_USERS = "SELECT count(*) as count FROM user WHERE role = 'user' %s";

    /* ROUTES */
    String GET_ROUTE_LIST_WITH_PARAMETERS = "SELECT " +
            "r.name as route_name, " +
            "r.number as route_number, " +
            "r.id as route_id, " +
            "station, " +
            "s.id as station_id, " +
            "t.number as train_number, " +
            "r.train_id, " +
            "station_arrival, " +
            "station_dispatch, " +
            "station_order " +
            "FROM route as r " +
            "JOIN train as t on r.train_id = t.id " +
            "JOIN station_has_route as shr on shr.route_id = r.id " +
            "JOIN station as s on shr.station_id = s.id " +
            "WHERE station IN (?, ?) " +
            "ORDER BY station_dispatch, r.name , r.number";

    String GET_ROUTE_INFO_BY_ID = "SELECT r.id as route_id, " +
            "r.train_id as train_id, " +
            "r.name as route_name, " +
            "r.number as route_number, " +
            "t.number as train_number "
            + "FROM route as r "
            + "JOIN train as t on r.train_id = t.id "
            + "WHERE r.id = ?";

    String ADD_ROUTE = "INSERT INTO route (name, number, train_id) VALUES (?,?,?)";
    String UPDATE_ROUTE = "UPDATE route SET name = ?, number = ?, train_id = ? WHERE id = ?";
    String DELETE_ROUTE = "DELETE FROM route WHERE id = ?";
    String GET_ALL_ROUTES = "SELECT r.id as route_id, r.train_id as train_id, r.name as route_name, r.number as route_number, t.number as train_number " +
            "FROM route as r " +
            "JOIN train as t on r.train_id = t.id " +
            "ORDER BY t.number, r.name";

    /* ROUTE MAPPING */
    String GET_ALL_ROUTE_MAPPING = "SELECT * FROM station_has_route";
    String ADD_ROUTE_MAPPINGS = "INSERT INTO station_has_route " +
            "(station_id, route_id, station_arrival, station_dispatch, station_order) VALUES (?, ?, ?, ?, ?)";
    String GET_ROUT_MAPPING_BY_ID = "SELECT * FROM station_has_route WHERE route_id = ?";
    String UPDATE_ROUTE_MAPPING = "UPDATE station_has_route " +
            "SET station_id = ?, " +
            "station_arrival = ?, " +
            "station_dispatch = ?, " +
            "station_order = ?  " +
            "WHERE route_id = ? AND station_id= ?";

    String DELETE_ROUTE_MAPPING = "DELETE FROM station_has_route WHERE route_id = ? AND station_id = ?";
    String GET_ROUTE_MAPPING_BY_ROUTE_ID = "SELECT * FROM station_has_route as rm " +
            "JOIN station as s ON rm.station_id = s.id " +
            "WHERE route_id = ? ORDER BY station_order";
    String GET_ROUTE_MAPPING_BY_STATION_AND_ROUTE_ID = "SELECT * FROM station_has_route as rm " +
            "JOIN station as s ON rm.station_id = s.id " +
            "WHERE rm.route_id = ? AND rm.station_id = ? ORDER BY station_order";

    /* ORDER */
    String ADD_ORDER = "INSERT INTO `booking` (`booking_date`, `route_id`, `dispatch_station`, `dispatch_date`,\n" +
            "                                 `arrival_station`, `arrival_date`, `travel_time`, `train_number`, `carriage_number`,\n" +
            "                                 `carriage_type`, `seat_count`, `seat_number`, `seats_id`, `user_id`, `price`, `status`)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    String READ_ORDER_BY_ORDER_ID = "SELECT * FROM booking JOIN user on user_id = user.id WHERE booking.id = ?";

    String UPDATE_ORDER_STATUS = "UPDATE booking SET status = ? WHERE id = ?";
    String GET_ALL_ORDER = "SELECT * FROM booking JOIN user ON user_id = user.id";
    String GET_ORDER_BY_USER_ID = "SELECT * FROM booking JOIN user ON user_id = user.id WHERE user_id = ?";
    String GET_THE_PRICE_OF_SUCCESSFUL_ORDERS = "SELECT SUM(price) as sum FROM booking WHERE user_id = ? AND status = 'ACCEPTED'";

    // Constants

    String ID = "id";
    String TOTAL = "sum";
    String BUSY = "busy";
    String COUNT = "count";
    String TRAIN_NUMBER = "train_number";
    String TRAIN_ID = "train_id";
    String NUMBER = "number";
    String CARRIAGE_TYPE =  "carriage_type";
    String TYPE =  "type";
    String PRICE =  "price";
    String ARRIVAL_DATE =  "arrival_date";
    String DISPATCH_DATE =  "dispatch_date";
    String BOOKING_DATE =  "booking_date";
    String STATUS =  "status";
    String SEATS_COUNT =  "seat_count";
    String ARRIVAL_STATION =  "arrival_station";
    String DISPATCH_STATION =  "dispatch_station";
    String STATION =  "station";
    String STATION_ID =  "station_id";
    String STATION_ARRIVAL_DATE =  "station_arrival";
    String STATION_DISPATCH_DATE =  "station_dispatch";
    String STATION_ORDER =  "station_order";
    String TRAVEL_TIME =  "travel_time";
    String ROUTE_ID =  "route_id";
    String ROUTE_NAME =  "route_name";
    String ROUTE_NUMBER =  "route_number";
    String CARRIAGE_NUMBER =  "carriage_number";
    String CARRIAGE_ID =  "carriage_id";
    String SEAT_NUMBER =  "seat_number";
    String SEATS_ID =  "seats_id";
    String EMAIL =  "email";
    String PASSWORD =  "password";
    String FIRST_NAME =  "first_name";
    String LAST_NAME =  "last_name";
    String PHONE =  "phone";
    String BIRTH_DATE =  "birth_date";
    String ROLE =  "role";
    String BLOCKED =  "blocked";

}
