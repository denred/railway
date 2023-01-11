package com.epam.redkin.web.controller.command;

import com.epam.redkin.web.controller.command.common.HomeCommand;
import com.epam.redkin.web.controller.command.common.I18NCommand;
import com.epam.redkin.web.controller.command.common.LoginCommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory factory = new CommandFactory();
    private static Map<String, Command> commands = new HashMap<>();

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
        commands.put("home", new HomeCommand());
        commands.put("i18n", new I18NCommand());
        /* commands.put("logout", new LogoutCommand());
        commands.put("pdf_builder", new PdfBuilderCommand());
        commands.put("no_command", new NoCommand());*/

        commands.put("redirect", null);

        // admin commands
        /*commands.put("main", new MainCommand());
        commands.put("users", new ShowUserListCommand());
        commands.put("services", new EditServicesCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("edit_client", new EditClientCommand());
        commands.put("profile", new ProfileCommand());
        commands.put("add_tariff", new AddTariffCommand());
        commands.put("edit_tariff", new EditTariffCommand());
        commands.put("remove_tariff", new RemoveTariffCommand());*/

        // client commands
        /*commands.put("account", new AccountCommand());
        commands.put("personal_data", new PersonalDataCommand());
        commands.put("user_profile", new UserProfileCommand());
        commands.put("transactions", new TransactionCommand());
        commands.put("save_profile", new SaveUserProfileCommand());*/
    }


    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }
}
