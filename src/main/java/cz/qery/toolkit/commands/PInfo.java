package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PInfo implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        Player target;

        if (!CommandHandler.hasPermission(sender, cmd)) {
            return false;
        }

        if (args.length > 0) {
            target = Bukkit.getServer().getPlayer(args[0]);
            if(target == null){
                sender.sendMessage(Tools.chat(b+"["+n+"PINFO"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                return false;
            }
        } else {
            sender.sendMessage(Tools.chat(b+"["+n+"PINFO"+b+"]"+t+" Please use "+h+"/pinfo <player>"));
            return false;
        }

        Player finalTarget = target;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String name = finalTarget.getName();
            String ip = Objects.requireNonNull(finalTarget.getAddress()).getHostName();
            String client;
            String trueclient = null;

            if (!Objects.equals(finalTarget.getMetadata("client").toString(), "[]")) {
                client = finalTarget.getMetadata("client").get(0).asString();
            } else {
                client = "Vanilla";
            }

            if (!Objects.equals(finalTarget.getMetadata("trueclient").toString(), "[]")) {
                trueclient = finalTarget.getMetadata("trueclient").get(0).asString();
            }

            sender.sendMessage(Tools.chat(b+"-------["+n+"PlayerInfo"+b+"]-------"));
            sender.sendMessage(Tools.chat(b+"- "+t+"Username "+h+name));
            sender.sendMessage(Tools.chat(b+"- "+t+"Ip "+h+ip));
            if (trueclient != null) {
                sender.sendMessage(Tools.chat(b+"- "+t+"Client "+h+client+" ("+trueclient+")"));
            } else {
                sender.sendMessage(Tools.chat(b+"- "+t+"Client "+h+client));
            }
            sender.sendMessage(Tools.chat(b+"------------------------"));
        });

        return false;
    }
}
