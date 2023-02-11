package cz.qery.toolkit;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;

public final class Vnsh {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String h = plugin.getConfig().getString("color.highlight");
    static String t = plugin.getConfig().getString("color.text");

    public static ArrayList<String> players = new ArrayList<String>();

    @SuppressWarnings("deprecation")
    public static void Show(Player p, boolean init) {
        for (Player pl : plugin.getServer().getOnlinePlayers()) {
            if (pl == p || pl.hasPermission("toolkit.vanish") || Enabled(pl)) {
                pl.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Player "+h+p.getName()+t+" has &cexited"+t+" vanish mode!"));

            }
            if (pl == p || Enabled(pl)) {
                continue;
            }
            pl.showPlayer(plugin, p);
        }
        p.setSleepingIgnored(false);
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
            p.setAllowFlight(false);
        }
        if (init) {
            Vnsh.players.remove(p.getName());
            if (Tools.isPaper) {
                plugin.getServer().sendMessage(Component.text(Tools.chat(plugin.getConfig().getString("join.message")).replace("%player%",p.getName())));
            } else {
                plugin.getServer().broadcastMessage(Tools.chat(plugin.getConfig().getString("join.message")).replace("%player%",p.getName()));
            }
        }

        for (String pl : players) {
            Player target = Bukkit.getServer().getPlayer(pl);
            if (target != null) {
                p.hidePlayer(plugin, target);
            }
        }

        p.setMetadata("vanished", new FixedMetadataValue(plugin, false));
    }

    @SuppressWarnings("deprecation")
    public static void Hide(Player p, boolean init) {
        for (Player pl : plugin.getServer().getOnlinePlayers()) {
            if (pl == p || pl.hasPermission("toolkit.vanish") || Enabled(pl)) {
                pl.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Player "+h+p.getName()+t+" has &aentered"+t+" vanish mode!"));
            }
            if (pl == p || Enabled(pl)) {
                continue;
            }
            pl.hidePlayer(plugin, p);
        }
        if (init) {
            Vnsh.players.add(p.getName());
            if (Tools.isPaper) {
                plugin.getServer().sendMessage(Component.text(Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%",p.getName())));
            } else {
                plugin.getServer().broadcastMessage(Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%",p.getName()));
            }
        }
        p.setSleepingIgnored(true);
        p.setAllowFlight(true);
        for(Entity e : p.getWorld().getEntities()) {
            if (e instanceof Creature && ((Creature) e).getTarget() != null && ((Creature) e).getTarget().getName() == p.getName()) {
                ((Creature) e).setTarget(null);
            }
        }

        for (String pl : players) {
            Player target = Bukkit.getServer().getPlayer(pl);
            if (target != null) {
                p.showPlayer(plugin, target);
            }
        }

        p.setMetadata("vanished", new FixedMetadataValue(plugin, true));
    }

    public static boolean Enabled(Player p) {
        try {
            return players.contains(p.getName());
        } catch (Exception e) {
            return false;
        }
    }
}
