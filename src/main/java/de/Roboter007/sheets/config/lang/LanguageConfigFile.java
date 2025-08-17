package de.Roboter007.sheets.config.lang;

import de.Roboter007.sheets.SheetsPlugin;
import de.Roboter007.sheets.data.SheetsConfigFile;
import de.Roboter007.sheets.utils.JavaUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class LanguageConfigFile extends SheetsConfigFile {

    private final String lang;
    private Translation translation;
    private final SheetsPlugin plugin;

    public LanguageConfigFile(SheetsPlugin plugin, String lang, @NotNull Translation defaultTranslation) {
        super(new File(plugin.getLangConfigFolder().toString() + lang + ".yml"), defaultTranslation.getTranslationData());
        this.lang = lang;
        this.plugin = plugin;
        this.translation = defaultTranslation;
    }

    public LanguageConfigFile(SheetsPlugin plugin, File file) {
        super(file);
        this.lang = JavaUtils.removeYmlFromString(file.getName());
        this.plugin = plugin;
        this.translation = new Translation(lang, this.config);
    }

    public Translation getTranslation() {
        return translation;
    }

    public String getLang() {
        return lang;
    }

    public void reset() {
        translation = plugin.getLanguageManager().getTranslation(lang);
        translation.loadInConfig(this.getConfig());
    }
}
