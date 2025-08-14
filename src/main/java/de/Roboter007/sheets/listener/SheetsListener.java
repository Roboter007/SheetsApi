package de.Roboter007.sheets.listener;

import de.Roboter007.sheets.SheetsApi;
import de.Roboter007.sheets.SheetsPlugin;
import de.Roboter007.sheets.data.player.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public record SheetsListener(@NotNull SheetsPlugin plugin) implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SheetsApi.getLogger().info("Checking for Missing Player Data for plugin '" + plugin.getName() + "'...");

        PlayerDataManager playerDataManager = plugin.getPlayerDataManager();
        playerDataManager.checkForMissingPlayerData(event.getPlayer().getUniqueId());
    }
}
