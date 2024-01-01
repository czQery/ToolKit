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
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");
    static String t = Main.colors.get("t");
    static String h = Main.colors.get("h");

    public Command(Main plugin) {
        Command.plugin = plugin;

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
        return;
    }
}
