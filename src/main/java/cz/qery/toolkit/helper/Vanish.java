package cz.qery.toolkit.helper;

import cz.qery.toolkit.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Vanish {
    public static HashMap<UUID, String> players = new HashMap<>();
    static Main plugin = Main.getPlugin(Main.class);
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");
    static String h = Main.colors.get("h");
    static String t = Main.colors.get("t");

    public static void Show(Player p, boolean init) {
        for (Player pl : plugin.getServer().getOnlinePlayers()) {
            if (pl == p || pl.hasPermission("toolkit.vanish") || Enabled(pl)) {
                pl.sendMessage(Other.Tools.chat(b + "[" + n + "VANISH" + b + "]" + t + " Player " + h + p.getName() + t + " has &cexited" + t + " vanish mode!"));

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
            Vanish.players.remove(p.getUniqueId());
            plugin.getServer().sendMessage(Component.text(Other.Tools.chat(plugin.getConfig().getString("join.message")).replace("%player%", p.getName())));
        }

        for (Map.Entry<UUID, String> pl : players.entrySet()) {
            Player target = Bukkit.getServer().getPlayer(pl.getKey());
            if (target != null) {
                p.hidePlayer(plugin, target);
            }
        }

        p.setMetadata("vanished", new FixedMetadataValue(plugin, false));
    }

    public static void Hide(Player p, boolean init) {
        for (Player pl : plugin.getServer().getOnlinePlayers()) {
            if (pl == p || pl.hasPermission("toolkit.vanish") || Enabled(pl)) {
                pl.sendMessage(Other.Tools.chat(b + "[" + n + "VANISH" + b + "]" + t + " Player " + h + p.getName() + t + " has &aentered" + t + " vanish mode!"));
            }
            if (pl == p || Enabled(pl)) {
                continue;
            }
            pl.hidePlayer(plugin, p);
        }
        if (init) {
            Vanish.players.put(p.getUniqueId(), p.getName());
            plugin.getServer().sendMessage(Component.text(Other.Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%", p.getName())));
        }
        p.setSleepingIgnored(true);
        p.setAllowFlight(true);
        for (Entity e : p.getWorld().getEntities()) {
            if (e instanceof Creature && ((Creature) e).getTarget() != null && ((Creature) e).getTarget().getUniqueId().compareTo(p.getUniqueId()) == 0) {
                ((Creature) e).setTarget(null);
            }
        }

        for (Map.Entry<UUID, String> pl : players.entrySet()) {
            Player target = Bukkit.getServer().getPlayer(pl.getKey());
            if (target != null) {
                p.showPlayer(plugin, target);
            }
        }

        p.setMetadata("vanished", new FixedMetadataValue(plugin, true));
    }

    public static boolean Enabled(Player p) {
        try {
            return players.containsKey(p.getUniqueId());
        } catch (Exception e) {
            return false;
        }
    }
}
