package de.Roboter007.sheets;

import de.Roboter007.sheets.api.listeners.SheetsListeners;
import de.Roboter007.sheets.listener.SheetsListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SheetsApi {

    public static String NAME = "Sheets Api";
    public static String VERSION = "v0.3.8";

    private static final Logger SHEETS_LOGGER = Logger.getLogger(NAME);

    private static final List<SheetsPlugin> plugins = new ArrayList<>();

    public static List<SheetsPlugin> getPlugins() {
        return plugins;
    }

    public static void addPluginToApi(@NotNull SheetsPlugin plugin) {
        plugins.add(plugin);
    }

    public static Logger getLogger() {
        return SHEETS_LOGGER;
    }

}
