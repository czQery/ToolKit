package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Crawl implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");

    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player)) {
            Tools.log(b+"["+n+"SERVER"+b+"]"+t+" This command cannot be used by the console!");
        } else {
            Player p = (Player) sender;
            if(!p.hasPermission("toolkit.crawl")) {
                p.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
            } else {
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
                if (p.getMetadata("crawl").toString() == "[]") {
                    if (p.isOnGround()) {
                        if (p.getLocation().getY() % 1 <= 0.25) {
                            p.setMetadata("crawl", new FixedMetadataValue(plugin, true));
                            p.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" Crawl mode has been turned &aON"+t+"!"));
                            if (loc.getBlock().isEmpty()) {
                                loc.getBlock().setType(Material.BARRIER);
                            }
                        } else {
                            p.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" You must stand on a full block or lower than slab!"));
                        }
                    } else {
                        p.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" You must stand on a block!"));
                    }
                } else {
                    if (p.getMetadata("crawl").get(0).asBoolean()) {
                        p.setMetadata("crawl", new FixedMetadataValue(plugin, false));
                        p.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" Crawl mode has been turned &cOFF"+t+"!"));
                        Scripts.bCheck(p);
                        if (loc.getBlock().getType() == Material.BARRIER){loc.getBlock().setType(Material.AIR);}
                    } else {
                        if (p.isOnGround()) {
                            if (p.getLocation().getY() % 1 <= 0.25) {
                                p.setMetadata("crawl", new FixedMetadataValue(plugin, true));
                                p.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" Crawl mode has been turned &aON"+t+"!"));
                                if (loc.getBlock().isEmpty()) {
                                    loc.getBlock().setType(Material.BARRIER);
                                }
                            } else {
                                p.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" You must stand on a full block or lower than slab!"));
                            }
                        } else {
                            p.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" You must stand on a block!"));
                        }
                    }
                }
            }
        }
        return false;
    }
}
