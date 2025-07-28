package de.Roboter007.sheetsApi.utils;

import de.Roboter007.sheetsApi.SheetsApi;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    public static List<String> readLines(String path) {
        List<String> list = new ArrayList<>();
        try {
            final BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                list.add(line);
            }
            in.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void deleteFolder(File folder, Runnable taskOnDeletion) {
        File[] files = folder.listFiles();
        if(files!=null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f, null);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();

        if(taskOnDeletion != null) {
            taskOnDeletion.run();
        }
    }
}
