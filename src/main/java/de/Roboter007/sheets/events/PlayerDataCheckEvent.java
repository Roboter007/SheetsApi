package de.Roboter007.sheets.events;

import de.Roboter007.sheets.SheetsPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerDataCheckEvent extends PlayerJoinEvent {

    @NotNull
    public final SheetsPlugin plugin;

    // I don't care about this warning (;
    @SuppressWarnings("all")
    public PlayerDataCheckEvent(@NotNull SheetsPlugin plugin, @NotNull Player playerJoined, @Nullable Component joinMessage) {
        super(playerJoined, joinMessage);
        this.plugin = plugin;
    }

    @NotNull
    public SheetsPlugin getPlugin() {
        return plugin;
    }
}
