package de.Roboter007.sheetsApi.data;

import de.Roboter007.sheetsApi.SheetsApi;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

public abstract class DataManager {

    @NotNull
    private final String name;
    @NotNull
    public File path = SheetsApi.getPlugin().getDataFolder();
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public DataManager(@NotNull String name) {
        this.name = name;
    }
    public DataManager(@NotNull String name, Path path) {
        this.name = name;
        this.path = path.toFile();
    }

    public DataManager(@NotNull String name, @NotNull File file) {
        this.name = name;
        this.path = file;
        this.reloadConfig(file);
    }

    public <D> void set(@NotNull String configKey, @Nullable D data) {
        getConfig().set(configKey, data);
        saveConfig();
    }

    public void reloadConfig() {
        reloadConfig(null);
    }

    public void reloadConfig(@Nullable File file) {
        if(file == null) {
            if (this.configFile == null) {
                this.configFile = new File(path, name);
            }
        } else {
            this.configFile = file;
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = SheetsApi.getPlugin().getResource(name);
        if(defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if(this.dataConfig == null) {
            reloadConfig();
        }
        return dataConfig;
    }

    public File getLocation() {
        return path;
    }

    public @NotNull String getName() {
        return name;
    }

    public void saveConfig() {
        if(this.dataConfig == null || this.configFile == null) {
            SheetsApi.getPluginLogger().severe("Failed to save Config File");
            SheetsApi.getPluginLogger().severe("DataConfig: " + this.dataConfig + " ConfigFile: " + this.configFile);
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            SheetsApi.getPluginLogger().severe("Couldn't save Config File");
        }
    }

    public void saveDefaultConfig() {
        if(this.configFile == null) {
            this.configFile = new File(path, name);
        }
        if(!this.configFile.exists()) {
            SheetsApi.getPlugin().saveResource(name, false);
        }
    }

    public abstract void onEnable();


}
