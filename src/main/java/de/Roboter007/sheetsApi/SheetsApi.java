package de.Roboter007.sheetsApi;

import de.Roboter007.sheetsApi.commands.SheetsCommands;
import de.Roboter007.sheetsApi.data.player.PlayerDataHandler;
import de.Roboter007.sheetsApi.listeners.SheetsListeners;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class SheetsApi {

    private static JavaPlugin plugin;

    @NotNull
    public static JavaPlugin getPlugin() {
        if(plugin != null) {
            return plugin;
        } else {
            throw new NullPointerException();
        }
    }

    public static Logger getPluginLogger() {
        return getPlugin().getLogger();
    }

    public static void setPlugin(@NotNull JavaPlugin plugin) {
        SheetsApi.plugin = plugin;
    }

    public static void onLoad(JavaPlugin plugin) {
        setPlugin(plugin);

        SheetsCommands.registerAllCommands();
        SheetsListeners.registerAllListeners();

        PlayerDataHandler.loadPlayerConfigList();
    }
}
