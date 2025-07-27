package de.Roboter007.sheetsApi.config;

import de.Roboter007.sheetsApi.SheetsApi;
import de.Roboter007.sheetsApi.utils.JavaUtils;
import de.Roboter007.sheetsApi.utils.PathsUtils;

import java.nio.file.Path;

public class LanguageConfig {

    public final static Path path = JavaUtils.getPathAddition(PathsUtils.OVERWORLD_FOLDER_PATH, "/sheetsApi/players/" + SheetsApi.getPlugin().getName());

    public void load() {

    }
}
