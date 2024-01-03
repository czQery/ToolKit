package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import cz.qery.toolkit.Vnsh;
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
            Scripts.spawnTeleport(p);
        }

        if (plugin.getConfig().getBoolean("join.alert") && !Vnsh.Enabled(p)) {
            if (Tools.isPaper) {
                e.joinMessage(Component.text(Tools.chat(plugin.getConfig().getString("join.message")).replace("%player%",p.getName())));
            } else {
                e.setJoinMessage(Tools.chat(plugin.getConfig().getString("join.message")).replace("%player%",p.getName()));
            }
        } else {
            e.joinMessage(null);
        }

        // vanish
        for (Map.Entry<UUID, String> pl : Vnsh.players.entrySet()) {
            if (p.getUniqueId() == pl.getKey()) {
                Vnsh.Hide(p, false);
            } else {
                Player target = Bukkit.getServer().getPlayer(pl.getKey());
                if (target != null) {
                    if (p.hasPermission("toolkit.vanish")) {
                        p.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Player "+h+target.getName()+t+" has &aentered"+t+" vanish mode!"));
                    }
                    p.hidePlayer(plugin, target);
                }
            }
        }
    }
}
