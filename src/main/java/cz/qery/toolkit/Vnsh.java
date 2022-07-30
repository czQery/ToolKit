package cz.qery.toolkit;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class Vnsh {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String h = plugin.getConfig().getString("color.highlight");
    static String t = plugin.getConfig().getString("color.text");

    public static ArrayList<String> players = new ArrayList<String>();


    public static void Show(Player p, boolean init) {
        for (Player pl : plugin.getServer().getOnlinePlayers()) {
            if (pl == p || pl.hasPermission("toolkit.vanish")) {
                pl.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Player "+h+p.getName()+t+" has &cexited"+t+" vanish mode!"));
                continue;
            } else {
                pl.showPlayer(plugin, p);
            }
        }
        if (init) {
            Vnsh.players.remove(p.getName());
            plugin.getServer().sendMessage(Component.text(Tools.chat(plugin.getConfig().getString("join.message")).replace("%player%",p.getName())));
        }
    }

    public static void Hide(Player p, boolean init) {
        for (Player pl : plugin.getServer().getOnlinePlayers()) {
            if (pl == p || pl.hasPermission("toolkit.vanish")) {
                pl.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Player "+h+p.getName()+t+" has &aentered"+t+" vanish mode!"));
                continue;
            } else {
                pl.hidePlayer(plugin, p);
            }
        }
        if (init) {
            Vnsh.players.add(p.getName());
            plugin.getServer().sendMessage(Component.text(Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%",p.getName())));
        }
    }

    public static boolean Enabled(Player p) {
        try {
            return players.contains(p.getName());
        } catch (Exception e) {
            return false;
        }
    }
}
