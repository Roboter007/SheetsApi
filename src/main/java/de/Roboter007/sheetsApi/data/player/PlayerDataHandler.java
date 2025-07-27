package de.Roboter007.sheetsApi.data.player;

import de.Roboter007.sheetsApi.SheetsApi;
import de.Roboter007.sheetsApi.utils.JavaUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerDataHandler {

    public static HashMap<UUID, PlayerData> playerConfigs = new HashMap<>();

    public static void createNewDataConfigIfMissing(Player player) {
        if(!playerConfigExists(player.getUniqueId())) {
            new PlayerData(player.getUniqueId());
        } else if (playerConfigs.containsKey(player.getUniqueId())) {
            File file = getPlayerConfigFile(player.getUniqueId());
            if(file != null) {
                new PlayerData(player.getUniqueId(), file);
            } else {
                SheetsApi.getPluginLogger().severe("Why is the file null?");
            }
        }
    }

    @NotNull
    public static PlayerData getDataFromPlayer(Player player) {
        if(!playerConfigs.containsKey(player.getUniqueId())) {
            return new PlayerData(player.getUniqueId());
        } else {
            return playerConfigs.get(player.getUniqueId());
        }
    }

    @NotNull
    public static PlayerData getDataFromPlayer(UUID player) {
        if(!playerConfigs.containsKey(player)) {
            return new PlayerData(player);
        } else {
            return playerConfigs.get(player);
        }
    }

    public static boolean playerConfigExists(UUID uuid) {
        File file = PlayerData.path.toFile();
        if(file.exists() && file.isDirectory()) {
            String[] files = file.list();
            if(files != null) {
                for(String content : files) {
                    String realUUID = JavaUtils.removeYmlFromString(content);
                    if(uuid.toString().equals(realUUID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Nullable
    public static File getPlayerConfigFile(UUID uuid) {
        File file = PlayerData.path.toFile();
        if(file.exists() && file.isDirectory()) {
            String[] files = file.list();
            if(files != null) {
                for(String content : files) {
                    String realUUID = JavaUtils.removeYmlFromString(content);
                    if(uuid.toString().equals(realUUID)) {
                        Path path = Path.of(file.getPath() + "/" + content);
                        return path.toFile();
                    }
                }
            }
        }
        return null;
    }

    @NotNull
    public static List<UUID> getPlayerConfigList() {
        List<UUID> uuids = new ArrayList<>();
        File file = PlayerData.path.toFile();
        if(file.exists() && file.isDirectory()) {
            String[] files = file.list();
            if(files != null) {
                for(String content : files) {
                    String realUUID = JavaUtils.removeYmlFromString(content);
                    uuids.add(UUID.fromString(realUUID));
                }
            }
        }
        return uuids;
    }

    public static void loadPlayerConfigList() {
        JavaPlugin plugin = SheetsApi.getPlugin();

        File file = PlayerData.path.toFile();
        if(!file.exists()) {
            if (!file.mkdirs()) {
                plugin.getLogger().severe("Why couldn't the directories be created?");
            }
        }
        if(file.isDirectory()) {
            String[] files = file.list();
            if(files != null) {
                for(String content : files) {
                    plugin.getLogger().info(content);
                    String realUUID = JavaUtils.removeYmlFromString(content);
                    plugin.getLogger().info(realUUID);
                    UUID uuid = UUID.fromString(realUUID);
                    if(!playerConfigs.containsKey(uuid)) {
                        Path path = Path.of(file.getPath() + "/" + content);
                        plugin.getLogger().info(path.toString());
                        File inFolder = path.toFile();
                        PlayerData playerData = new PlayerData(uuid, inFolder);
                        playerConfigs.put(uuid, playerData);
                    }
                }
            }
        } else {
            plugin.getLogger().severe("Path: " + file.getPath() + ", exists?: " + file.exists() + ", isDirectory?: " + file.isDirectory());
        }
    }
}
