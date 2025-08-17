package de.Roboter007.sheets;

import de.Roboter007.sheets.api.commands.SheetsCommands;
import de.Roboter007.sheets.config.lang.LanguageManager;
import de.Roboter007.sheets.config.lang.Translation;
import de.Roboter007.sheets.config.player.PlayerData;
import de.Roboter007.sheets.config.player.PlayerDataManager;
import de.Roboter007.sheets.api.listeners.SheetsListeners;
import de.Roboter007.sheets.listener.SheetsListener;
import de.Roboter007.sheets.utils.HtmlColors;
import de.Roboter007.sheets.utils.JavaUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class SheetsPlugin extends JavaPlugin {

    public static final String LANG_CONFIG_KEY = "lang";

    private LanguageManager languageManager = null;
    private PlayerDataManager playerDataManager = null;

    public SheetsPlugin() {

    }

    // Api settings

    public abstract PlayerData getDefaultPlayerData();

    public abstract List<Translation> getDefaultTranslations();


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
        return JavaUtils.getModifiedPath(getPluginFolderPath(), "/players/", JavaUtils.PathType.DIRECTORY);
    }

    public File uuidToPlayerDataFile(@NotNull UUID uuid) {
        return JavaUtils.getModifiedPath(getPlayerDataFolderPath(), "/" + uuid + ".yml", JavaUtils.PathType.FILE).toFile();
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
        ArrayList<String> comments = new ArrayList<>(config().getComments(key));
        comments.add(message);
        config().setInlineComments(key, comments);
    }


    public void loadSheetConfigOptions() {
        addComment(LANG_CONFIG_KEY, "Translations can be found under the plugins/SheetsApi/lang");
        setConfigValue(LANG_CONFIG_KEY, "en_us");
    }

    // manage Lang Config


    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public String getPluginLang() {
        return getConfigValue(LANG_CONFIG_KEY, "en_us");
    }

    public Path getLangConfigFolder() {
        return JavaUtils.getModifiedPath(getPluginFolderPath(), "/lang/", JavaUtils.PathType.DIRECTORY);
    }


    // Hook into onLoad/onEnable/onDisable

    @Override
    public void onLoad() {
        Bukkit.getServer().sendMessage(Component.text(this.getName() + " is using SheetsApi").color(HtmlColors.yellow.getTextColor()));
        SheetsApi.addPluginToApi(this);
    }

    @Override
    public void onEnable() {
        // Listener & Command Registry
        SheetsListeners.registerListener(this, new SheetsListener(this));

        SheetsListeners.registerAllListeners(this);
        SheetsCommands.registerAllCommands(this);

        // Player Data Manager
        playerDataManager = new PlayerDataManager(this);

        // Load needed Sheet Config Options into Plugin Config
        loadSheetConfigOptions();

        // Load lang Config
        this.languageManager = new LanguageManager(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
