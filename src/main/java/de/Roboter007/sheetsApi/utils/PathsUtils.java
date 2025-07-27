package de.Roboter007.sheetsApi.utils;

import de.Roboter007.sheetsApi.SheetsApi;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.nio.file.*;

public class PathsUtils {

    public static Path PLUGINS_FOLDER_PATH = Bukkit.getPluginsFolder().toPath();
    public static Path PLUGIN_FOLDER_PATH = SheetsApi.getPlugin().getDataPath();

    public static Path OVERWORLD_FOLDER_PATH = getWorldFolderPath("world");
    public static Path NETHER_FOLDER_PATH = getWorldFolderPath("world_nether");
    public static Path THE_END_FOLDER_PATH = getWorldFolderPath("world_the_end");


    public static Path getWorldFolderPath(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if(world != null) {
            return world.getWorldFolder().toPath();
        } else {
            SheetsApi.getPluginLogger().warning("Couldn't find the World Folder of the World '" + worldName + "'");
            return null;
        }
    }

}
