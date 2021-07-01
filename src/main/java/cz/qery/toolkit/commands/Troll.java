package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Troll implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");


    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player)) {
            Tools.log(b + "[" + n + "SERVER" + b + "]" + t + " This command cannot be used by the console!");
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission("toolkit.troll")) {
                p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " You're not allowed to do this!"));
            } else {
                if (args.length < 2) {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("help")) {
                            p.sendMessage(Tools.chat(b + "-------[" + n + "TROLL" + b + "]-------"));
                            p.sendMessage(Tools.chat(b + "- " + t + "Sneak"));
                            p.sendMessage(Tools.chat(b + "- " + t + "Sleep"));
                            p.sendMessage(Tools.chat(b + "- " + t + "Close"));
                            p.sendMessage(Tools.chat(b + "- " + t + "Glow"));
                            p.sendMessage(Tools.chat(b + "- " + t + "PickUp"));
                            p.sendMessage(Tools.chat(b + "- " + t + "Freeze"));
                            p.sendMessage(Tools.chat(b + "- " + t + "FakeOP"));
                            p.sendMessage(Tools.chat(b + "- " + t + "Flip"));
                            p.sendMessage(Tools.chat(b + "----------------------"));
                        } else {
                            p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> <troll>"));
                        }
                    } else {
                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> <troll>"));
                    }
                } else {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                    } else if (target.hasPermission("toolkit.troll.bypass")) {
                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " You cannot troll this player!"));
                    } else {
                        //SNEAK
                        switch (args[1].toLowerCase()) {
                            case "sneak":
                                if (args.length == 2) {
                                    p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> sneak <boolean>"));
                                } else {
                                    if (args[2].equalsIgnoreCase("true")) {
                                        target.setSneaking(Boolean.parseBoolean(args[2]));
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sneak" + t + " to&a true" + t + "!"));
                                    } else if (args[2].equalsIgnoreCase("false")) {
                                        target.setSneaking(Boolean.parseBoolean(args[2]));
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sneak" + t + " to&c false" + t + "!"));
                                    } else {
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> sneak <boolean>"));
                                    }
                                }
                                break;
                            case "sleep":
                                Location loc = p.getLocation();
                                target.sleep(loc, true);
                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sleep" + t + "!"));
                                break;
                            case "close":
                                target.closeInventory();
                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " close" + t + "!"));
                                break;
                            case "glow":
                                if (args.length == 2) {
                                    p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> glow <boolean>"));
                                } else {
                                    if (args[2].equalsIgnoreCase("true")) {
                                        target.setGlowing(Boolean.parseBoolean(args[2]));
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " glow" + t + " to&a true" + t + "!"));
                                    } else if (args[2].equalsIgnoreCase("false")) {
                                        target.setGlowing(Boolean.parseBoolean(args[2]));
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " glow" + t + " to&c false" + t + "!"));
                                    } else {
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> glow <boolean>"));
                                    }
                                }
                                break;
                            case "pickup":
                                if (args.length == 2) {
                                    p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> pickup <boolean>"));
                                } else {
                                    if (args[2].equalsIgnoreCase("true")) {
                                        target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " pickup" + t + " to&a true" + t + "!"));
                                    } else if (args[2].equalsIgnoreCase("false")) {
                                        target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " pickup" + t + " to&c false" + t + "!"));
                                    } else {
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> pickup <boolean>"));
                                    }
                                }
                                break;
                            case "freeze":
                                if (args.length == 2) {
                                    p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> freeze <boolean>"));
                                } else {
                                    if (args[2].equalsIgnoreCase("true")) {
                                        target.setMetadata("freeze", new FixedMetadataValue(plugin, true));
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " freeze" + t + " to&a true" + t + "!"));
                                    } else if (args[2].equalsIgnoreCase("false")) {
                                        target.setMetadata("freeze", new FixedMetadataValue(plugin, false));
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " freeze" + t + " to&c false" + t + "!"));
                                    } else {
                                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> freeze <boolean>"));
                                    }
                                }
                                break;
                            case "fakeop":
                                target.sendMessage(Tools.chat("&7&o[") + p.getName() + ": Made " + target.getName() + " a server operator]");
                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " fakeop" + t + "!"));
                                break;
                            case "flip":
                                final Location targetLocation = target.getLocation().clone();
                                float newYaw;
                                newYaw = targetLocation.getYaw() + 180.0f;
                                while (newYaw > 360.0f) {
                                    newYaw -= 360.0f;
                                }
                                targetLocation.setYaw(newYaw);
                                target.teleport(targetLocation);
                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " flip" + t + "!"));
                                break;
                            case "thor":
                                target.getWorld().strikeLightning(target.getLocation());
                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been struck by lightning!"));
                                break;
                            case "fakedemo-WIP":
                                /*
                                final CraftPlayer craftPlayer = (CraftPlayer) target;
                                final PacketPlayOutGameStateChange welcomePacket = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.a, 0.0F);
                                final PacketPlayOutGameStateChange moveHelpPacket = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.a, 101F);
                                final PacketPlayOutGameStateChange jumpHelpPacket = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.a, 102F);
                                final PacketPlayOutGameStateChange inventoryControl = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.a, 103F);

                                craftPlayer.getHandle().

                                craftPlayer.getHandle().playerConnection.sendPacket(inventoryControl);
                                craftPlayer.getHandle().playerConnection.sendPacket(jumpHelpPacket);
                                craftPlayer.getHandle().playerConnection.sendPacket(welcomePacket);
                                craftPlayer.getHandle().playerConnection.sendPacket(moveHelpPacket);
                                 */


                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " fakedemo" + t + "!"));
                                break;
                            default:
                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> <troll>"));
                                break;
                        }
                    }
                }
            }
        }
        return false;
    }
}
