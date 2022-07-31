package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import cz.qery.toolkit.Vnsh;
import cz.qery.toolkit.lunar.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Vanish implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player target = null;

        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            if (!p.hasPermission("toolkit.vanish")) {
                p.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
                return false;
            } else if (args.length > 0) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (Vnsh.players.size() > 0) {
                        p.sendMessage(Tools.chat(b + "-------[" + n + "VANISH" + b + "]--------"));
                        for (String pl : Vnsh.players) {
                            p.sendMessage(Tools.chat(b + "- " + t + pl));
                        }
                        p.sendMessage(Tools.chat(b + "-----------------------"));
                    } else {
                        p.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" There are no players in vanish mode"));
                    }
                    return false;
                } else {
                    target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        p.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Player "+h+args[0]+t+ " is not online!"));
                        return false;
                    }
                }
            } else {
                target = p;
            }
        } else {
            CommandSender p = sender;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (Vnsh.players.size() > 0) {
                        p.sendMessage(Tools.chat(b + "-------[" + n + "VANISH" + b + "]--------"));
                        for (String pl : Vnsh.players) {
                            p.sendMessage(Tools.chat(b + "- " + t + pl));
                        }
                        p.sendMessage(Tools.chat(b + "----------------------"));
                    } else {
                        p.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" There are no players in vanish mode"));
                    }
                    return false;
                } else {
                    target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        p.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Player "+h+args[0]+t+ " is not online!"));
                        return false;
                    }
                }
            } else {
                p.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Please use "+h+"/vanish <player/list>"));
                return false;
            }
        }

        // turn on/off logic
        if (!Vnsh.Enabled(target)) {
            Vnsh.Hide(target, true);
        } else {
            Vnsh.Show(target, true);
        }

        return false;
    }
}
