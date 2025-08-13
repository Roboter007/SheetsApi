package de.Roboter007.sheets.api.commands;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class SheetsCommands {

    public static void registerAllCommands(JavaPlugin plugin) {
        plugin.getLogger().info("Loading Commands for " + plugin.getName());

        String packageName = plugin.getClass().getPackage().getName();
        String targetLocation = packageName + ".commands";

        for (Class<? extends SheetsCommandExecutor> clazz : new Reflections(targetLocation).getSubTypesOf(SheetsCommandExecutor.class)) {
            try {
                SheetsCommandExecutor commandExecutor = clazz.getDeclaredConstructor().newInstance();
                PluginCommand pluginCommand = plugin.getCommand(commandExecutor.getName());
                plugin.getLogger().info("Registered Command: " + commandExecutor.getName());
                if (pluginCommand != null) {
                    pluginCommand.setExecutor(commandExecutor);
                } else {
                    plugin.getLogger().warning("Failed to register Commands: " + commandExecutor.getName() + ", because you have forgotten to implement it in the plugin.yml");
                }

            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                plugin.getLogger().severe(e.fillInStackTrace().toString());
            }
        }
    }
}
