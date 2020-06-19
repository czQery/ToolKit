package cz.qery.toolkit.commands;

import cz.qery.toolkit.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Crasher implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p =(Player) sender;

        if(!p.hasPermission("toolkit.crasher")){
            return false;
        } else {
            if(args.length == 0) {
                p.sendMessage(Utils.chat("&8[&cCRASHER&8]&7 Please use &6/crasher <player>"));
            } else {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    p.sendMessage(Utils.chat("&8[&cCRASHER&8]&7 Player &6") + args[0] + Utils.chat(" &7is not online!"));
                } else if(target.getName().equalsIgnoreCase(p.getName())){
                    p.sendMessage(Utils.chat("&8[&cCRASHER&8]&7 You can't crash yourself!"));
                } else if(target.hasPermission("toolkit.bypass")){
                    p.sendMessage(Utils.chat("&8[&cCRASHER&8]&7 You cannot crash this player!"));
                } else {
                    Location loc = target.getLocation();
                    target.spawnParticle(Particle.EXPLOSION_HUGE, loc, Integer.MAX_VALUE);
                    Utils.delay(10000);
                    target.kickPlayer("");

                    p.sendMessage(Utils.chat("&8[&cCRASHER&8]&7 Player &6") + target.getName() + Utils.chat(" &7has been crashed!"));
                }
            }
        }

        return false;
    }
}
