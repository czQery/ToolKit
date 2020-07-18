package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class Move implements Listener {
    private Main plugin;

    public Move(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    @SuppressWarnings("deprecation")
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
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.sendBlockChange(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ()), Material.BARRIER, (byte)0);
                    }
                    loc.getBlock().setType(Material.BARRIER);
                }
                Scripts.bCheck(p);
            }
        }
        //SIT
        if (p.getMetadata("sit").toString() != "[]") {
            if (p.getMetadata("sit").get(0).asInt() != 0) {
                if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                    Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
                    for (Entity ent: loc.getChunk().getEntities()){
                        if (ent.getEntityId() == p.getMetadata("sit").get(0).asInt()) {
                            ent.remove();
                            p.setMetadata("sit", new FixedMetadataValue(plugin, 0));
                            p.sendMessage(Utils.chat(b+"["+n+"SIT"+b+"]"+t+" Sit mode has been turned &cOFF"+t+"!"));
                        }
                    }
                }
            }
        }
    }
}
