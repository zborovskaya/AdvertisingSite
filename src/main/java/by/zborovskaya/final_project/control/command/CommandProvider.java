package by.zborovskaya.final_project.control.command;

import by.zborovskaya.final_project.control.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private static final Logger logger = LogManager.getLogger(CommandProvider.class);
    private final Map<CommandName, Command> repository = new HashMap<CommandName, Command>();
    public CommandProvider() {
        repository.put(CommandName.SHOWALLADVERTISEMENTS, new ShowAllAdvertisement());
        repository.put(CommandName.GOTOINDEXPAGE, new GoToIndexPage());
        repository.put(CommandName.CHANGELOCALEN, new ChangeLocalEn());
        repository.put(CommandName.CHANGELOCALRU, new ChangeLocalRu());
        repository.put(CommandName.SIGNIN, new SignIn());
        repository.put(CommandName.LOGINATION, new Logination());
        repository.put(CommandName.REGISTRATION, new Registration());
        repository.put(CommandName.SAVENEWUSER, new SaveNewUser());
        repository.put(CommandName.GOTOCLIENTACCOUNT, new GoToClientAccount());
        repository.put(CommandName.LOGOUT, new Logout());
        repository.put(CommandName.RUNNER, new Runner());

    }

    public Command getCommand(String name){
        CommandName commandName =null;
        Command command = null;
        try{
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        }catch(Exception e){
            logger.error("Request cannot be made");
        }
        return command;
    }
}
