package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Msg implements CommandExecutor {

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
            if (target == null) {
                sender.sendMessage(Tools.chat(b + "[" + n + "MSG" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                return false;
            }
        } else {
            sender.sendMessage(Tools.chat(b + "[" + n + "MSG" + b + "]" + t + " Please use " + h + "/msg <player> <message>"));
            return false;
        }

        String[] textArray = Arrays.copyOfRange(args, 1, args.length);
        String text = String.join(" ", textArray);

        String chatSender = plugin.getConfig().getString("msg.sender");
        assert chatSender != null;
        chatSender = chatSender.replace("%sender%", sender.getName());
        chatSender = chatSender.replace("%receiver%", target.getName());
        chatSender = chatSender.replace("%msg%", text);

        String chatReceiver = plugin.getConfig().getString("msg.receiver");
        assert chatReceiver != null;
        chatReceiver = chatReceiver.replace("%sender%", sender.getName());
        chatReceiver = chatReceiver.replace("%receiver%", target.getName());
        chatReceiver = chatReceiver.replace("%msg%", text);

        sender.sendMessage(Tools.chat(chatSender));
        target.sendMessage(Tools.chat(chatReceiver));

        return false;
    }
}
