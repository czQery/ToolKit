package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Crash implements CommandExecutor {

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
                    sender.sendMessage(Tools.chat(b+"["+n+"CRASH"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                    return false;
                }
            } else {
                Tools.log(b+"["+n+"CRASH"+b+"]"+t+" Please use "+h+"/crash <player>");
                return false;
            }
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission("toolkit.crash")) {
                p.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
                return false;
            } else {
                if (args.length > 0) {
                    target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        p.sendMessage(Tools.chat(b + "[" + n + "CRASH" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                        return false;
                    }
                } else {
                    p.sendMessage(Tools.chat(b+"["+n+"CRASH"+b+"]"+t+" Please use "+h+"/crash <player>"));
                    return false;
                }
            }
        }


        if(target.hasPermission("toolkit.crash.bypass")){
            sender.sendMessage(Tools.chat(b+"["+n+"CRASH"+b+"]"+t+" You cannot crash this player!"));
        } else {
            Player finalTarget = target;
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    Scripts.crash(finalTarget);
                    sender.sendMessage(Tools.chat(b+"["+n+"CRASH"+b+"]"+t+" Player "+h+finalTarget.getName()+t+" has been crashed!"));
                } catch (InterruptedException e) {
                    sender.sendMessage(Tools.chat(b+"["+n+"CRASH"+b+"]&c Failed to crash player "+h+finalTarget.getName()));
                }
            });
        }

        return false;
    }
}