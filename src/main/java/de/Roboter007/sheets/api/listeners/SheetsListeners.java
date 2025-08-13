package de.Roboter007.sheets.api.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class SheetsListeners {

    public static void registerAllListeners(JavaPlugin plugin) {
        plugin.getLogger().info("Loading Listeners for " + plugin.getName());

        String packageName = plugin.getClass().getPackage().getName();
        String targetLocation = packageName + ".listeners";

        for (Class<?> clazz : new Reflections(targetLocation).getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                registerListener(plugin, listener);
                plugin.getLogger().info("Registered Listener: " + clazz.getName());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                plugin.getLogger().severe(e.fillInStackTrace().toString());
            }
        }
    }

    public static void registerListener(JavaPlugin plugin, Listener listener) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listener, plugin);
    }
}
