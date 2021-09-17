package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class ChannelRegister implements Listener {
    private Main plugin;

    public ChannelRegister(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRegisterChannel(PlayerRegisterChannelEvent event) {
        Player p = event.getPlayer();
        String h = plugin.getConfig().getString("color.highlight");
        String b = plugin.getConfig().getString("color.bracket");
        String n = plugin.getConfig().getString("color.name");
        String t = plugin.getConfig().getString("color.text");

        if ("fml:handshake".equals(event.getChannel())) {
            event.getPlayer().setMetadata("client", new FixedMetadataValue(plugin, "Forge"));

            /*
            event.getPlayer().sendPluginMessage(plugin, "fml:handshake", new byte[]{-2, 0});
            final byte[] array = new byte[6];
            array[1] = 2;
            p.sendPluginMessage(plugin, "fml:handshake", array);
            final byte[] array2 = new byte[5];
            array2[0] = 2;
            p.sendPluginMessage(plugin, "fml:handshake", array2);

            p.sendPluginMessage(plugin, "fml:handshake", new byte[] {0,2,0});
            p.sendPluginMessage(plugin, "fml:handshake", new byte[] {2,0,0,0});
            p.sendPluginMessage(plugin, "fml:handshake", new byte[] {0, 2, 0, 0, 0, 0});
            p.sendPluginMessage(plugin, "fml:handshake", new byte[] {2, 0, 0, 0, 0});
            */
        } else if ("lunarclient:pm".equals(event.getChannel())) {
            p.setMetadata("client", new FixedMetadataValue(plugin, "LunarClient"));
        } else {
            Tools.log(b + "[" + n + "SERVER" + b + "] " + h + event.getPlayer().getName() + t + " registered channel " + h + event.getChannel());
        }
    }
}
