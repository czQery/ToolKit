package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class Crawl implements CommandExecutor {
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        Plugin plugin = Main.getPlugin(Main.class);
        String b = plugin.getConfig().getString("color.bracket");
        String n = plugin.getConfig().getString("color.name");
        String t = plugin.getConfig().getString("color.text");

        if(!p.hasPermission("toolkit.crawl")) {
            p.sendMessage(Utils.chat(b+"["+n+"SERVER"+b+"]"+t+" You're not allowed to do this!"));
        } else {
            Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
            if (p.getMetadata("crawl").toString() == "[]") {
                if (p.isOnGround()) {
                    if (p.getLocation().getY() % 1 <= 0.25) {
                        p.setMetadata("crawl", new FixedMetadataValue(plugin, true));
                        p.sendMessage(Utils.chat(b+"["+n+"CRAWL"+b+"]"+t+" Crawl mode has been turned &aON"+t+"!"));
                        if (loc.getBlock().isEmpty()) {
                            loc.getBlock().setType(Material.BARRIER);
                        }
                    } else {
                        p.sendMessage(Utils.chat(b+"["+n+"CRAWL"+b+"]"+t+" You must stand on a full block or lower than slab!"));
                    }
                } else {
                    p.sendMessage(Utils.chat(b+"["+n+"CRAWL"+b+"]"+t+" You must stand on a block!"));
                }
            } else {
                if (p.getMetadata("crawl").get(0).asBoolean()) {
                    p.setMetadata("crawl", new FixedMetadataValue(plugin, false));
                    p.sendMessage(Utils.chat(b+"["+n+"CRAWL"+b+"]"+t+" Crawl mode has been turned &cOFF"+t+"!"));
                    Scripts.bCheck(p);
                    if (loc.getBlock().getType() == Material.BARRIER){loc.getBlock().setType(Material.AIR);}
                } else {
                    if (p.isOnGround()) {
                        if (p.getLocation().getY() % 1 <= 0.25) {
                            p.setMetadata("crawl", new FixedMetadataValue(plugin, true));
                            p.sendMessage(Utils.chat(b+"["+n+"CRAWL"+b+"]"+t+" Crawl mode has been turned &aON"+t+"!"));
                            if (loc.getBlock().isEmpty()) {
                                loc.getBlock().setType(Material.BARRIER);
                            }
                        } else {
                            p.sendMessage(Utils.chat(b+"["+n+"CRAWL"+b+"]"+t+" You must stand on a full block or lower than slab!"));
                        }
                    } else {
                        p.sendMessage(Utils.chat(b+"["+n+"CRAWL"+b+"]"+t+" You must stand on a block!"));
                    }
                }
            }
        }
        return false;
    }
}
