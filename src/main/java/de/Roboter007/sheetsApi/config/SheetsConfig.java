package de.Roboter007.sheetsApi.config;

import de.Roboter007.sheetsApi.SheetsApi;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class SheetsConfig {

    public static FileConfiguration configuration = SheetsApi.getPlugin().getConfig();


    public static void saveConfig() {
        SheetsApi.getPlugin().saveConfig();
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> T getValue(String path, T defaultData) {
        T t = (T) configuration.get(path);
        if(t != null) {
            return t;
        } else {
            return defaultData;
        }
    }

    public void setValue(String path, Object object) {
        configuration.set(path, object);
    }

    public void loadDefaultConfig() {
        setValue("lang", "en_us");

    }
}
