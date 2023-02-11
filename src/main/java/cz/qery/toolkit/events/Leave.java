package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import cz.qery.toolkit.Vnsh;
import net.kyori.adventure.text.Component;
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
    @SuppressWarnings("deprecation")
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (plugin.getConfig().getBoolean("leave.alert") && !Vnsh.Enabled(p)) {
            if (Tools.isPaper) {
                e.quitMessage(Component.text(Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%",p.getName())));
            } else {
                e.setQuitMessage(Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%",p.getName()));
            }
        } else {
            e.quitMessage(null);
        }

        Scripts.cleanup(p);
    }
}
