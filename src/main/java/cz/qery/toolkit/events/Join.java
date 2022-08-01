package cz.qery.toolkit.events;

import cz.qery.toolkit.Vnsh;
import cz.qery.toolkit.lunar.Mod;
import cz.qery.toolkit.lunar.Waypoint;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.plugin.Plugin;

public class Join implements Listener {
    static Plugin plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String t = plugin.getConfig().getString("color.text");
    static String h = plugin.getConfig().getString("color.highlight");

    public Join(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (plugin.getConfig().getBoolean("join.teleport")) {
            if (Bukkit.getWorld(plugin.getConfig().getString("join.world")) == null) {
                Tools.log(b+"["+n+"ToolKit"+b+"] "+t+"Join teleport has invalid world "+h+plugin.getConfig().getString("join.world"));
            } else {
                String config_world = plugin.getConfig().getString("join.world");
                World world = Bukkit.getWorld(config_world).getSpawnLocation().getWorld();
                double x = Bukkit.getWorld(config_world).getSpawnLocation().getX() + 0.5;
                double y = Bukkit.getWorld(config_world).getSpawnLocation().getY() + 0.5;
                double z = Bukkit.getWorld(config_world).getSpawnLocation().getZ() + 0.5;
                Location location = new Location(world, x, y, z);
                p.teleport(location);
            }
        }

        if (plugin.getConfig().getBoolean("join.alert") && !Vnsh.Enabled(p)) {
            e.joinMessage(Component.text(Tools.chat(plugin.getConfig().getString("join.message")).replace("%player%",p.getName())));
        } else {
            e.joinMessage(null);
        }

        // vanish
        for (String pl : Vnsh.players) {
            if (pl.equalsIgnoreCase(p.getName())) {
                Vnsh.Hide(p, false);
            } else {
                Player target = Bukkit.getServer().getPlayer(pl);
                if (target != null) {
                    if (p.hasPermission("toolkit.vanish")) {
                        p.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Player "+h+target.getName()+t+" has &aentered"+t+" vanish mode!"));
                    }
                    p.hidePlayer(plugin, target);
                }
            }
        }

        // lunar
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Waypoint.SendOne(p), 40);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> Mod.SendOne(p), 40);
    }
}
