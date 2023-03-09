package cz.qery.toolkit;

import com.lunarclient.bukkitapi.nethandler.LCPacket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dynmap.DynmapCommonAPI;

public class Tools {
    static Main plugin = Main.getPlugin(Main.class);
    public static boolean isPaper = false;

    public static DynmapCommonAPI DynApi;

    public static String chat (String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(chat(message));
    }

    public static void sendLunarPacket(Player player, LCPacket packet) {
        if (!player.getMetadata("client").toString().equals("[]")) {
            if (player.getMetadata("client").get(0).asString().equals("LunarClient")) {
                player.sendPluginMessage(plugin, "lunarclient:pm", LCPacket.getPacketData(packet));
            }
        }
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
