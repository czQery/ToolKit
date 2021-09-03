package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PInfo implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            Tools.log(b+"["+n+"SERVER"+b+"]"+t+" This command cannot be used by the console!");
        } else {
            Player p = (Player) sender;

            if (!p.hasPermission("toolkit.pinfo")) {
                p.sendMessage(Tools.chat(b+"["+n+"SERVER"+b+"]"+t+" You're not allowed to do this!"));
            } else {
                if(args.length == 0) {
                    p.sendMessage(Tools.chat(b+"["+n+"PINFO"+b+"]"+t+" Please use "+h+"/pinfo <player>"));
                } else {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Player target = Bukkit.getServer().getPlayer(args[0]);
                            if(target == null){
                                p.sendMessage(Tools.chat(b+"["+n+"SERVER"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                                return;
                            }
                            String name = target.getName();
                            String ip = target.getAddress().getHostName();
                            String client;
                            if (target.getMetadata("client").toString() != "[]") {
                                client = target.getMetadata("client").get(0).asString();
                            } else {
                                client = "Vanilla";
                            }
                            p.sendMessage(Tools.chat(b+"-------["+n+"PlayerInfo"+b+"]-------"));
                            p.sendMessage(Tools.chat(b+"- "+t+"Username "+h+name));
                            p.sendMessage(Tools.chat(b+"- "+t+"Ip "+h+ip));
                            p.sendMessage(Tools.chat(b+"- "+t+"Client "+h+client));
                            p.sendMessage(Tools.chat(b+"------------------------"));

                        }
                    });
                }
            }
        }
        return false;
    }
}
