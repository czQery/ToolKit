package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Move implements Listener {
    private Main plugin;

    public Move(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        String b = plugin.getConfig().getString("color.bracket");
        String n = plugin.getConfig().getString("color.name");
        String t = plugin.getConfig().getString("color.text");

        //FREEZE
        if (p.getMetadata("freeze").toString() != "[]") {
            if (p.getMetadata("freeze").get(0).asBoolean()) {
                if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                    e.setCancelled(true);
                }
            }
        }
        //CRAWL
        if (p.getMetadata("crawl").toString() != "[]") {
            if (p.getMetadata("crawl").get(0).asBoolean()) {
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
                if (loc.getBlock().isEmpty()) {
                    loc.getBlock().setType(Material.BARRIER);
                }
                Scripts.bCheck(p);
            }
        }

        //LUNAR
        if (plugin.getConfig().getBoolean("lunar.kick")) {
            if (p.getPlayer().getMetadata("client").toString() != "[]") {
                if (!p.getPlayer().getMetadata("client").get(0).asString().equals("LunarClient")) {
                    if (Tools.isPaper) {
                        e.getPlayer().kick(Component.text(Tools.chat(plugin.getConfig().getString("lunar.message"))));
                    } else {
                        e.getPlayer().kickPlayer(Tools.chat(plugin.getConfig().getString("lunar.message")));
                    }
                }
            } else {
                if (Tools.isPaper) {
                    e.getPlayer().kick(Component.text(Tools.chat(plugin.getConfig().getString("lunar.message"))));
                } else {
                    e.getPlayer().kickPlayer(Tools.chat(plugin.getConfig().getString("lunar.message")));
                }
            }
        }

        //SIT
        Scripts.sCheck(p, false);
    }
}
