package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
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

        if (event.getChannel().startsWith("lunarclient:")) {
            Scripts.addTrueClient(p, "LunarClient");
        } else if (event.getChannel().startsWith("feather:")) {
            Scripts.addTrueClient(p, "FeatherClient");
        } else if (event.getChannel().startsWith("fabric:") || event.getChannel().startsWith("fabric-screen-handler-api")) {
            Scripts.addTrueClient(p, "Fabric");
        } else if (event.getChannel().startsWith("fml:")) {
            Scripts.addTrueClient(p, "Forge");
        } else {
            Tools.log(b + "[" + n + "SERVER" + b + "] " + h + event.getPlayer().getName() + t + " registered channel " + h + event.getChannel());
        }
    }
}
