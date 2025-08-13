package de.Roboter007.sheets.listener;

import de.Roboter007.sheets.SheetsApi;
import de.Roboter007.sheets.SheetsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record SheetsListener(SheetsPlugin plugin) implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SheetsApi.getLogger().info("Checking for Missing Player Data...");
        plugin.getPlayerDataManager().checkForMissingPlayerData(event.getPlayer().getUniqueId());
    }


}
