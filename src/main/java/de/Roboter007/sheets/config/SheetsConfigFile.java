package de.Roboter007.sheets.config;

import de.Roboter007.sheets.SheetsApi;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public abstract class SheetsConfigFile {

    protected FileConfiguration config;
    protected final File file;

    protected final HashMap<String, Object> defaultConfigMap;

    public SheetsConfigFile(File file, @Nullable HashMap<String, Object> defaultConfigMap) {
        this.file = file;
        this.defaultConfigMap = defaultConfigMap;
        this.config = load();
    }

    public SheetsConfigFile(File file) {
        this(file, null);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void createFile() {
        try {
            if (!file.exists()) {
                if(!file.createNewFile()) {
                    SheetsApi.getLogger().severe("Failed to create Sheets Config File!");
                }
            }
        } catch (IOException e) {
            SheetsApi.getLogger().severe("Failed to create Sheets Config File!");
        }
    }

    public FileConfiguration load() {
        createFile();

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(defaultConfigMap != null && !defaultConfigMap.isEmpty()) {
            config.addDefaults(defaultConfigMap);
        }
        return config;
    }

    public void reload() {
        this.config = load();
    }

    @Nullable
    public HashMap<String, Object> getDefaults() {
        return defaultConfigMap;
    }


    public void save() {
        if(this.config == null || this.file == null) {
            SheetsApi.getLogger().severe("Failed to save Config File");
            SheetsApi.getLogger().severe("File: " + this.file + " Config: " + this.config);
            return;
        }
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            SheetsApi.getLogger().severe("Couldn't save Config File");
        }
    }

    public <D> void set(@NotNull String key, @Nullable D data) {
        config.set(key, data);
        save();
    }

    private void addComment(String key, String message) {
        List<String> comments = config.getComments(key);
        comments.add(message);
        config.setInlineComments(key, comments);
    }

    public Object get(@NotNull String configKey) {
        return config.get(configKey);
    }

    public String getString(@NotNull String configKey) {
        return config.getString(configKey);
    }

    public Boolean getBoolean(@NotNull String configKey) {
        return config.getBoolean(configKey);
    }

    public Integer getInt(@NotNull String configKey) {
        return config.getInt(configKey);
    }

    public Double getDouble(@NotNull String configKey) {
        return config.getDouble(configKey);
    }

    public Long getLong(@NotNull String configKey) {
        return config.getLong(configKey);
    }

}
