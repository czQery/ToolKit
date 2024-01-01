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
    String b = Main.colors.get("b");
    String n = Main.colors.get("n");
    String t = Main.colors.get("t");
    String h = Main.colors.get("h");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player target;

        if (!CommandHandler.hasPermission(sender, cmd)) {
            return false;
        }

        if (args.length > 0) {
            target = CommandHandler.getPlayer(sender, args[0]);
            if (target == null) {
                sender.sendMessage(Tools.chat(b + "[" + n + "CRASH" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                return false;
            }
        } else {
            sender.sendMessage(Tools.chat(b + "[" + n + "CRASH" + b + "]" + t + " Please use " + h + "/crash <player>"));
            return false;
        }

        if (CommandHandler.hasPermissionBypass(target, cmd)) {
            sender.sendMessage(Tools.chat(b + "[" + n + "CRASH" + b + "]" + t + " You cannot crash this player!"));
            return false;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                Scripts.crash(target);
                sender.sendMessage(Tools.chat(b + "[" + n + "CRASH" + b + "]" + t + " Player " + h + target.getName() + t + " has been crashed!"));
            } catch (InterruptedException e) {
                sender.sendMessage(Tools.chat(b + "[" + n + "CRASH" + b + "]&c Failed to crash player " + h + target.getName()));
            }
        });


        return false;
    }
}