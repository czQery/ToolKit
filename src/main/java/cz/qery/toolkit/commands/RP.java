package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RP implements CommandExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Tools.log(b + "[" + n + "SERVER" + b + "]" + t + " This command cannot be used by the console!");
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission("toolkit.rp")) {
                p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " You're not allowed to do this!"));
            } else {
                if(args.length < 2) {
                    p.sendMessage(Tools.chat(b+"["+n+"RP"+b+"]"+t+" Please use "+h+"/rp <player> <url>"));
                } else {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Player target = Bukkit.getServer().getPlayer(args[0]);
                            if(target == null){
                                p.sendMessage(Tools.chat(b+"["+n+"SERVER"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                                return;
                            }
                            String url = args[1];

                            target.setResourcePack(url);
                            target.sendMessage(Tools.chat(b + "[" + n + "RP" + b + "]" + t + " Player " + h + p.getName() + t + " has sent you" + h + " resource pack" + t + "!"));
                            p.sendMessage(Tools.chat(b + "[" + n + "RP" + b + "]" + t + " Player " + h + target.getName() + t + " has been set" + h + " resource pack" + t + "!"));
                        }
                    });
                }
            }
        }
        return false;
    }
}
