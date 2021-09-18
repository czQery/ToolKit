package cz.qery.toolkit.events;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketUpdateWorld;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointAdd;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.lunar.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;

public class Join implements Listener {
    private final Main plugin;

    public Join(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (plugin.getConfig().getBoolean("join.teleport")) {
            String config_world = plugin.getConfig().getString("join.world");
            World world = Bukkit.getWorld(config_world).getSpawnLocation().getWorld();
            double x = Bukkit.getWorld(config_world).getSpawnLocation().getX() + 0.5;
            double y = Bukkit.getWorld(config_world).getSpawnLocation().getY() + 0.5;
            double z = Bukkit.getWorld(config_world).getSpawnLocation().getZ() + 0.5;
            Location location = new Location(world, x, y, z);
            p.teleport(location);
        }
        if (plugin.getConfig().getBoolean("join.alert")) {
            e.setJoinMessage(Tools.chat(plugin.getConfig().getString("join.message")).replace("%player%",p.getName()));
        } else {
            e.setJoinMessage("");
        }

        //LUNAR STAFF
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Waypoint.SendOne(p), 20);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Waypoint.SendOne(p), 40);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Waypoint.SendOne(p), 60);
    }
}
