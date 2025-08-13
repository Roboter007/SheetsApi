package de.Roboter007.sheets.data.player;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class PlayerData {

    public HashMap<String, Object> playerData;

    public PlayerData(FileConfiguration config) {
        this(new HashMap<>());
        loadFromConfig(config);
    }

    public PlayerData(HashMap<String, Object> playerData) {
        this.playerData = playerData;
    }

    public HashMap<String, Object> getPlayerData() {
        return playerData;
    }

    public void setData(String key, Object object) {
        this.playerData.put(key, object);
    }

    public Object getData(String key) {
        return playerData.get(key);
    }

    public void loadInConfig(FileConfiguration config) {
        for(String key : playerData.keySet()) {
            config.set(key, playerData.get(key));
        }
    }

    public void loadFromConfig(FileConfiguration config) {
        for (String configKey : config.getKeys(true)) {
            setData(configKey, config.get(configKey));
        }
    }

}
