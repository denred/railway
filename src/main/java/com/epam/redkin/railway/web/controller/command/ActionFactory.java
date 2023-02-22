package com.epam.redkin.railway.web.controller.command;

import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.web.controller.command.admin.*;
import com.epam.redkin.railway.web.controller.command.common.*;
import com.epam.redkin.railway.web.controller.command.user.ForgetPasswordEmailSendingCommand;
import com.epam.redkin.railway.web.controller.command.user.LoginByTokenLinkCommand;
import com.epam.redkin.railway.web.controller.command.user.RegistrationApprovalCommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory method for executing from request commands
 * The method extracts the value of the command parameter from the request and, based on it,
 * extracts the command object corresponding to the request.
 */
public class ActionFactory {
    private static ActionFactory factory = new ActionFactory();
    private static final Map<String, Command> commands = new HashMap<>();

    private ActionFactory() {
    }
    // Singleton
    public static ActionFactory getInstance() {
        if (factory == null) {
            factory = new ActionFactory();
        }
        return factory;
    }

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegistrationCommand());
        commands.put("home", new HomeCommand());
        commands.put("i18n", new I18NCommand());
        commands.put("profile", new ProfileCommand());
        commands.put("profilePage", new ProfilePageCommand());
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
        commands.put("carriages_page", new CarriageInfoPageCommand());
        commands.put("trains", new TrainInfoCommand(AppContext.getInstance()));
        commands.put("trains_page", new TrainInfoPageCommand());
        commands.put("admin_orders", new OrderInfoCommand());
        commands.put("users", new UserInfoCommand());
        commands.put("block", new UserBlockCommand());
        commands.put("order_status", new OrderChangeStatusCommand());
        commands.put("set_train", new TrainSetCommand());
        commands.put("remove_train", new TrainRemoveCommand());
        commands.put("set_carriage", new CarriageSetCommand());
        commands.put("add_carriage", new CarriageAddCommand());
        commands.put("set_carriage_page", new CarriageSetPageCommand());
        commands.put("remove_carriage", new CarriageRemoveCommand());

        // user commands
        commands.put("search_routes", new SearchRoutesCommand());
        commands.put("search_page", new SearchRoutesPage());
        commands.put("orders", new GetUserOrdersCommand());
        commands.put("route", new DetailRouteCommand());
        commands.put("route_detail", new DetailRoutePage());
        commands.put("select_station_and_carriage_type", new SelectStationAndCarriageTypeCommand());
        commands.put("select_carriage_and_count_seats", new SelectCarriageAndSeatsCommand());
        commands.put("select_carriage_and_seats", new SelectCarriageAndSeatsPage());
        commands.put("select_seats", new SelectSeatsCommand());
        commands.put("select_seats_page", new SelectSeatsPage());
        commands.put("confirm_order", new ConfirmOrderCommand());
        commands.put("create_order", new CreateOrderCommand());
        commands.put("cancel_order", new CancelOrderCommand());
        commands.put("sendForgetPasswordData", new ForgetPasswordEmailSendingCommand());
        commands.put("login_by_token_link", new LoginByTokenLinkCommand());
        commands.put("postRegistrationAccountApproval", new RegistrationApprovalCommand());
    }


    public Command defineCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }
}
