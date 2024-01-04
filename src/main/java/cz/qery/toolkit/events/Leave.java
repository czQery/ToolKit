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
import org.bukkit.plugin.Plugin;

public class Leave implements Listener {
    static Plugin plugin = Main.getPlugin(Main.class);

    public Leave(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (plugin.getConfig().getBoolean("leave.alert") && !Vnsh.Enabled(p)) {
            e.quitMessage(Component.text(Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%", p.getName())));
        } else {
            e.quitMessage(null);
        }

        Scripts.cleanup(p);
    }
}
