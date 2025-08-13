package de.Roboter007.sheets.data.player;

import de.Roboter007.sheets.SheetsApi;
import de.Roboter007.sheets.SheetsPlugin;
import de.Roboter007.sheets.utils.JavaUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerDataManager {

    private final SheetsPlugin plugin;
    private final Path path;

    public HashMap<UUID, PlayerDataConfig> playerConfigs = new HashMap<>();

    public PlayerDataManager(SheetsPlugin plugin) {
        this.plugin = plugin;
        this.path = plugin.getPlayerDataFolderPath();
        loadPlayerConfigList();
    }


    @NotNull
    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }

    @NotNull
    public PlayerData getPlayerData(UUID uuid) {
        checkForMissingPlayerData(uuid);
        return playerConfigs.get(uuid).getPlayerData();
    }

    @NotNull
    public PlayerDataConfig getPlayerDataConfig(Player player) {
        return getPlayerDataConfig(player.getUniqueId());
    }

    @NotNull
    public PlayerDataConfig getPlayerDataConfig(UUID player) {
        checkForMissingPlayerData(player);
        return playerConfigs.get(player);
    }

    public void checkForMissingPlayerData(UUID uuid) {
        if(playerConfigExists(uuid)) {
            if(!playerConfigs.containsKey(uuid)) {
                File configFile = getPlayerConfigFile(uuid);
                loadPlayerData(uuid, configFile);
            }
        } else {
            PlayerDataConfig playerDataConfig = new PlayerDataConfig(plugin, uuid);
            playerConfigs.put(uuid, playerDataConfig);
        }
    }

    public boolean playerConfigExists(UUID uuid) {
        File file = path.toFile();
        if (file.exists() && file.isDirectory()) {
            String[] files = file.list();
            if (files != null) {
                for (String content : files) {
                    String realUUID = JavaUtils.removeYmlFromString(content);
                    if (uuid.toString().equals(realUUID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Nullable
    public File getPlayerConfigFile(UUID targetUUID) {
        File file = path.toFile();
        if (file.exists() && file.isDirectory()) {
            String[] files = file.list();
            if (files != null) {
                for (String content : files) {
                    String uuid = JavaUtils.removeYmlFromString(content);
                    if (targetUUID.toString().equals(uuid)) {
                        Path path = Path.of(file.getPath() + "/" + content);
                        return path.toFile();
                    }
                }
            }
        }
        return null;
    }

    @NotNull
    public List<UUID> getPlayerConfigList() {
        List<UUID> uuids = new ArrayList<>();
        File file = path.toFile();
        if (file.exists() && file.isDirectory()) {
            String[] files = file.list();
            if (files != null) {
                for (String content : files) {
                    String uuid = JavaUtils.removeYmlFromString(content);
                    uuids.add(UUID.fromString(uuid));
                }
            }
        }
        return uuids;
    }

    public void loadPlayerConfigList() {
        File file = plugin.getPlayerDataFolderPath().toFile();

        String[] files = file.list();
        if (files != null) {
            for (String fileName : files) {
                UUID uuid = UUID.fromString(JavaUtils.removeYmlFromString(fileName));

                if (!playerConfigs.containsKey(uuid)) {
                    File configFile = Path.of(file.getPath() + "/" + fileName).toFile();

                    loadPlayerData(uuid, configFile);
                }
            }
        } else {
            SheetsApi.getLogger().severe("Path: " + file.getPath() + ", exists?: " + file.exists() + ", isDirectory?: " + file.isDirectory());
        }
    }

    public void loadPlayerData(UUID uuid, File playerConfigFile) {
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerConfigFile);

        PlayerData playerData = new PlayerData(playerConfig);
        PlayerDataConfig playerDataConfig = new PlayerDataConfig(plugin, uuid, playerData);

        playerConfigs.put(uuid, playerDataConfig);
    }


}
