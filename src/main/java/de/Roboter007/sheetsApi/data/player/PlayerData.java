package de.Roboter007.sheetsApi.data.player;

import de.Roboter007.sheetsApi.SheetsApi;
import de.Roboter007.sheetsApi.data.DataManager;
import de.Roboter007.sheetsApi.utils.JavaUtils;
import de.Roboter007.sheetsApi.utils.PathsUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class PlayerData extends DataManager {

    public final static Path path = JavaUtils.getPathAddition(PathsUtils.PLUGINS_FOLDER_PATH, "/SheetsApi/players/" + SheetsApi.getPlugin().getName());

    public static String STATISTIC_TO_CHECK_KEY = "totemTask.statisticToCheck";
    public static String TOTEM_TASK_NAME_KEY = "totemTask.name";
    public static String TOTEM_TASK_DATA_KEY = "totemTask.data";

    public PlayerData(@NotNull UUID uuid) {
        super(uuid + ".yml", path);
        onEnable();
        PlayerDataHandler.playerConfigs.put(uuid, this);
    }

    public PlayerData(@NotNull UUID uuid, File file) {
        super(uuid + ".yml", file);
        PlayerDataHandler.playerConfigs.put(uuid, this);
    }

    public int getStatisticToCheck() {
        return getConfig().getInt(STATISTIC_TO_CHECK_KEY);
    }

    public void setStatisticToCheck(int statisticToCheck) {
        set(STATISTIC_TO_CHECK_KEY, statisticToCheck);
    }

    @Nullable
    public String getTotemTaskName() {
        return getConfig().getString(TOTEM_TASK_NAME_KEY);
    }

    public void setTotemTaskName(@Nullable String totemTaskName) {
        set(TOTEM_TASK_NAME_KEY, totemTaskName);
    }

    @Nullable
    public String getTotemTaskData() {
        return getConfig().getString(TOTEM_TASK_DATA_KEY);
    }

    public void setTotemTaskData(@Nullable String totemTaskData) {
        set(TOTEM_TASK_DATA_KEY, totemTaskData);
    }


    @Override
    public void onEnable() {
        if(!getConfig().contains(STATISTIC_TO_CHECK_KEY)) {
            setStatisticToCheck(-1);
        }
        if(!getConfig().contains(TOTEM_TASK_NAME_KEY)) {
            setTotemTaskName(null);
        }
        if(!getConfig().contains(TOTEM_TASK_DATA_KEY)) {
            setTotemTaskData(null);
        }
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "config=" + this.getConfig().toString() +
                '}';
    }
}
