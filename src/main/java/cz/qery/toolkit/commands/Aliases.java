package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Aliases implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        Player target = null;

        if (!(sender instanceof Player)) {
            if (args.length > 0) {
                target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    sender.sendMessage(Tools.chat(b+"["+n+"SERVER"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                    return false;
                }
            } else {
                Tools.log(b+"["+n+"SERVER"+b+"]"+t+" Please use "+h+"/"+cmd.getName().toLowerCase()+" <player>");
                return false;
            }
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission("toolkit."+cmd.getName().toLowerCase())) {
                p.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
                return false;
            } else {
                if (args.length > 0) {
                    target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                        return false;
                    }
                } else {
                    target = p;
                }
            }
        }

        switch (cmd.getName().toLowerCase()) {
            case "gmc":
                target.setGameMode(GameMode.CREATIVE);
                sender.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "CREATIVE"));
                break;
            case "gms":
                target.setGameMode(GameMode.SURVIVAL);
                sender.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "SURVIVAL"));
                break;
            case "gma":
                target.setGameMode(GameMode.ADVENTURE);
                sender.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "ADVENTURE"));
                break;
            case "gmsp":
                target.setGameMode(GameMode.SPECTATOR);
                sender.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "SPECTATOR"));
                break;
        }
        return false;
    }
}
