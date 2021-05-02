package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {

    public Interact(Main plugin) {

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        //FREEZE
        if (!p.getMetadata("freeze").toString().equals("[]")) {
            if (p.getMetadata("freeze").get(0).asBoolean()) {
                e.setCancelled(true);
            }
        }
    }
}
