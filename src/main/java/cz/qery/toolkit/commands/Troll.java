package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import cz.qery.toolkit.loader.Commands;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Troll implements TabExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = Main.colors.get("b");
    String n = Main.colors.get("n");
    String t = Main.colors.get("t");
    String h = Main.colors.get("h");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        Player target;

        if (!Commands.hasPermission(sender, cmd)) {
            return false;
        }

        if (args.length > 1) {
            target = Commands.getPlayer(sender, args[0]);
            if (target == null) {
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                return false;
            }
        } else {
            sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> <troll>"));
            return false;
        }

        if (Commands.hasPermissionBypass(target, cmd)) {
            sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " You cannot troll this player!"));
            return false;
        }

        //SNEAK
        switch (args[1].toLowerCase()) {
            case "help" -> {
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "Sneak"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "Sleep (it must be night, and you must stand on the bed)"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "Close"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "CloseSpam (anti-leave)"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "Glow"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "PickUp"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "Freeze"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "FakeOP"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "Flip"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "Thor"));
                sender.sendMessage(Other.Tools.chat(b + "- " + t + "Fakedemo"));
            }
            case "sneak" -> {
                if (args.length == 2) {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> sneak <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setSneaking(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sneak" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setSneaking(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sneak" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> sneak <boolean>"));
                    }
                }
            }
            case "sleep" -> {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " This troll cannot be used by the console!"));
                } else {
                    Location loc = ((Player) sender).getLocation();
                    target.sleep(loc, true);
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sleep" + t + "!"));
                }
            }
            case "close" -> {
                target.closeInventory();
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " close" + t + "!"));
            }
            case "closespam" -> {
                if (args.length == 2) {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> closespam <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setMetadata("closespam", new FixedMetadataValue(plugin, true));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " closespam" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setMetadata("closespam", new FixedMetadataValue(plugin, false));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " closespam" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> closespam <boolean>"));
                    }
                }
            }
            case "glow" -> {
                if (args.length == 2) {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> glow <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setGlowing(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " glow" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setGlowing(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " glow" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> glow <boolean>"));
                    }
                }
            }
            case "pickup" -> {
                if (args.length == 2) {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> pickup <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " pickup" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " pickup" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> pickup <boolean>"));
                    }
                }
            }
            case "freeze" -> {
                if (args.length == 2) {
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> freeze <boolean>"));
                } else {
                    if (args[2].equalsIgnoreCase("true")) {
                        target.setMetadata("freeze", new FixedMetadataValue(plugin, true));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " freeze" + t + " to&a true" + t + "!"));
                    } else if (args[2].equalsIgnoreCase("false")) {
                        target.setMetadata("freeze", new FixedMetadataValue(plugin, false));
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " freeze" + t + " to&c false" + t + "!"));
                    } else {
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> freeze <boolean>"));
                    }
                }
            }
            case "fakeop" -> {
                target.sendMessage(Other.Tools.chat("&7&o[") + sender.getName() + ": Made " + target.getName() + " a server operator]");
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " fakeop" + t + "!"));
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
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " flip" + t + "!"));
            }
            case "thor" -> {
                target.getWorld().strikeLightning(target.getLocation());
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been struck by lightning!"));
            }
            case "fakedemo" -> {
                ServerPlayer target_entity = ((CraftPlayer) target).getHandle();
                final ClientboundGameEventPacket packet = new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 0.0F);
                target_entity.connection.send(packet);
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " fakedemo" + t + "!"));
            }
            default ->
                    sender.sendMessage(Other.Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> <troll>"));
        }

        return false;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        List<String> list = new ArrayList<>();

        switch (args.length) {
            case 1 -> list = Commands.getPlayerList();
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