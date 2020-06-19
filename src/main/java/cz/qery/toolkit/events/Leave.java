package cz.qery.toolkit.events;

import cz.qery.toolkit.ToolKit;
import cz.qery.toolkit.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {
    private ToolKit plugin;

    public Leave(ToolKit plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (plugin.getConfig().getBoolean("welcome")) {
            e.setQuitMessage(Utils.chat("&8[&cSERVER&8]&6 "+p.getName()+"&7 disconnected!"));
        } else {
            e.setQuitMessage("");
        }
    }
}
