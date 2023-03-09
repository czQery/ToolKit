package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Crawl implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player target;
        String who;

        if (args.length > 0) {
            if (!CommandHandler.hasPermissionOther(sender, cmd)) {
                return false;
            }

            target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Tools.chat(b + "[" + n + "CRAWL" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                return false;
            }
            who = "Player";
        } else {
            if (sender instanceof Player) {
                if (!CommandHandler.hasPermission(sender, cmd)) {
                    return false;
                }

                target = (Player) sender;
                who = "You";
            } else {
                sender.sendMessage(Tools.chat(b + "[" + n + "CRAWL" + b + "]" + t + " Please use " + h + "/crawl <player>"));
                return false;
            }
        }

        Location loc = new Location(target.getWorld(), target.getLocation().getBlockX(), target.getLocation().getBlockY()+1, target.getLocation().getBlockZ());

        if ((!Objects.equals(target.getMetadata("crawl").toString(), "[]") && !target.getMetadata("crawl").get(0).asBoolean()) || Objects.equals(target.getMetadata("crawl").toString(), "[]")) {
            if (target.isOnGround()) {
                if (target.getLocation().getY() % 1 <= 0.25) {
                    target.setMetadata("crawl", new FixedMetadataValue(plugin, true));
                    Scripts.bMap.put(target.getUniqueId(), new Location[]{loc});
                    sender.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" Crawl mode has been turned &aON"+t+"!"));
                    if (loc.getBlock().isEmpty()) {
                        loc.getBlock().setType(Material.BARRIER);
                    }
                } else {
                    sender.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" "+who+" must stand on a full block or lower than slab!"));
                }
            } else {
                sender.sendMessage(Tools.chat(b+"["+n+"CRAWL"+b+"]"+t+" "+who+" must stand on a block!"));
            }
        } else {
            Scripts.bDisable(target, false);
        }

        return false;
    }
}
