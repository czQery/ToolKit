package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class Move implements Listener {
    static Plugin plugin = Main.getPlugin(Main.class);

    public Move(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        //FREEZE
        if (!Objects.equals(p.getMetadata("freeze").toString(), "[]")) {
            if (p.getMetadata("freeze").get(0).asBoolean()) {
                if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                    e.setCancelled(true);
                }
            }
        }
        //CRAWL
        if (!Objects.equals(p.getMetadata("crawl").toString(), "[]")) {
            if (p.getMetadata("crawl").get(0).asBoolean()) {
                Scripts.bCheck(p);
            }
        }

        //LUNAR
        if (plugin.getConfig().getBoolean("lunar.kick")) {
            if (!Objects.equals(Objects.requireNonNull(p.getPlayer()).getMetadata("client").toString(), "[]")) {
                if (!p.getPlayer().getMetadata("client").get(0).asString().equals("LunarClient")) {
                    e.getPlayer().kick(Component.text(Tools.chat(plugin.getConfig().getString("lunar.message"))));
                }
            } else {
                e.getPlayer().kick(Component.text(Tools.chat(plugin.getConfig().getString("lunar.message"))));
            }
        }

        //SIT
        if (!Objects.equals(p.getMetadata("sit").toString(), "[]") && p.getMetadata("sit").get(0).asInt() != 0) {
            Scripts.sCheck(p);
        }
    }
}
