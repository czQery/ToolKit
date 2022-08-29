package cz.qery.toolkit;

import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static String chat (String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static boolean check(String str) {
        if (str != null && !str.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    public static void delay(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
