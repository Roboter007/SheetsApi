package de.Roboter007.sheetsApi.utils;

import de.Roboter007.sheetsApi.SheetsApi;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JavaUtils {

    @SafeVarargs
    public static <T> ArrayList<T> arrayList(T... objects) {
        return new ArrayList<>(List.of(objects));
    }

    public static <T> ArrayList<T> arrayList(T object) {
        return new ArrayList<>(List.of(object));
    }

    public static Path getPathAddition(Path path, String addition) {
        Path pathWithAddition = Path.of(path.toString() + addition);
        if(!pathWithAddition.toFile().exists()) {
            if(!pathWithAddition.toFile().mkdirs()) {
                SheetsApi.getPluginLogger().severe("Couldn't create path: " + pathWithAddition);
            }
        }
        return pathWithAddition;
    }

    public static String removeYmlFromString(String str) {
        String result = null;
        if (str != null && !str.isEmpty()) {
            result = str.substring(0, str.length() - 4);
        }
        return result;
    }
}
