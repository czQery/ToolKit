package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {
    private final Main plugin;

    public Leave(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (plugin.getConfig().getBoolean("leave.alert")) {
            e.setQuitMessage(Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%",p.getName()));
        } else {
            e.setQuitMessage("");
        }
    }
}
