package cz.qery.toolkit.commands;

import cz.qery.toolkit.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Troll implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if(!p.hasPermission("toolkit.troll")) {
            return false;
        } else {
            if(args.length < 2) {
                if(args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage(Utils.chat("&8-------[&cTROLL&8]-------"));
                        p.sendMessage(Utils.chat("&8- &7Sneak"));
                        p.sendMessage(Utils.chat("&8- &7Sleep"));
                        p.sendMessage(Utils.chat("&8- &7Close"));
                        p.sendMessage(Utils.chat("&8- &7Glow"));
                        p.sendMessage(Utils.chat("&8- &7PickUp"));
                        p.sendMessage(Utils.chat("&8- &7Freeze"));
                        p.sendMessage(Utils.chat("&8- &7FakeOP"));
                        p.sendMessage(Utils.chat("&8- &7Flip"));
                        p.sendMessage(Utils.chat("&8---------------------"));
                    } else {
                        p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> <troll>"));
                    }
                } else {
                    p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> <troll>"));
                }
            } else {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + args[0] + Utils.chat(" &7is not online!"));
                } else if (target.hasPermission("toolkit.bypass")){
                    p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 You cannot troll this player!"));
                } else {
                    //SNEAK
                    if (args[1].equalsIgnoreCase("sneak")) {
                        if (args.length == 2) {
                            p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> sneak <boolean>"));
                        } else {
                            if (args[2].equalsIgnoreCase("true")) {
                                target.setSneaking(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 sneak&7 to&a true&7!"));
                            } else if (args[2].equalsIgnoreCase("false")){
                                target.setSneaking(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 sneak&7 to&c false&7!"));
                            } else {
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> sneak <boolean>"));
                            }
                        }
                    }
                    //SLEEP
                    else if (args[1].equalsIgnoreCase("sleep")) {
                        Location loc = p.getLocation();
                        target.sleep(loc, true);
                        p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 sleep&7!"));
                    }
                    //CLOSE
                    else if (args[1].equalsIgnoreCase("close")) {
                        target.closeInventory();
                        p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 close&7!"));
                    }
                    //GLOW
                    else if (args[1].equalsIgnoreCase("glow")) {
                        if (args.length == 2) {
                            p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> glow <boolean>"));
                        } else {
                            if (args[2].equalsIgnoreCase("true")) {
                                target.setGlowing(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 glow&7 to&a true&7!"));
                            } else if (args[2].equalsIgnoreCase("false")){
                                target.setGlowing(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 glow&7 to&c false&7!"));
                            } else {
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> glow <boolean>"));
                            }
                        }
                    }
                    //PICKUP
                    else if (args[1].equalsIgnoreCase("pickup")) {
                        if (args.length == 2) {
                            p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> pickup <boolean>"));
                        } else {
                            if (args[2].equalsIgnoreCase("true")) {
                                target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 pickup&7 to&a true&7!"));
                            } else if (args[2].equalsIgnoreCase("false")){
                                target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 pickup&7 to&c false&7!"));
                            } else {
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> pickup <boolean>"));
                            }
                        }
                    }
                    //FREEZE
                    else if (args[1].equalsIgnoreCase("freeze")) {
                        if (args.length == 2) {
                            p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> freeze <boolean>"));
                        } else {
                            if (args[2].equalsIgnoreCase("true")) {
                                target.removePotionEffect(PotionEffectType.SLOW);
                                target.removePotionEffect(PotionEffectType.JUMP);
                                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 255, true));
                                target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 128, true));
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 freeze&7 to&a true&7!"));
                            } else if (args[2].equalsIgnoreCase("false")){
                                target.removePotionEffect(PotionEffectType.SLOW);
                                target.removePotionEffect(PotionEffectType.JUMP);
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 freeze&7 to&c false&7!"));
                            } else {
                                p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> freeze <boolean>"));
                            }
                        }
                    }
                    //FAKEOP
                    else if (args[1].equalsIgnoreCase("fakeop")) {
                        target.sendMessage(Utils.chat("&7&o[")+p.getName()+": Made "+target.getName()+" a server operator]");
                        p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 fakeop&7!"));
                    }
                    //FLIP
                    else if (args[1].equalsIgnoreCase("flip")) {
                        final Location targetLocation = target.getLocation().clone();
                        float newYaw;
                        for (newYaw = targetLocation.getYaw() + 180.0f; newYaw < 0.0f; newYaw += 360.0f) {}
                        while (newYaw > 360.0f) {
                            newYaw -= 360.0f;
                        }
                        targetLocation.setYaw(newYaw);
                        target.teleport(targetLocation);
                        p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been set&6 rotate&7!"));
                    }
                    else {
                        p.sendMessage(Utils.chat("&8[&cTROLL&8]&7 Please use &6/troll <player> <troll>"));
                    }
                }
            }
        }
        return false;
    }
}
