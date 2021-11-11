package cz.qery.toolkit;

import com.lunarclient.bukkitapi.nethandler.LCPacket;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Tools {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String t = plugin.getConfig().getString("color.text");

    public static String chat (String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static boolean check(String str) {
        if (str != null && !str.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    public static TextComponent schat(String input){
        return new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', input));
    }
    public static void delay(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(chat(message));
    }

    public static boolean sendLunarPacket(Player player, LCPacket packet) {
        if (player.getMetadata("client").toString() != "[]") {
            if (player.getMetadata("client").get(0).asString() == "LunarClient") {
                player.sendPluginMessage(plugin, "lunarclient:pm", LCPacket.getPacketData(packet));
                return true;
            }
        }
        return false;
    }
}
