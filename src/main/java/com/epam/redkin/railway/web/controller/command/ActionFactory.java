package com.epam.redkin.railway.web.controller.command;

import com.epam.redkin.railway.appcontext.AppContext;
import com.epam.redkin.railway.model.validator.RouteMappingValidator;
import com.epam.redkin.railway.model.validator.SearchValidator;
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
        commands.put("login", new LoginCommand(AppContext.getInstance()));
        commands.put("login_page", new GetLoginPageCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegistrationCommand(AppContext.getInstance()));
        commands.put("home", new HomeCommand());
        commands.put("i18n", new I18NCommand());
        commands.put("profile", new ProfileCommand());
        commands.put("profilePage", new ProfilePageCommand());
        commands.put("redirect", null);
        commands.put("view_ticket", new ViewTicketPageCommand());

        // admin commands
        commands.put("routes", new RouteInfoCommand());
        commands.put("add_route", new RouteAddCommand(AppContext.getInstance().getRouteService(), AppContext.getInstance().getTrainService()));
        commands.put("edit_route", new RouteEditCommand());
        commands.put("delete_route", new RouteDeleteCommand());
        commands.put("route_mapping", new RouteMappingCommand(AppContext.getInstance().getRouteMappingService()));
        commands.put("route_mapping_set_station", new RouteMappingSetStationCommand(AppContext.getInstance().getRouteMappingService(), AppContext.getInstance().getStationService(), new RouteMappingValidator()));
        commands.put("route_mapping_remove_station", new RouteMappingRemoveCommand());
        commands.put("stations", new StationInfoCommand());
        commands.put("set_station", new StationSetCommand());
        commands.put("delete_station", new StationRemoveCommand());
        commands.put("carriages", new CarriageInfoCommand());
        commands.put("carriages_page", new CarriageInfoPageCommand());
        commands.put("trains", new TrainInfoCommand(AppContext.getInstance()));
        commands.put("trains_page", new TrainInfoPageCommand());
        commands.put("admin_orders", new OrderInfoCommand(AppContext.getInstance()));
        commands.put("users", new UserInfoCommand(AppContext.getInstance()));
        commands.put("users_page", new UserInfoGetPageCommand());
        commands.put("block", new UserBlockCommand());
        commands.put("order_status", new OrderChangeStatusCommand());
        commands.put("order_detail", new OrderDetailGetPage());
        commands.put("set_train", new TrainSetCommand());
        commands.put("remove_train", new TrainRemoveCommand());
        commands.put("set_carriage", new CarriageSetCommand());
        commands.put("add_carriage", new CarriageAddCommand());
        commands.put("set_carriage_page", new CarriageSetPageCommand());
        commands.put("remove_carriage", new CarriageRemoveCommand());

        // user commands
        commands.put("search_routes", new SearchRoutesCommand(AppContext.getInstance().getRouteService(), AppContext.getInstance().getPaginationService(), new SearchValidator()));
        commands.put("search_page", new SearchRoutesPage());
        commands.put("orders", new GetUserOrdersCommand());
        commands.put("route", new DetailRouteCommand());
        commands.put("route_detail", new DetailRoutePage());
        commands.put("select_station_and_carriage_type", new SelectStationAndCarriageTypeCommand(AppContext.getInstance().getCarriageService(), AppContext.getInstance().getRouteMappingService()));
        commands.put("select_carriage_and_count_seats", new SelectCarriageAndSeatsCommand());
        commands.put("select_carriage_and_seats", new SelectCarriageAndSeatsPage());
        commands.put("select_seats", new SelectSeatsCommand());
        commands.put("select_seats_page", new SelectSeatsPage());
        commands.put("confirm_order", new ConfirmOrderCommand(AppContext.getInstance().getOrderService()));
        commands.put("create_order", new CreateOrderCommand());
        commands.put("cancel_order", new CancelOrderCommand());
        commands.put("sendForgetPasswordData", new ForgetPasswordEmailSendingCommand(AppContext.getInstance().getUserService()));
        commands.put("login_by_token_link", new LoginByTokenLinkCommand(AppContext.getInstance().getUserService()));
        commands.put("postRegistrationAccountApproval", new RegistrationApprovalCommand());
    }


    public Command defineCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }
}
