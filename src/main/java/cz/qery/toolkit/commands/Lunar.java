package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import cz.qery.toolkit.lunar.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class Lunar implements CommandExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " This command cannot be used by the console!"));
        } else {
            if (!p.hasPermission("toolkit.lunar")) {
                p.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
            } else {
                if (args.length < 1) {
                    p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Please use " + h + "/lunar <tool>"));
                } else {
                    switch (args[0].toLowerCase()) {
                        case "help" -> {
                            p.sendMessage(Tools.chat(b + "-------[" + n + "LUNAR" + b + "]--------"));
                            p.sendMessage(Tools.chat(b + "- " + t + "Waypoint"));
                            p.sendMessage(Tools.chat(b + "----------------------"));
                        }
                        case "waypoint" -> {
                            if (args.length < 2) {
                                p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Please use " + h + "/lunar waypoint <add/remove/list>"));
                            } else {
                                switch (args[1].toLowerCase()) {
                                    case "add" -> {
                                        if (args.length < 4) {
                                            p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Please use " + h + "/lunar waypoint <add> <name> <HEX-color>"));
                                        } else {
                                            for (Waypoint waypoint : Waypoint.waypoints) {
                                                if (waypoint.name().equals(args[2])) {
                                                    p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Waypoint with this name already exists!"));
                                                    return false;
                                                }
                                            }
                                            if (args[3].contains("#") && args[3].matches("^[a-fA-F0-9#]{0,7}$")) {
                                                Waypoint waypoint = new Waypoint(args[2], p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getWorld().getName(), args[3]);
                                                Waypoint.waypoints.add(waypoint);
                                                Bukkit.getScheduler().runTaskAsynchronously(plugin, Waypoint::Update);
                                                p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Waypoint created!"));
                                            } else {
                                                p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " You must use HEX color (example: white = #FFFFFF)!"));
                                            }
                                        }
                                    }
                                    case "remove" -> {
                                        if (args.length < 3) {
                                            p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Please use " + h + "/lunar waypoint <remove> <name>"));
                                        } else {
                                            for (Waypoint waypoint : Waypoint.waypoints) {
                                                if (waypoint.name().equals(args[2])) {
                                                    Waypoint.waypoints.remove(waypoint);
                                                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Waypoint.Remove(waypoint.name(), Objects.requireNonNull(Bukkit.getWorld(waypoint.world())).getUID().toString()));
                                                    p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Waypoint removed!"));
                                                    return false;
                                                }
                                            }
                                            p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Waypoint with this name does not exists!"));
                                        }
                                    }
                                    case "list" -> {
                                        p.sendMessage(Tools.chat(b + "-------[" + n + "LUNAR-WAYPOINTS" + b + "]--------"));
                                        for (Waypoint waypoint : Waypoint.waypoints) {
                                            p.sendMessage(Tools.chat(b + "- " + t + waypoint.name()));
                                        }
                                        p.sendMessage(Tools.chat(b + "--------------------------------"));
                                    }
                                    default ->
                                            p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Please use " + h + "/lunar waypoint <add/remove/list>"));
                                }
                            }
                        }
                        default ->
                                p.sendMessage(Tools.chat(b + "[" + n + "LUNAR" + b + "]" + t + " Please use " + h + "/lunar <tool>"));
                    }
                }
            }
        }
        return false;
    }

}
