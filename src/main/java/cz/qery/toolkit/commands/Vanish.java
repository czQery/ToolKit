package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import cz.qery.toolkit.Vnsh;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class Vanish implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player target;

        if ((sender instanceof Player) && !sender.hasPermission("toolkit.vanish")) {
            sender.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
            return false;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("list")) {
                if (Vnsh.players.size() > 0) {
                    sender.sendMessage(Tools.chat(b + "-------[" + n + "VANISH" + b + "]--------"));
                    for (Map.Entry<UUID, String> pl : Vnsh.players.entrySet()) {
                        sender.sendMessage(Tools.chat(b + "- " + t + pl.getValue()));
                    }
                    sender.sendMessage(Tools.chat(b + "----------------------"));
                } else {
                    sender.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" There are no players in vanish mode"));
                }
                return false;
            }

            target = Bukkit.getServer().getPlayer(args[0]);
            if(target == null){
                sender.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                return false;
            }
        } else {
            sender.sendMessage(Tools.chat(b+"["+n+"VANISH"+b+"]"+t+" Please use "+h+"/vanish <player/list>"));
            return false;
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
