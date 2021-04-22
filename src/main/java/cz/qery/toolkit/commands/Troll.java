package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
            Tools.log(b+"["+n+"SERVER"+b+"]"+t+" This command cannot be used by the console!");
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
                                for (newYaw = targetLocation.getYaw() + 180.0f; newYaw < 0.0f; newYaw += 360.0f) {
                                }
                                while (newYaw > 360.0f) {
                                    newYaw -= 360.0f;
                                }
                                targetLocation.setYaw(newYaw);
                                target.teleport(targetLocation);
                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " flip" + t + "!"));
                                break;
                            case "fakedemo":
                                //! ISSUE #1041
                                // ? WAITING FOR FIX
                            /*
                            ProtocolManager pm = ProtocolLibrary.getProtocolManager();
                            PacketContainer packet = pm.createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
                            packet.getModifier().writeDefaults();
                            packet.getBytes().write(0, (byte) 5);
                            packet.getFloat().write(0,0F);
                            try {
                                pm.sendServerPacket(target, packet);
                            } catch (InvocationTargetException e) {
                            }
                             */

                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " fakedemo" + t + "!"));
                                break;
                            case "test":
                                //? I am trying to crash players windows by using this bug \\.\globalroot\device\condrv\kernelconnect (just file that crash windows when its open)
                                //? But its not working... If you reading this message you have probably enough skill to try it :)
                                target.setResourcePack("https://qery.cz/kek");

                                p.sendMessage(Tools.chat(b + "[" + n + "TROLL" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " test" + t + "!"));
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
