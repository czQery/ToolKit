package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.Objects;

public class EntityDismount implements Listener {
    private final Main plugin;

    public EntityDismount(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent e) {
        Entity p = e.getEntity();
        Entity en = e.getDismounted();

        String b = plugin.getConfig().getString("color.bracket");
        String n = plugin.getConfig().getString("color.name");
        String t = plugin.getConfig().getString("color.text");

        if (en.getType() == EntityType.ARMOR_STAND && p.getType() == EntityType.PLAYER) {
            if (!Objects.equals(p.getMetadata("sit").toString(), "[]") && p.getMetadata("sit").get(0).asInt() != 0) {
                Scripts.sCheck((Player) p);
            }
        }
    }
}
