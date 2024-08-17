package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;

import java.util.Objects;

public class EntityDismount implements Listener {

    public EntityDismount(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent e) {
        Entity p = e.getEntity();
        Entity en = e.getDismounted();

        if (en.getType() == EntityType.ARMOR_STAND && p.getType() == EntityType.PLAYER) {
            if (!Objects.equals(p.getMetadata("sit").toString(), "[]") && p.getMetadata("sit").get(0).asInt() != 0) {
                Scripts.sCheck((Player) p);
            }
        }
    }
}
