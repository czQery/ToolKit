package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import cz.qery.toolkit.helper.Vanish;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

public class Join implements Listener {
    static Plugin plugin = Main.getPlugin(Main.class);
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");
    static String t = Main.colors.get("t");
    static String h = Main.colors.get("h");

    public Join(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (plugin.getConfig().getBoolean("spawn.join")) {
            Other.spawnTeleport(p);
        }

        if (plugin.getConfig().getBoolean("join.alert") && !Vanish.Enabled(p)) {
            e.joinMessage(Component.text(Other.Tools.chat(plugin.getConfig().getString("join.message")).replace("%player%", p.getName())));
        } else {
            e.joinMessage(null);
        }

        // vanish
        for (Map.Entry<UUID, String> pl : Vanish.players.entrySet()) {
            if (p.getUniqueId().compareTo(pl.getKey()) == 0) {
                Vanish.Hide(p, false);
            } else {
                Player target = Bukkit.getServer().getPlayer(pl.getKey());
                if (target != null) {
                    if (p.hasPermission("toolkit.vanish")) {
                        p.sendMessage(Other.Tools.chat(b + "[" + n + "VANISH" + b + "]" + t + " Player " + h + target.getName() + t + " has &aentered" + t + " vanish mode!"));
                    }
                    p.hidePlayer(plugin, target);
                }
            }
        }
    }
}
