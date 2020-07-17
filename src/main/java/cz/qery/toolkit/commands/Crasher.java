package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Crasher implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p =(Player) sender;

        Plugin plugin = Main.getPlugin(Main.class);
        String b = plugin.getConfig().getString("color.bracket");
        String n = plugin.getConfig().getString("color.name");
        String t = plugin.getConfig().getString("color.text");
        String h = plugin.getConfig().getString("color.highlight");

        if(!p.hasPermission("toolkit.crasher")){
            p.sendMessage(Utils.chat(b+"["+n+"SERVER"+b+"]"+t+" You're not allowed to do this!"));
        } else {
            if(args.length == 0) {
                p.sendMessage(Utils.chat(b+"["+n+"CRASHER"+b+"]"+t+" Please use "+h+"/crasher <player>"));
            } else {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    p.sendMessage(Utils.chat(b+"["+n+"CRASHER"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                } else if(target.hasPermission("toolkit.crasher.bypass")){
                    p.sendMessage(Utils.chat(b+"["+n+"CRASHER"+b+"]"+t+" You cannot crash this player!"));
                } else {
                    Location loc = target.getLocation();
                    target.spawnParticle(Particle.EXPLOSION_HUGE, loc, Integer.MAX_VALUE);
                    p.sendMessage(Utils.chat(b+"["+n+"CRASHER"+b+"]"+t+" Player "+h+target.getName()+t+" has been crashed!"));
                    Utils.delay(3000);
                    target.kickPlayer("");
                }
            }
        }

        return false;
    }
}
