package cz.qery.toolkit;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static String chat (String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static TextComponent schat(String input){
        return new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', input));
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
