package de.Roboter007.sheets.utils;

import de.Roboter007.sheets.SheetsApi;

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

    public static Path getModifiedPath(Path prefix, String suffix) {
        Path path = Path.of(prefix.toString() + suffix);
        if(!path.toFile().exists()) {
            if(!path.toFile().mkdirs()) {
                SheetsApi.getLogger().severe("Couldn't create path: " + path);
            }
        }
        return path;
    }

    public static Path getModifiedPath(String prefix, Path suffix) {
        Path path = Path.of(prefix + suffix.toString());
        if(!path.toFile().exists()) {
            if(!path.toFile().mkdirs()) {
                SheetsApi.getLogger().severe("Couldn't create path: " + path);
            }
        }
        return path;
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
