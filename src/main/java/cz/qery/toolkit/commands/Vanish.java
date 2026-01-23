package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import cz.qery.toolkit.loader.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class Vanish implements CommandExecutor {

    String b = Main.colors.get("b");
    String n = Main.colors.get("n");
    String t = Main.colors.get("t");
    String h = Main.colors.get("h");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player target;

        if (!Commands.hasPermission(sender, cmd)) {
            return false;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("list")) {
                if (!cz.qery.toolkit.helper.Vanish.players.isEmpty()) {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "VANISH" + b + "]"));
                    for (Map.Entry<UUID, String> pl : cz.qery.toolkit.helper.Vanish.players.entrySet()) {
                        sender.sendMessage(Other.Tools.chat(b + "- " + t + pl.getValue()));
                    }
                } else {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "VANISH" + b + "]" + t + " There are no players in vanish mode"));
                }
                return false;
            }

            target = Commands.getPlayer(sender, args[0]);
            if (target == null) {
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "VANISH" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                return false;
            }
        } else {
            if ((sender instanceof Player)) {
                target = (Player) sender;
            } else {
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "VANISH" + b + "]" + t + " Please use " + h + "/vanish <player/list>"));
                return false;
            }
        }


        // turn on/off logic
        if (!cz.qery.toolkit.helper.Vanish.Enabled(target)) {
            cz.qery.toolkit.helper.Vanish.Hide(target, true);
        } else {
            cz.qery.toolkit.helper.Vanish.Show(target, true);
        }

        return false;
    }
}
