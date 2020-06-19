package cz.qery.toolkit.events;

import cz.qery.toolkit.ToolKit;
import cz.qery.toolkit.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    private ToolKit plugin;

    public Join(ToolKit plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        World world = Bukkit.getWorld(plugin.getConfig().getString("world")).getSpawnLocation().getWorld();
        double x = Bukkit.getWorld(plugin.getConfig().getString("world")).getSpawnLocation().getX() + 0.5;
        double y = Bukkit.getWorld(plugin.getConfig().getString("world")).getSpawnLocation().getY() + 0.5;
        double z = Bukkit.getWorld(plugin.getConfig().getString("world")).getSpawnLocation().getZ() + 0.5;
        Location location = new Location(world, x, y, z);
        p.teleport(location);
        if (plugin.getConfig().getBoolean("welcome")) {
            e.setJoinMessage(Utils.chat("&8[&cSERVER&8]&6 "+p.getName()+"&7 joined!"));
        } else {
            e.setJoinMessage("");
        }
    }
}
