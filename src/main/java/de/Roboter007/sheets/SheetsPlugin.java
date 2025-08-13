package de.Roboter007.sheets;

import de.Roboter007.sheets.api.commands.SheetsCommands;
import de.Roboter007.sheets.config.lang.LanguageConfig;
import de.Roboter007.sheets.data.player.PlayerData;
import de.Roboter007.sheets.data.player.PlayerDataConfig;
import de.Roboter007.sheets.data.player.PlayerDataManager;
import de.Roboter007.sheets.api.listeners.SheetsListeners;
import de.Roboter007.sheets.listener.SheetsListener;
import de.Roboter007.sheets.utils.JavaUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public abstract class SheetsPlugin extends JavaPlugin {

    public static final String LANG_CONFIG_KEY = "lang";


    private PlayerDataManager playerDataManager = null;

    public SheetsPlugin() {

    }

    // Api settings

    public abstract LanguageConfig langConfig();

    public abstract PlayerData getDefault();


    // Shortcuts

    public Path getPluginFolderPath() {
        return this.getDataPath();
    }

    public FileConfiguration config() {
        return this.getConfig();
    }

    // manage Player Data

    @NotNull
    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public Path getPlayerDataFolderPath() {
        return JavaUtils.getModifiedPath(getPluginFolderPath(), "/players/");
    }

    public File uuidToPlayerDataFile(@NotNull UUID uuid) {
        return JavaUtils.getModifiedPath(getPlayerDataFolderPath(), uuid + ".yml").toFile();
    }

    // manage Plugin Config

    @NotNull
    @SuppressWarnings("unchecked")
    public <T> T getConfigValue(String path, T defaultData) {
        T t = (T) config().get(path);
        if(t != null) {
            return t;
        } else {
            return defaultData;
        }
    }

    public <D> void setConfigValue(@NotNull String key, @Nullable D data) {
        config().set(key, data);
        save();
    }

    public void save() {
        this.saveConfig();
    }

    private void addComment(String key, String message) {
        List<String> comments = config().getComments(key);
        comments.add(message);
        config().setInlineComments(key, comments);
    }


    public void loadDefaultSheetConfig() {
        addComment(LANG_CONFIG_KEY, "Translations can be found under the plugins/SheetsApi/lang");
        setConfigValue(LANG_CONFIG_KEY, "en_us");
    }

    // Hook into onLoad/onEnable/onDisable

    @Override
    public void onLoad() {
        SheetsApi.addPluginToApi(this);

        SheetsListeners.registerListener(this, new SheetsListener(this));

        SheetsCommands.registerAllCommands(this);
        SheetsListeners.registerAllListeners(this);

        playerDataManager = new PlayerDataManager(this);

        loadDefaultSheetConfig();

        langConfig().load();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
