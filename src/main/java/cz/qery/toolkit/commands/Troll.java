package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.server.level.EntityPlayer;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Troll implements TabExecutor {

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
                sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                return false;
            }
        } else {
            sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> <troll>"));
            return false;
        }

        if (CommandHandler.hasPermissionBypass(target, cmd)) {
            sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " You cannot troll this player!"));
            return false;
        }

        //SNEAK
        switch (args[1].toLowerCase()) {
            case "help" -> {
                sender.sendMessage(Tools.chat(b + "-------[" + n + "TROLL" + b + "]-------"));
                sender.sendMessage(Tools.chat(b + "- " + t + "Sneak"));
                sender.sendMessage(Tools.chat(b + "- " + t + "Sleep (it must be night, and you must stand on the bed)"));
                sender.sendMessage(Tools.chat(b + "- " + t + "Close"));
                sender.sendMessage(Tools.chat(b + "- " + t + "CloseSpam (anti-leave)"));
                sender.sendMessage(Tools.chat(b + "- " + t + "Glow"));
                sender.sendMessage(Tools.chat(b + "- " + t + "PickUp"));
                sender.sendMessage(Tools.chat(b + "- " + t + "Freeze"));
                sender.sendMessage(Tools.chat(b + "- " + t + "FakeOP"));
                sender.sendMessage(Tools.chat(b + "- " + t + "Flip"));
                sender.sendMessage(Tools.chat(b + "- " + t + "Thor"));
                sender.sendMessage(Tools.chat(b + "- " + t + "Fakedemo"));
                sender.sendMessage(Tools.chat(b + "----------------------"));
            }
            case "sneak" -> {
                if (args.length == 2) {
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> sneak <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setSneaking(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sneak" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setSneaking(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sneak" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> sneak <boolean>"));
                    }
                }
            }
            case "sleep" -> {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " This troll cannot be used by the console!"));
                } else {
                    Location loc = ((Player) sender).getLocation();
                    target.sleep(loc, true);
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sleep" + t + "!"));
                }
            }
            case "close" -> {
                target.closeInventory();
                sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " close" + t + "!"));
            }
            case "closespam" -> {
                if (args.length == 2) {
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> closespam <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setMetadata("closespam", new FixedMetadataValue(plugin, true));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " closespam" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setMetadata("closespam", new FixedMetadataValue(plugin, false));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " closespam" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> closespam <boolean>"));
                    }
                }
            }
            case "glow" -> {
                if (args.length == 2) {
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> glow <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setGlowing(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " glow" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setGlowing(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " glow" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> glow <boolean>"));
                    }
                }
            }
            case "pickup" -> {
                if (args.length == 2) {
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> pickup <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " pickup" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " pickup" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> pickup <boolean>"));
                    }
                }
            }
            case "freeze" -> {
                if (args.length == 2) {
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> freeze <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setMetadata("freeze", new FixedMetadataValue(plugin, true));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " freeze" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setMetadata("freeze", new FixedMetadataValue(plugin, false));
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " freeze" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> freeze <boolean>"));
                    }
                }
            }
            case "fakeop" -> {
                target.sendMessage(Tools.chat("&7&o[") + sender.getName() + ": Made " + target.getName() + " a server operator]");
                sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " fakeop" + t + "!"));
            }
            case "flip" -> {
                final Location targetLocation = target.getLocation().clone();
                float newYaw;
                newYaw = targetLocation.getYaw() + 180.0f;
                while (newYaw > 360.0f) {
                    newYaw -= 360.0f;
                }
                targetLocation.setYaw(newYaw);
                target.teleport(targetLocation);
                sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " flip" + t + "!"));
            }
            case "thor" -> {
                target.getWorld().strikeLightning(target.getLocation());
                sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been struck by lightning!"));
            }
            case "fakedemo" -> {
                EntityPlayer target_entity = ((CraftPlayer) target).getHandle();
                final PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.f, 0.0F);
                target_entity.c.b(packet);
                sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " fakedemo" + t + "!"));
            }
            default ->
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> <troll>"));
        }

        return false;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        List<String> list = new ArrayList<>();

        switch (args.length) {
            case 1 -> list = CommandHandler.getPlayerList();
            case 2 -> {
                list.add("sneak");
                list.add("sleep");
                list.add("close");
                list.add("closespam");
                list.add("glow");
                list.add("pickup");
                list.add("freeze");
                list.add("fakeop");
                list.add("flip");
                list.add("thor");
                list.add("fakedemo");
            }
            case 3 -> {
                list.add("true");
                list.add("false");
            }
        }

        if (!list.isEmpty()) {
            return list;
        }

        return null;

    }
}