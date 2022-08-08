package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import cz.qery.toolkit.Vnsh;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Leave implements Listener {
    private final Main plugin;

    public Leave(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (plugin.getConfig().getBoolean("leave.alert") && !Vnsh.Enabled(p)) {
            e.quitMessage(Component.text(Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%",p.getName())));
        } else {
            e.quitMessage(null);
        }

        // Sit check
        if (p.getMetadata("sit").toString() != "[]") {
            if (p.getMetadata("sit").get(0).asInt() != 0) {
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                for (Entity ent: loc.getChunk().getEntities()){
                    if (ent.getEntityId() == p.getMetadata("sit").get(0).asInt()) {
                        ent.remove();
                    }
                }
            }
        }

        // Crawl check
        if (p.getMetadata("crawl").toString() != "[]") {
            if (p.getMetadata("crawl").get(0).asBoolean()) {
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
                p.setMetadata("crawl", new FixedMetadataValue(plugin, false));
                Scripts.bCheck(p);
                if (loc.getBlock().getType() == Material.BARRIER){loc.getBlock().setType(Material.AIR);}
            }
        }

        // Client
        p.removeMetadata("client", plugin);
        p.removeMetadata("trueclient", plugin);
    }
}
