package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import cz.qery.toolkit.loader.Commands;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Sit implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = Main.colors.get("b");
    String n = Main.colors.get("n");
    String t = Main.colors.get("t");
    String h = Main.colors.get("h");

    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player target;
        String who;

        if (args.length > 0) {
            if (!Commands.hasPermissionOther(sender, cmd)) {
                return false;
            }

            target = Commands.getPlayer(sender, args[0]);
            if (target == null) {
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                return false;
            }
            who = "Player";
        } else {
            if (sender instanceof Player) {
                if (!Commands.hasPermission(sender, cmd)) {
                    return false;
                }

                target = (Player) sender;
                who = "You";
            } else {
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Please use " + h + "/sit <player>"));
                return false;
            }
        }

        if ((!Objects.equals(target.getMetadata("sit").toString(), "[]") && target.getMetadata("sit").getFirst().asInt() == 0) || Objects.equals(target.getMetadata("sit").toString(), "[]")) {
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

                String msgOn = Other.Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Sit mode has been turned &aON" + t + "!");
                target.sendMessage(msgOn);

                if (!target.getName().equals(sender.getName())) {
                    sender.sendMessage(msgOn);
                }
            } else {
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " " + who + " must stand on a block!"));
            }
        } else {
            Other.sCheck(target);

            if (!target.getName().equals(sender.getName())) {
                sender.sendMessage(Other.Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Sit mode has been turned &cOFF" + t + "!"));
            }
        }
        return false;
    }
}
