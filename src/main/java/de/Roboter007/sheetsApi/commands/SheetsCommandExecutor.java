package de.Roboter007.sheetsApi.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

public abstract class SheetsCommandExecutor implements CommandExecutor, TabCompleter {

    @NotNull
    public abstract String getName();
}
