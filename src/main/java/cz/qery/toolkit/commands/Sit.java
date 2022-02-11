package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("deprecation")
public class Sit implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Tools.log(b + "[" + n + "SERVER" + b + "]" + t + " This command cannot be used by the console!");
        } else {
            Player p = (Player) sender;

            if (!p.hasPermission("toolkit.sit")) {
                p.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
            } else {
                if (p.getMetadata("sit").toString() == "[]") {
                    if (p.isOnGround()) {
                        int id = setAs(p);
                        p.setMetadata("sit", new FixedMetadataValue(plugin, id));
                        p.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Sit mode has been turned &aON" + t + "!"));
                    } else {
                        p.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " You must stand on a block!"));
                    }
                } else {
                    if (p.getMetadata("sit").get(0).asInt() != 0) {
                        p.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " You are already sitting!"));
                    } else {
                        if (p.isOnGround()) {
                            int id = setAs(p);
                            p.setMetadata("sit", new FixedMetadataValue(plugin, id));
                            p.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Sit mode has been turned &aON" + t + "!"));
                        } else {
                            p.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " You must stand on a block!"));
                        }
                    }
                }
            }
        }
        return false;

    }

    public static int setAs(Player p) {
        Location loc = new Location(p.getWorld(), p.getLocation().getBlockX() + 0.5, p.getLocation().getBlockY() - 0.96 + p.getLocation().getY() % 1, p.getLocation().getBlockZ() + 0.5);
        loc.setYaw(p.getLocation().getYaw());
        ArmorStand as = loc.getWorld().spawn(loc, ArmorStand.class);
        as.setBasePlate(false);
        as.setArms(false);
        as.setVisible(false);
        as.setCanPickupItems(false);
        as.setGravity(false);
        as.setSmall(true);
        as.setPassenger(p);
        int id = as.getEntityId();
        return id;
    }
}
