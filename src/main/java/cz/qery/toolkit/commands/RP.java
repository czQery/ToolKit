package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RP implements TabExecutor {
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

        if (args.length > 1) {
            target = CommandHandler.getPlayer(sender, args[0]);
            if(target == null){
                sender.sendMessage(Tools.chat(b+"["+n+"RP"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                return false;
            }
        } else {
            sender.sendMessage(Tools.chat(b+"["+n+"RP"+b+"]"+t+" Please use "+h+"/rp <player> <url>"));
            return false;
        }


        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            target.setResourcePack(args[1], "", true);

            target.sendMessage(Tools.chat(b + "[" + n + "RP" + b + "]" + t + " Player " + h + sender.getName() + t + " has sent you" + h + " resource pack" + t + "!"));
            sender.sendMessage(Tools.chat(b + "[" + n + "RP" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " resource pack" + t + "!"));
        });
        return false;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        List<String> list = new ArrayList<>();

        switch (args.length) {
            case 1 -> list = CommandHandler.getPlayerList();
            case 2 -> list.add("url");
        }

        if (!list.isEmpty()) {
            return list;
        }

        return null;

    }
}
