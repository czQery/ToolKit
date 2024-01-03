package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class Respawn implements Listener {
   static Plugin plugin = Main.getPlugin(Main.class);

    public Respawn(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (!e.isAnchorSpawn() && !e.isBedSpawn() && plugin.getConfig().getBoolean("spawn.death")) {
            e.setRespawnLocation(Objects.requireNonNull(Scripts.spawnTeleport(p)));
        }
    }
}
