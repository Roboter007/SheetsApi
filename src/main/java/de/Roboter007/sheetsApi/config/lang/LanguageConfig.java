package de.Roboter007.sheetsApi.config.lang;

import de.Roboter007.sheetsApi.config.SheetsConfig;
import de.Roboter007.sheetsApi.utils.JavaUtils;
import de.Roboter007.sheetsApi.utils.PathsUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public abstract class LanguageConfig {
    public static Path LANG_FOLDER_PATH = JavaUtils.getPathAddition(PathsUtils.PLUGINS_FOLDER_PATH, "/SheetsApi/lang/");

    private final JavaPlugin plugin;
    private final Path path;
    public static HashMap<String, HashMap<String, String>> LANGUAGE_CONFIG_MAP = new HashMap<>();

    public LanguageConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.path = JavaUtils.getPathAddition(LANG_FOLDER_PATH, plugin.getName());
    }

    public void load() {
        File[] langFiles = path.toFile().listFiles();

        if(langFiles != null) {
            for (File file : langFiles) {

                if(file.exists()) {
                    String language = file.getName();
                    HashMap<String, String> translationMap = new HashMap<>();

                    YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(file);

                    for(String langKey : langConfig.getKeys(false)) {
                        String message = langConfig.getString(langKey);

                        if(message != null) {
                            translationMap.put(langKey, message);
                        }
                    }
                    LANGUAGE_CONFIG_MAP.put(language, translationMap);
                }
            }
        } else {
            plugin.getLogger().warning("No Lang File found! Creating the defaults");
            createDefaultLangs();
            load();
        }
    }

    @NotNull
    public abstract HashMap<String, Object> getEnTranslations();

    @NotNull
    public abstract HashMap<String, Object> getDeTranslations();

    public void createDefaultLangs() {
        createLangConfig("en_us", getEnTranslations());
        createLangConfig("de_de", getDeTranslations());
    }

    public void createLangConfig(String lang, HashMap<String, Object> translations) {
        try {
            File langFile = new File(path.toString() + lang + ".yml");
            YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
            langConfig.addDefaults(translations);

            langConfig.save(langFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageWithPlaceholder(Player player, String messageKey, LangPlaceholderTypes langPlaceholderType, String replacement) {
        player.sendMessage(Component.text(getMessageWithPlaceholder(messageKey, langPlaceholderType, replacement)));
    }

    public void sendMessage(Player player, String messageKey) {
        player.sendMessage(Component.text(getMessage(messageKey)));
    }


    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String getMessageWithPlaceholder(String messageKey, LangPlaceholderTypes langPlaceholderType, String replacement) {
        String message = getMessage(messageKey);
        if(message.contains(langPlaceholderType.getPlaceholder())) {
            message = message.replace(langPlaceholderType.getPlaceholder(), replacement);
        }
        return message;
    }

    public String getMessage(String messageKey) {
        return getMessageInLang(messageKey, SheetsConfig.getValue(SheetsConfig.LANG_CONFIG_KEY, "en_us"));
    }

    public String getMessageInLang(String messageKey, String lang) {
        HashMap<String, String> translationMap = LANGUAGE_CONFIG_MAP.get(lang);
        if(translationMap != null) {
            return translationMap.get(messageKey);
        } else {
            return "$missing-translation:" + messageKey + "$";
        }
    }
}
