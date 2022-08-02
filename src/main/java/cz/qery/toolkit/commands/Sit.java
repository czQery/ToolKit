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
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Sit implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player target = null;
        String who = null;

        if (!(sender instanceof Player)) {
            if (args.length > 0) {
                target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                    return false;
                }
                who = "Player";
            } else {
                sender.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Please use " + h + "/sit <player>"));
                return false;
            }
        } else {
            if (args.length > 0) {
                if (!sender.hasPermission("toolkit.sit.other")) {
                    sender.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
                    return false;
                }
                target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                    return false;
                }
                who = "Player";
            } else {
                if (!sender.hasPermission("toolkit.sit")) {
                    sender.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
                    return false;
                }
                target = (Player) sender;
                who = "You";
            }
        }

        if ((target.getMetadata("sit").toString() != "[]" && target.getMetadata("sit").get(0).asInt() == 0) || target.getMetadata("sit").toString() == "[]") {
            if (target.isOnGround()) {
                Location loc = new Location(target.getWorld(), target.getLocation().getBlockX() + 0.5, target.getLocation().getBlockY() - 0.96 + target.getLocation().getY() % 1, target.getLocation().getBlockZ() + 0.5);
                loc.setYaw(target.getLocation().getYaw());
                ArmorStand as = loc.getWorld().spawn(loc, ArmorStand.class);
                as.setBasePlate(false);
                as.setArms(false);
                as.setVisible(false);
                as.setCanPickupItems(false);
                as.setGravity(false);
                as.setSmall(true);
                as.setPassenger(target);
                target.setMetadata("sit", new FixedMetadataValue(plugin, as.getEntityId()));
                sender.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Sit mode has been turned &aON" + t + "!"));
            } else {
                sender.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " " + who + " must stand on a block!"));
            }
        } else {
            Location loc = target.getLocation();
            for (Entity ent : loc.getChunk().getEntities()) {
                if (ent.getEntityId() == target.getMetadata("sit").get(0).asInt()) {
                    ent.remove();

                    target.teleport(loc.add(0, 1.7, 0));
                    target.setMetadata("sit", new FixedMetadataValue(plugin, 0));
                    sender.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Sit mode has been turned &cOFF" + t + "!"));
                }
            }
        }
        return false;
    }
}
