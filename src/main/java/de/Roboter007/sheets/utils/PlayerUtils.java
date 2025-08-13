package de.Roboter007.sheets.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerUtils {

    public static List<String> getAllPlayerNames() {
        List<String> names = new ArrayList<>();
        for(OfflinePlayer player : Bukkit.getOnlinePlayers()) {
            names.add(player.getName());
        }
        return names;
    }

    public static List<String> getOnlinePlayerNames() {
        List<String> names = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            names.add(player.getName());
        }
        return names;
    }

    public static List<Player> getOnlinePlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    public static boolean isPlayerOnline(String name) {
        for(Player player : getOnlinePlayers()) {
            if(player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPlayerOnline(UUID uuid) {
        for(Player player : getOnlinePlayers()) {
            if(player.getUniqueId().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<Advancement, AdvancementProgress> getAdvancementProgress(Player player) {
        HashMap<Advancement, AdvancementProgress> advancementProgressHashMap = new HashMap<>();
        for (@NotNull Iterator<Advancement> it = Bukkit.advancementIterator(); it.hasNext(); ) {
            Advancement advancement = it.next();
            AdvancementProgress advancementProgress = player.getAdvancementProgress(advancement);
            advancementProgressHashMap.put(advancement, advancementProgress);
        }
        return advancementProgressHashMap;
    }



}
