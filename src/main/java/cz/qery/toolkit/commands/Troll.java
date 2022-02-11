package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.server.level.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
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

        Player target = null;
        Player p = null;

        if (!(sender instanceof Player)) {
            if (args.length > 1) {
                target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    sender.sendMessage(Tools.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                    return false;
                }
            } else {
                Tools.log(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> <troll>");
                return false;
            }
        } else {
            p = ((Player) sender).getPlayer();
            if (!p.hasPermission("toolkit.troll")) {
                p.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
                return false;
            } else {
                if (args.length > 1) {
                    target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                        return false;
                    }
                } else {
                    p.sendMessage(Tools.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> <troll>"));
                    return false;
                }
            }
        }

        if (target.hasPermission("toolkit.troll.bypass")) {
            sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " You cannot troll this player!"));
        } else {
            //SNEAK
            switch (args[1].toLowerCase()) {
                case "help":
                    sender.sendMessage(Tools.chat(b + "-------[" + n + "TROLL" + b + "]-------"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "Sneak"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "Sleep (it must be night, and you must stand on the bed)"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "Close"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "Glow"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "PickUp"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "Freeze"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "FakeOP"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "Flip"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "Thor"));
                    sender.sendMessage(Tools.chat(b + "- " + t + "Fakedemo"));
                    sender.sendMessage(Tools.chat(b + "----------------------"));
                    break;
                case "sneak":
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
                    break;
                case "sleep":
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " This troll cannot be used by the console!"));
                        break;
                    } else {
                        Location loc = p.getLocation();
                        target.sleep(loc, true);
                        p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " sleep" + t + "!"));
                        break;
                    }
                case "close":
                    target.closeInventory();
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " close" + t + "!"));
                    break;
                case "glow":
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
                    break;
                case "pickup":
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
                    break;
                case "freeze":
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
                    break;
                case "fakeop":
                    target.sendMessage(Tools.chat("&7&o[") + sender.getName() + ": Made " + target.getName() + " a server operator]");
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " fakeop" + t + "!"));
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
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " flip" + t + "!"));
                    break;
                case "thor":
                    target.getWorld().strikeLightning(target.getLocation());
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been struck by lightning!"));
                    break;
                case "fakedemo":
                    EntityPlayer target_entity = ((CraftPlayer) target).getHandle();
                    final PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.f, 0.0F);
                    target_entity.b.a(packet);

                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " fakedemo" + t + "!"));
                    break;
                default:
                    sender.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Please use " + h + "/troll <player> <troll>"));
                    break;
            }
        }
        return false;
    }
}
