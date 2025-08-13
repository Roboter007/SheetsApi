package de.Roboter007.sheets.listener;

import de.Roboter007.sheets.SheetsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record SheetsListener(SheetsPlugin plugin) implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

    }


}
