package cz.qery.toolkit.commands;

import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointAdd;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import cz.qery.toolkit.lunar.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Lunar implements CommandExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player)) {
            Tools.log(b + "[" + n + "SERVER" + b + "]" + t + " This command cannot be used by the console!");
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission("toolkit.lunar")) {
                p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " You're not allowed to do this!"));
            } else {
                if (args.length < 1) {
                    p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Please use " + h + "/lunar <tool>"));
                } else {
                    switch (args[0].toLowerCase()) {
                        case "help":
                            p.sendMessage(Tools.chat(b + "-------[" + n + "LUNAR" + b + "]--------"));
                            p.sendMessage(Tools.chat(b + "- " + t + "Waypoint"));
                            p.sendMessage(Tools.chat(b + "----------------------"));
                            break;
                        case "waypoint":
                            if(args.length < 3) {
                                p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Please use " + h + "/lunar waypoint <name> <HEX-color>"));
                            } else {
                                Waypoint waypoint = new Waypoint(args[1], p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getWorld().getName(), args[2]);
                                Waypoint.waypoints.add(waypoint);
                                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Waypoint.Update());
                                p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Waypoint created!"));
                            }
                            break;
                        default:
                            p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Please use " + h + "/lunar <tool>"));
                            break;
                    }
                }
            }
        }
        return false;
    }

}
