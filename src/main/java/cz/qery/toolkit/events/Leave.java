package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import cz.qery.toolkit.helper.Vanish;
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

        if (plugin.getConfig().getBoolean("leave.alert") && !Vanish.Enabled(p)) {
            e.quitMessage(Component.text(Other.Tools.chat(plugin.getConfig().getString("leave.message")).replace("%player%", p.getName())));
        } else {
            e.quitMessage(null);
        }

        Other.cleanup(p);
    }
}
