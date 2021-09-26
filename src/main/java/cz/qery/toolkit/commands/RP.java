package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutResourcePackSend;
import net.minecraft.server.level.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;

public class RP implements CommandExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player target = null;

        if (!(sender instanceof Player)) {
            if (args.length > 1) {
                target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    sender.sendMessage(Tools.chat(b+"["+n+"RP"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                    return false;
                }
            } else {
                Tools.log(b+"["+n+"RP"+b+"]"+t+" Please use "+h+"/rp <player> <url>");
                return false;
            }
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission("toolkit.rp")) {
                p.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " You're not allowed to do this!"));
                return false;
            } else {
                if (args.length > 1) {
                    target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        p.sendMessage(Tools.chat(b + "[" + n + "RP" + b + "]" + t + " Player " + h + args[0] + t + " is not online!"));
                        return false;
                    }
                } else {
                    p.sendMessage(Tools.chat(b+"["+n+"RP"+b+"]"+t+" Please use "+h+"/rp <player> <url>"));
                    return false;
                }
            }
        }


        Player finalTarget = target;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (finalTarget == null) {
                    sender.sendMessage(Tools.chat(b + "[" + n + "SERVER" + b + "]" + t + " Player " + h + args[0]+ t + " is not online!"));
                    return;
                }
                String url = args[1];

                EntityPlayer target_entity = (EntityPlayer) ((CraftPlayer) finalTarget).getHandle();
                final PacketPlayOutResourcePackSend packet = new PacketPlayOutResourcePackSend(url, "", true, null);

                target_entity.b.sendPacket(packet);

                finalTarget.sendMessage(Tools.chat(b + "[" + n + "RP" + b + "]" + t + " Player " + h + sender.getName() + t + " has sent you" + h + " resource pack" + t + "!"));
                sender.sendMessage(Tools.chat(b + "[" + n + "RP" + b + "]" + t + " Player " + h + finalTarget.getName() + t + " has been set" + h + " resource pack" + t + "!"));
            }
        });
        return false;
    }
}
