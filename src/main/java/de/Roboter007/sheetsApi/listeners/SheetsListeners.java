package de.Roboter007.sheetsApi.listeners;

import de.Roboter007.sheetsApi.SheetsApi;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class SheetsListeners {

    public static void registerAllListeners() {
        JavaPlugin plugin = SheetsApi.getPlugin();
        plugin.getLogger().info("Loading Listeners for " + plugin.getName());

        String packageName = plugin.getClass().getPackage().getName();
        String targetLocation = packageName + ".listeners";
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        for (Class<?> clazz : new Reflections(targetLocation).getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                pluginManager.registerEvents(listener, plugin);
                plugin.getLogger().info("Registered Listener: " + clazz.getName());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                plugin.getLogger().severe(e.fillInStackTrace().toString());
            }
        }
    }
}
