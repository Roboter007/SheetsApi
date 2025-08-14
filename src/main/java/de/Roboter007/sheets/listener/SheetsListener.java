package de.Roboter007.sheets.listener;

import de.Roboter007.sheets.SheetsApi;
import de.Roboter007.sheets.SheetsPlugin;
import de.Roboter007.sheets.events.PlayerDataCheckEvent;
import de.Roboter007.sheets.utils.JavaUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

//ToDo: better solution this doesn't work
public abstract class SheetsListener implements Listener {

    public SheetsListener() {}

    public abstract SheetsPlugin getPlugin();

    @EventHandler
    public void onPlayerJoin(PlayerDataCheckEvent event) {
        SheetsApi.getLogger().info("Checking for Missing Player Data...");
        this.getPlugin().getPlayerDataManager().checkForMissingPlayerData(event.getPlayer().getUniqueId());
    }


}
