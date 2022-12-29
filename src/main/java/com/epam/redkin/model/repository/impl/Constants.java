package com.epam.redkin.model.repository.impl;

public interface Constants {
    /* CARRIAGE */
    String ADD_CARRIAGE = "INSERT INTO carriage (type, number, train_id) VALUES (?,?,?)";
    String GET_CARRIAGE_BY_ID = "SELECT * FROM carriage WHERE id = ?";
    String UPDATE_CARRIAGE = "UPDATE carriage SET type = ?, number = ?, train_id = ? WHERE id = ?";
    String DELETE_CARRIAGE = "DELETE FROM carriage WHERE id = ?";
    String GET_CARRIAGES_BY_TRAIN_ID = "SELECT * FROM carriage WHERE train_id = ?";
    String GET_CARRIAGES_BY_TRAIN_ID_AND_TYPE = "SELECT * FROM carriage WHERE train_id = ? AND type = ? ORDER BY id";
    String GET_ALL_CARRIAGE = "SELECT c.id, type, c.number, train_id,t.number " +
            "FROM carriage as c LEFT OUTER JOIN train as t\n" +
            "ON train_id = t.id ORDER BY t.number, c.number";

    /* SEAT */
    String ADD_SEAT = "INSERT INTO seat (seat_number, busy, carriage_id) VALUES (?,?,?)";
    String GET_SEAT_BY_ID = "SELECT * FROM seat WHERE id = ?";
    String UPDATE_SEAT = "UPDATE seat SET seat_number = ?, busy = ?, carriage_id = ? WHERE id = ?";
    String DELETE_SEAT = "DELETE FROM seat WHERE id = ?";
    String DELETE_SEATS_BY_CARRIAGE = "DELETE FROM seat WHERE carriage_id = ?";
    String GET_SEATS_IN_CARRIAGE = "SELECT COUNT(*) FROM seat WHERE carriage_id = ?";
    String GET_SEATS_IN_CARRIAGE_BUSY = "SELECT COUNT(*) FROM seat WHERE carriage_id = ? AND busy = TRUE";
    String GET_SEATS_IN_TRAIN_BY_TYPE = "SELECT COUNT(seat_number) as count FROM seat " +
            "JOIN carriage ON carriage.id = seat.carriage_id " +
            "JOIN train ON train.id = carriage.train_id " +
            "WHERE train_id = ? AND carriage.type = ? AND busy = FALSE";
    String GET_LIST_SEATS_BY_CARRIAGE_ID = "SELECT * FROM seat WHERE carriage_id = ?";
    String GET_SEAT_NUMBER_BY_ID_BATCH = "SELECT * FROM seat WHERE id IN (%s)";
    String TAKE_IN_SEAT = "UPDATE seat SET busy = true WHERE id = ?";
    String TAKE_OFF_SEAT = "UPDATE seat SET busy = false WHERE id = ?";

    /* STATION */
    String ADD_STATION = "INSERT INTO station (station) VALUES (?)";
    String GET_STATION_BY_ID = "SELECT * FROM station WHERE id = ?";
    String UPDATE_STATION = "UPDATE station SET station = ? WHERE id = ?";
    String DELETE_STATION = "DELETE FROM station WHERE id = ?";
    String GET_ALL_STATIONS = "SELECT * FROM station";

    /* TRAIN */
    String ADD_TRAIN = "INSERT INTO train (number) VALUES (?)";
    String GET_TRAIN_BY_ID = "SELECT * FROM train WHERE id = ?";
    String UPDATE_TRAIN = "UPDATE train SET number = ? WHERE id = ?";
    String DELETE_TRAIN = "DELETE FROM train WHERE id = ?";
    String GET_ALL_TRAINS = "SELECT * FROM train";

    /* USER */

    String ADD_USER = "INSERT INTO user (email, password, first_name, last_name, phone, birth_date, role, blocked) " +
            "VALUES (?,?,?,?,?,?,?,?)";
    String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    String UPDATE_USER = "UPDATE user SET email = ?, password = ?, first_name = ?, last_name = ?, phone = ?, " +
            "birth_date = ?, role = ?, blocked = ? WHERE id = ?";
    String DELETE_USER = "DELETE FROM user WHERE id = ?";
    String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ? LIMIT 1";
    String GET_USERS_BY_ROLE = "SELECT * FROM user WHERE role = ? ORDER BY id";
    String UPDATE_BLOCKED = "UPDATE user SET blocked = ? WHERE id = ?";

    /* ROUTES */
    String GET_ROUTE_LIST_WITH_PARAMETERS = "SELECT r.name, r.number, r.id, station, " +
            "s.id, t.number, r.train_id, station_arrival, station_dispatch, pnr " +
            "FROM route as r " +
            "JOIN train as t on r.train_id = t.id " +
            "JOIN station_has_route as shr on shr.route_id = r.id " +
            "JOIN station as s on shr.station_id = s.id " +
            "WHERE station IN (?, ?) " +
            "ORDER BY station_dispatch, r.name , r.number";

    String GET_ROUTE_INFO_BY_ID = "SELECT r.id, r.train_id, r.name, r.number, t.number "
            + "FROM route as r "
            + "JOIN train as t on r.train_id = t.id "
            + "WHERE r.id = ?";

    String ADD_ROUTE = "INSERT INTO route (name, number, train_id) VALUES (?,?,?)";
    String UPDATE_ROUTE = "UPDATE route SET name = ?, number = ?, train_id = ? WHERE id = ?";
    String DELETE_ROUTE = "DELETE FROM route WHERE id = ?";
    String GET_ALL_ROUTES = "SELECT r.id, r.train_id, r.name, r.number, t.number " +
            "FROM route as r " +
            "JOIN train as t on r.train_id = t.id " +
            "ORDER BY t.number, r.name";

    /* ROUTE MAPPING */
    String GET_ALL_ROUTE_MAPPING = "SELECT * FROM station_has_route";
    String ADD_ROUTE_MAPPINGS = "INSERT INTO station_has_route as rm " +
            "(station_id, route_id, station_arrival, station_dispatch, pnr) VALUES (?, ?, ?, ?, ?)";
    String GET_ROUT_MAPPING_BY_ID = "SELECT * FROM station_has_route WHERE route_id = ?";
    String UPDATE_ROUTE_MAPPING = "UPDATE station_has_route " +
            "SET station_id = ?, " +
            "station_arrival = ?, " +
            "station_dispatch = ?, " +
            "pnr = ?  " +
            "WHERE route_id = ? AND station_id= ?";

    String DELETE_ROUTE_MAPPING = "DELETE FROM station_has_route WHERE route_id = ? AND station_id = ?";
    String GET_ROUTE_MAPPING_BY_ROUTE_ID = "SELECT * FROM station_has_route as rm " +
            "JOIN station as s ON rm.station_id = s.id " +
            "WHERE route_id = ? ORDER BY pnr";
    String GET_ROUTE_MAPPING_BY_STATION_AND_ROUTE_ID = "SELECT * FROM station_has_route as rm " +
            "JOIN station as s ON rm.station_id = s.id " +
            "WHERE rm.route_id = ? AND rm.station_id = ? ORDER BY pnr";

    /* ORDER */
    String ADD_ORDER = "INSERT INTO booking " +
            "(booking_date, " +
            "route_id, " +
            "dispatch_station, " +
            "dispatch_date, " +
            "arrival_station, " +
            "arrival_date, " +
            "travel_time, " +
            "train_number, " +
            "carriage_number, " +
            "carriage_type, " +
            "seat_count, " +
            "seat_number, " +
            "seat_id, " +
            "price," +
            "status, " +
            "user_id) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    String UPDATE_ORDER_STATUS = "UPDATE booking SET status = ? WHERE id = ?";
    String GET_ALL_ORDER = "SELECT * FROM booking JOIN user ON user_id = user.id";
    String GET_ORDER_BY_USER_ID = "SELECT * FROM booking JOIN user ON user_id = user.id WHERE user_id = ?";
    String GET_THE_PRICE_OF_SUCCESSFUL_ORDERS = "SELECT SUM(price) FROM booking WHERE user_id = ? AND order_status = 'ACCEPTED'";
}
