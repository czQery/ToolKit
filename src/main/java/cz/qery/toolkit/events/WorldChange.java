package cz.qery.toolkit.events;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketUpdateWorld;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChange implements Listener {
    private Main plugin;

    public WorldChange(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        updateWorld(event.getPlayer());
    }

    private void updateWorld(Player p) {
        String worldIdentifier = p.getWorld().getUID().toString();
        Tools.sendLunarPacket(p, new LCPacketUpdateWorld(worldIdentifier));
    }
}
