package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import cz.qery.toolkit.loader.Commands;
import cz.qery.toolkit.loader.LunarWaypoints;
import cz.qery.toolkit.loader.Waypoints;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class WP implements TabExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    String b = Main.colors.get("b");
    String n = Main.colors.get("n");
    String t = Main.colors.get("t");
    String h = Main.colors.get("h");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " This command cannot be used by the console!"));
            return false;
        }

        if (!Commands.hasPermission(sender, cmd)) {
            return false;
        }

        if (args.length < 1) {
            p.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " Please use " + h + "/wp <add/remove/list>"));
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "add" -> {
                if (args.length < 3) {
                    p.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " Please use " + h + "/wp<add> <name> <HEX-color>"));
                } else {
                    for (Waypoints waypoint : Waypoints.list) {
                        if (waypoint.name().equals(args[1])) {
                            p.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " Waypoint with this name already exists!"));
                            return false;
                        }
                    }
                    if (args[2].contains("#") && args[2].matches("^[a-fA-F0-9#]{0,7}$")) {
                        Waypoints waypoint = new Waypoints(args[1], p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getWorld().getName(), args[2]);
                        Waypoints.list.add(waypoint);
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, Waypoints::Update);
                        p.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " Waypoint created!"));
                    } else {
                        p.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " You must use HEX color (example: white = #FFFFFF)!"));
                    }
                }
            }
            case "remove" -> {
                if (args.length < 2) {
                    p.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " Please use " + h + "/wp <remove> <name>"));
                } else {
                    for (Waypoints waypoint : Waypoints.list) {
                        if (waypoint.name().equals(args[1])) {
                            Waypoints.list.remove(waypoint);
                            if (Main.ApolloLoaded)
                                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> LunarWaypoints.Remove(waypoint.name()));
                            p.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " Waypoint removed!"));
                            return false;
                        }
                    }
                    p.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " Waypoint with this name does not exists!"));
                }
            }
            case "list" -> {
                if (!Waypoints.list.isEmpty()) {
                    p.sendMessage(Other.Tools.chat(b + "[" + n + "WAYPOINTS" + b + "]"));
                    for (Waypoints waypoint : Waypoints.list) {
                        p.sendMessage(Other.Tools.chat(b + "- " + t + waypoint.name()));
                    }
                } else {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " There are no waypoints!"));
                }
            }
            default ->
                    p.sendMessage(Other.Tools.chat(b + "[" + n + "WP" + b + "]" + t + " Please use " + h + "/wp <add/remove/list>"));
        }

        return false;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        List<String> list = new ArrayList<>();

        switch (args.length) {
            case 1 -> {
                list.add("add");
                list.add("remove");
                list.add("list");
            }
            case 2 -> list.add("name");
            case 3 -> {
                list.add("#ffffff");
                list.add("#ff0000");
                list.add("#fffb00");
                list.add("#26ff00");
                list.add("#006eff");
                list.add("#8c00ff");
                list.add("#8c00ff");
            }
        }

        if (!list.isEmpty()) {
            return list;
        }

        return null;
    }
}
