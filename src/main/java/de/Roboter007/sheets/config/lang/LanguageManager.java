package de.Roboter007.sheets.config.lang;

import de.Roboter007.sheets.SheetsPlugin;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageManager {

    private final SheetsPlugin plugin;

    private final Path path;
    private final List<LanguageConfigFile> langConfigFiles = new ArrayList<>();

    public LanguageManager(SheetsPlugin plugin) {
        this.plugin = plugin;
        this.path = plugin.getLangConfigFolder();
        loadFromFolder();
    }

    public void loadFromFolder() {
        File[] langFiles = path.toFile().listFiles();

        if(langFiles != null && !Arrays.stream(langFiles).toList().isEmpty()) {
            for (File file : langFiles) {
                if(file.exists()) {
                    langConfigFiles.add(new LanguageConfigFile(plugin, file));
                }
            }
        } else {
            plugin.getLogger().warning("No Lang File found! Creating the defaults");
            reset();
            loadFromFolder();
        }
    }

    public void reload() {
        langConfigFiles.clear();
        loadFromFolder();
    }

    public void reset() {
        for(Translation translation : plugin.getDefaultTranslations()) {
            LanguageConfigFile languageConfigFile = translation.toLangConfigFile(plugin);
            langConfigFiles.add(languageConfigFile);
        }
    }

    public Translation getTranslation(String lang) {
        for(LanguageConfigFile langConfig : langConfigFiles) {
            if(langConfig.getLang().equals(lang)) {
                return langConfig.getTranslation();
            }
        }
        return null;
    }

    public LanguageConfigFile getLangConfigFile(String lang) {
        for(LanguageConfigFile langConfig : langConfigFiles) {
            if(langConfig.getLang().equals(lang)) {
                return langConfig;
            }
        }
        return null;
    }

    public String getMessageInLang(String messageKey, String lang) {
        Translation translation = getTranslation(lang);
        if(translation != null) {
            return translation.getTranslatedMessage(messageKey);
        } else {
            return "$missing-translation:" + messageKey + "$";
        }
    }

    public String getMessage(String messageKey) {
        return getMessageInLang(messageKey, plugin.getPluginLang());
    }
}
