package net.xilla.discord.manager.command.console;


import lombok.Getter;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.StoredData;
import net.xilla.discord.api.command.Command;
import net.xilla.discord.api.command.CommandExecutor;

import java.util.List;

public class ConsoleCommand extends ManagerObject implements Command {

    @Getter
    @StoredData
    private String description;

    @Getter
    @StoredData
    private String permission;

    @Getter
    @StoredData
    private List<String> activators;

    @Getter
    private CommandExecutor executor;

    public ConsoleCommand(String name, String description, String permission, List<String> activators, CommandExecutor executor) {
        super(name, "ConsoleCommand");

        this.description = description;
        this.permission = permission;
        this.activators = activators;
        this.executor = executor;
    }

    public ConsoleCommand() {}

    @Override
    public String getName() {
        return toString();
    }

}