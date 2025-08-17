package de.Roboter007.sheets.config.lang;

import de.Roboter007.sheets.SheetsPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Translation {

    public final String lang;
    public HashMap<String, String> translationData;

    public Translation(String lang, FileConfiguration config) {
        this(lang, new HashMap<>());
        loadFromConfig(config);
    }

    public Translation(String lang, HashMap<String, String> translationData) {
        this.lang = lang;
        this.translationData = translationData;
    }

    public String getLang() {
        return lang;
    }

    public HashMap<String, String> getTranslations() {
        return translationData;
    }

    @NotNull
    public HashMap<String, Object> getTranslationData() {
        return new HashMap<>(translationData);
    }


    public void addTranslation(String key, String translation) {
        this.translationData.put(key, translation);
    }

    public String getTranslatedMessage(String key) {
        return translationData.get(key);
    }

    public void loadInConfig(FileConfiguration config) {
        for(String key : translationData.keySet()) {
            config.set(key, translationData.get(key));
        }
    }

    public void loadFromConfig(FileConfiguration config) {
        for (String configKey : config.getKeys(true)) {
            addTranslation(configKey, config.getString(configKey, "§missingTranslation§"));
        }
    }

    public LanguageConfigFile toLangConfigFile(SheetsPlugin plugin) {
        return new LanguageConfigFile(plugin, lang, this);
    }

}
