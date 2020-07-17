package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class Troll implements CommandExecutor {
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        Plugin plugin = Main.getPlugin(Main.class);
        String b = plugin.getConfig().getString("color.bracket");
        String n = plugin.getConfig().getString("color.name");
        String t = plugin.getConfig().getString("color.text");
        String h = plugin.getConfig().getString("color.highlight");

        if(!p.hasPermission("toolkit.troll")) {
            p.sendMessage(Utils.chat(b+"["+n+"SERVER"+b+"]"+t+" You're not allowed to do this!"));
        } else {
            if(args.length < 2) {
                if(args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage(Utils.chat(b+"-------["+n+"TROLL"+b+"]-------"));
                        p.sendMessage(Utils.chat(b+"- "+t+"Sneak"));
                        p.sendMessage(Utils.chat(b+"- "+t+"Sleep"));
                        p.sendMessage(Utils.chat(b+"- "+t+"Close"));
                        p.sendMessage(Utils.chat(b+"- "+t+"Glow"));
                        p.sendMessage(Utils.chat(b+"- "+t+"PickUp"));
                        p.sendMessage(Utils.chat(b+"- "+t+"Freeze"));
                        p.sendMessage(Utils.chat(b+"- "+t+"FakeOP"));
                        p.sendMessage(Utils.chat(b+"- "+t+"Flip"));
                        p.sendMessage(Utils.chat(b+"----------------------"));
                    } else {
                        p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> <troll>"));
                    }
                } else {
                    p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> <troll>"));
                }
            } else {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                } else if (target.hasPermission("toolkit.troll.bypass")){
                    p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" You cannot troll this player!"));
                } else {
                    //SNEAK
                    if (args[1].equalsIgnoreCase("sneak")) {
                        if (args.length == 2) {
                            p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> sneak <boolean>"));
                        } else {
                            if (args[2].equalsIgnoreCase("true")) {
                                target.setSneaking(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" sneak"+t+" to&a true"+t+"!"));
                            } else if (args[2].equalsIgnoreCase("false")){
                                target.setSneaking(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" sneak"+t+" to&c false"+t+"!"));
                            } else {
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> sneak <boolean>"));
                            }
                        }
                    }
                    //SLEEP
                    else if (args[1].equalsIgnoreCase("sleep")) {
                        Location loc = p.getLocation();
                        target.sleep(loc, true);
                        p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" sleep"+t+"!"));
                    }
                    //CLOSE
                    else if (args[1].equalsIgnoreCase("close")) {
                        target.closeInventory();
                        p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" close"+t+"!"));
                    }
                    //GLOW
                    else if (args[1].equalsIgnoreCase("glow")) {
                        if (args.length == 2) {
                            p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> glow <boolean>"));
                        } else {
                            if (args[2].equalsIgnoreCase("true")) {
                                target.setGlowing(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" glow"+t+" to&a true"+t+"!"));
                            } else if (args[2].equalsIgnoreCase("false")){
                                target.setGlowing(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" glow"+t+" to&c false"+t+"!"));
                            } else {
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> glow <boolean>"));
                            }
                        }
                    }
                    //PICKUP
                    else if (args[1].equalsIgnoreCase("pickup")) {
                        if (args.length == 2) {
                            p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> pickup <boolean>"));
                        } else {
                            if (args[2].equalsIgnoreCase("true")) {
                                target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" pickup"+t+" to&a true"+t+"!"));
                            } else if (args[2].equalsIgnoreCase("false")){
                                target.setCanPickupItems(Boolean.parseBoolean(args[2]));
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" pickup"+t+" to&c false"+t+"!"));
                            } else {
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> pickup <boolean>"));
                            }
                        }
                    }
                    //FREEZE
                    else if (args[1].equalsIgnoreCase("freeze")) {
                        if (args.length == 2) {
                            p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> freeze <boolean>"));
                        } else {
                            if (args[2].equalsIgnoreCase("true")) {
                                target.setMetadata("freeze", new FixedMetadataValue(plugin, true));
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" freeze"+t+" to&a true"+t+"!"));
                            } else if (args[2].equalsIgnoreCase("false")){
                                target.setMetadata("freeze", new FixedMetadataValue(plugin, false));
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" freeze"+t+" to&c false"+t+"!"));
                            } else {
                                p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> freeze <boolean>"));
                            }
                        }
                    }
                    //FAKEOP
                    else if (args[1].equalsIgnoreCase("fakeop")) {
                        target.sendMessage(Utils.chat("&7&o[")+p.getName()+": Made "+target.getName()+" a server operator]");
                        p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" fakeop"+t+"!"));
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
                        p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Player "+h+target.getName()+t+ " has been set"+h+" flip"+t+"!"));
                    }
                    else {
                        p.sendMessage(Utils.chat(b+"["+n+"TROLL"+b+"]"+t+" Please use "+h+"/troll <player> <troll>"));
                    }
                }
            }
        }
        return false;
    }
}
