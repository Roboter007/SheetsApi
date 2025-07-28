package de.Roboter007.sheetsApi.config;

import de.Roboter007.sheetsApi.SheetsApi;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SheetsConfig implements ISheetConfig {

    private static final FileConfiguration configuration = SheetsApi.getPlugin().getConfig();

    public static String LANG_CONFIG_KEY = "lang";

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

    public void saveConfig() {
        SheetsApi.getPlugin().saveConfig();
    }

    public void setValue(String path, Object object) {
        configuration.set(path, object);
    }

    private void addComment(String key, String message) {
        List<String> comments = configuration.getComments(key);
        comments.add(message);
        configuration.setInlineComments(key, comments);
    }

    public void loadConfig() {
        loadDefaultConfig();
    }

    public void loadDefaultConfig() {
        addComment(LANG_CONFIG_KEY, "Translations can be found under the plugins/SheetsApi/lang");
        setValue(LANG_CONFIG_KEY, "en_us");

        configuration.setDefaults(configuration);
    }
}
