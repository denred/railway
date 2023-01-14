package com.epam.redkin.web.controller.command;

import com.epam.redkin.web.controller.command.admin.*;
import com.epam.redkin.web.controller.command.common.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory factory = new CommandFactory();
    private static final Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {
    }

    public static CommandFactory commandFactory() {
        if (factory == null) {
            factory = new CommandFactory();
        }
        return factory;
    }

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("home", new HomeCommand());
        commands.put("i18n", new I18NCommand());

        commands.put("redirect", null);

        // admin commands
        commands.put("routes", new RouteInfoCommand());
        commands.put("add_route", new RouteAddCommand());
        commands.put("edit_route", new RouteEditCommand());
        commands.put("delete_route", new RouteDeleteCommand());
        commands.put("route_mapping", new RouteMappingCommand());
        commands.put("route_mapping_set_station", new RouteMappingSetStationCommand());
        commands.put("route_mapping_remove_station", new RouteMappingRemoveCommand());
        commands.put("stations", new StationInfoCommand());
        commands.put("set_station", new StationSetCommand());
        commands.put("delete_station", new StationRemoveCommand());
        commands.put("carriages", new CarriageInfoCommand());
        commands.put("trains", new TrainInfoCommand());
        commands.put("admin_orders", new OrderInfoCommand());
        commands.put("users", new UserInfoCommand());
        commands.put("block", new UserBlockCommand());
        commands.put("order_status", new OrderChangeStatusCommand());
        commands.put("set_train", new TrainSetCommand());
        commands.put("remove_train", new TrainRemoveCommand());
        commands.put("set_carriage", new CarriageSetCommand());
        commands.put("remove_carriage", new CarriageRemoveCommand());


        // user commands
        commands.put("search_routes", new SearchRoutesCommand());
        commands.put("orders", new GetUserOrdersCommand());
        commands.put("route", new DetailRouteCommand());
        commands.put("select_station_and_carriage_type", new SelectStationAndCarriageTypeCommand());
        commands.put("select_carriage_and_count_seats", new SelectCarriageAndSeatsCommand());
        commands.put("select_seats", new SelectSeatsCommand());
        commands.put("confirm_order", new ConfirmOrderCommand());
        commands.put("create_order", new CreateOrderCommand());
        commands.put("cancel_order", new CancelOrderCommand());
    }


    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }
}
