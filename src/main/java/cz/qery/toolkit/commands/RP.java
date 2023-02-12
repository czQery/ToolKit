package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import net.minecraft.network.protocol.game.PacketPlayOutResourcePackSend;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class RP implements CommandExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        Player target;

        if ((sender instanceof Player) && !sender.hasPermission("toolkit.rp")) {
            sender.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
            return false;
        }

        if (args.length > 1) {
            target = Bukkit.getServer().getPlayer(args[0]);
            if(target == null){
                sender.sendMessage(Tools.chat(b+"["+n+"RP"+b+"]"+t+" Player "+h+args[0]+t+" is not online!"));
                return false;
            }
        } else {
            sender.sendMessage(Tools.chat(b+"["+n+"RP"+b+"]"+t+" Please use "+h+"/rp <player> <url>"));
            return false;
        }


        Player finalTarget = target;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String url = args[1];
            EntityPlayer target_entity = ((CraftPlayer) finalTarget).getHandle();
            final PacketPlayOutResourcePackSend packet = new PacketPlayOutResourcePackSend(url, "", true, null);

            target_entity.b.a(packet);

            finalTarget.sendMessage(Tools.chat(b + "[" + n + "RP" + b + "]" + t + " Player " + h + sender.getName() + t + " has sent you" + h + " resource pack" + t + "!"));
            sender.sendMessage(Tools.chat(b + "[" + n + "RP" + b + "]" + t + " Player " + h + finalTarget.getName() + t + " has been set" + h + " resource pack" + t + "!"));
        });
        return false;
    }
}
