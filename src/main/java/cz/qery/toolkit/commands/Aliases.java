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

        if (!(sender instanceof Player)) {
            Tools.log(b+"["+n+"SERVER"+b+"]"+t+" This command cannot be used by the console!");
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission("toolkit."+cmd.getName().toLowerCase())) {
                p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " You're not allowed to do this!"));
            } else {
                Player target;
                if (args.length > 0) {
                    target = Bukkit.getServer().getPlayer(args[0]);
                } else {
                    target = p;
                }
                switch (cmd.getName().toLowerCase()) {
                    case "gmc":
                        target.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "CREATIVE"));
                        break;
                    case "gms":
                        target.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "SURVIVAL"));
                        break;
                    case "gma":
                        target.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "ADVENTURE"));
                        break;
                    case "gmsp":
                        target.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "SPECTATOR"));
                        break;
                }
            }
        }
        return false;
    }
}
