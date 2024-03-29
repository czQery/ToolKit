package cz.qery.toolkit.events;

import cz.qery.toolkit.commands.CommandBlock;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

public class Command implements Listener {
    static Plugin plugin = Main.getPlugin(Main.class);

    public Command(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("toolkit.commandblock.bypass")) {
            for (CommandBlock cmdb : CommandBlock.cmdlist) {
                if (e.getMessage().toLowerCase().startsWith("/"+cmdb.name())) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
                }
            }
        }
    }
}
