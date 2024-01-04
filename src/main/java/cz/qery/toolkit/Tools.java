package cz.qery.toolkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.dynmap.DynmapCommonAPI;

public class Tools {
    public static DynmapCommonAPI DynApi;

    @SuppressWarnings("deprecation")
    public static String chat (String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(chat(message));
    }
}
