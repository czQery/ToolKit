package cz.qery.toolkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.dynmap.DynmapCommonAPI;

public class Tools {
    public static boolean isPaper = false;

    public static DynmapCommonAPI DynApi;

    @SuppressWarnings("deprecation")
    public static String chat (String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(chat(message));
    }

    public static void paperCheck() {
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            isPaper = true;
        } catch (ClassNotFoundException ignored) {
            isPaper = false;
        }
    }
}
