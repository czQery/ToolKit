package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import cz.qery.toolkit.helper.Vanish;
import cz.qery.toolkit.loader.Commands;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Aliases implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = Main.colors.get("b");
    String n = Main.colors.get("n");
    String t = Main.colors.get("t");
    String h = Main.colors.get("h");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        Player target;

        if (!(sender instanceof Player p)) {
            if (args.length > 0) {
                target = Commands.getPlayer(sender, args[0]);
                if (target == null) {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                    return false;
                }
            } else {
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Please use " + h + "/" + cmd.getName().toLowerCase() + " <player>"));
                return false;
            }
        } else {
            if (!p.hasPermission("toolkit." + cmd.getName().toLowerCase())) {
                p.sendMessage(Other.Tools.chat(plugin.getConfig().getString("commandblock.message")));
                return false;
            } else {
                if (args.length > 0) {
                    if (!p.hasPermission("toolkit." + cmd.getName().toLowerCase() + ".other")) {
                        p.sendMessage(Other.Tools.chat(plugin.getConfig().getString("commandblock.message")));
                        return false;
                    } else {
                        target = Commands.getPlayer(sender, args[0]);
                        if (target == null) {
                            p.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                            return false;
                        }
                    }
                } else {
                    target = p;
                }
            }
        }

        switch (cmd.getName().toLowerCase()) {
            case "gmc" -> {
                target.setGameMode(GameMode.CREATIVE);
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "CREATIVE"));
            }
            case "gms" -> {
                target.setGameMode(GameMode.SURVIVAL);
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "SURVIVAL"));
                if (Vanish.Enabled(target)) {
                    target.setAllowFlight(true);
                }
            }
            case "gma" -> {
                target.setGameMode(GameMode.ADVENTURE);
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "ADVENTURE"));
                if (Vanish.Enabled(target)) {
                    target.setAllowFlight(true);
                }
            }
            case "gmsp" -> {
                target.setGameMode(GameMode.SPECTATOR);
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Switched to " + h + "SPECTATOR"));
            }
            case "spawn" -> {
                Other.spawnTeleport(target);
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Teleported to " + h + "SPAWN"));
            }
            case "fly" -> {
                if (target.getAllowFlight()) {
                    target.setAllowFlight(false);
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Fly mode has been turned &cOFF" + t + "!"));
                } else {
                    target.setAllowFlight(true);
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Fly mode has been turned &aON" + t + "!"));
                }
            }
            case "wc" -> {
                target.getWorld().setStorm(false);
                target.getWorld().setThundering(false);
                target.getWorld().setClearWeatherDuration(300);
                target.getWorld().setTime(1000);
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Weather cleared!"));
            }
            case "ic" -> {
                target.getInventory().setContents(new ItemStack[]{});
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Inventory cleared!"));
            }
        }
        return false;
    }
}