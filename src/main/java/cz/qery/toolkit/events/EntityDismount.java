package cz.qery.toolkit.events;

import org.spigotmc.event.entity.EntityDismountEvent;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

public class EntityDismount implements Listener {
    private Main plugin;

    public EntityDismount(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onEntityDismount(EntityDismountEvent e) {
        Entity p = e.getEntity();
        Entity en = e.getDismounted();

        String b = plugin.getConfig().getString("color.bracket");
        String n = plugin.getConfig().getString("color.name");
        String t = plugin.getConfig().getString("color.text");

        if (en.getType() == EntityType.ARMOR_STAND && p.getType() == EntityType.PLAYER) {
            if (p.getMetadata("sit").toString() != "[]") {
                if (p.getMetadata("sit").get(0).asInt() != 0) {
                    Location loc = p.getLocation();
                    for (Entity ent: loc.getChunk().getEntities()){
                        if (ent.getEntityId() == p.getMetadata("sit").get(0).asInt()) {
                            ent.remove();

                            p.teleport(loc.add(0, 1.7, 0));
                            p.setMetadata("sit", new FixedMetadataValue(plugin, 0));
                            p.sendMessage(Tools.chat(b+"["+n+"SIT"+b+"]"+t+" Sit mode has been turned &cOFF"+t+"!"));
                        }
                    }
                }
            }
        }
    }
}
