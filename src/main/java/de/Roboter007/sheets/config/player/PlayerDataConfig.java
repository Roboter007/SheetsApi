package de.Roboter007.sheets.config.player;

import de.Roboter007.sheets.SheetsPlugin;
import de.Roboter007.sheets.config.SheetsConfigFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerDataConfig extends SheetsConfigFile {

    private final UUID uuid;
    private PlayerData playerData;
    private final SheetsPlugin plugin;

    public PlayerDataConfig(@NotNull SheetsPlugin plugin, @NotNull UUID uuid, @Nullable PlayerData playerData) {
        super(plugin.uuidToPlayerDataFile(uuid), plugin.getDefaultPlayerData().getPlayerData());
        this.plugin = plugin;
        this.uuid = uuid;
        this.playerData = playerData;

        if(playerData != null) {
            playerData.loadInConfig(this.config);
        } else {
            reset();
        }
        save();
    }

    public PlayerDataConfig(@NotNull SheetsPlugin plugin, @NotNull UUID uuid) {
        this(plugin, uuid, null);
    }

    public UUID getUUID() {
        return uuid;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        playerData.loadInConfig(this.getConfig());
    }

    @Override
    public <D> void set(@NotNull String key, @Nullable D data) {
        super.set(key, data);
        this.playerData.setData(key, data);
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "config=" + this.getConfig().toString() +
                '}';
    }

    public void reset() {
        playerData = plugin.getDefaultPlayerData();
        playerData.loadInConfig(this.getConfig());
    }
}

