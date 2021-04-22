package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Crash implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            Tools.log(b+"["+n+"SERVER"+b+"]"+t+" This command cannot be used by the console!");
        } else {
            Player p =(Player) sender;
            if(!p.hasPermission("toolkit.crash")){
                p.sendMessage(Tools.chat(b+"["+n+"SERVER"+b+"]"+t+" You're not allowed to do this!"));
            } else {
                if(args.length == 0) {
                    p.sendMessage(Tools.chat(b+"["+n+"CRASH"+b+"]"+t+" Please use "+h+"/crash <player>"));
                } else {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if(target == null){
                        p.sendMessage(Tools.chat(b+"["+n+"CRASH"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                    } else if(target.hasPermission("toolkit.crash.bypass")){
                        p.sendMessage(Tools.chat(b+"["+n+"CRASH"+b+"]"+t+" You cannot crash this player!"));
                    } else {
                        Location loc = target.getLocation();
                        target.spawnParticle(Particle.EXPLOSION_HUGE, loc, Integer.MAX_VALUE);
                        p.sendMessage(Tools.chat(b+"["+n+"CRASH"+b+"]"+t+" Player "+h+target.getName()+t+" has been crashed!"));
                    }
                }
            }
        }

        return false;
    }
}