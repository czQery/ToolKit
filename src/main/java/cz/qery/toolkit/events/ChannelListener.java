package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ChannelListener implements PluginMessageListener {

    static Plugin plugin = Main.getPlugin(Main.class);
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player p, byte[] data) {
        String b = Main.colors.get("b");
        String n = Main.colors.get("n");
        String t = Main.colors.get("t");
        String h = Main.colors.get("h");

        data = Arrays.copyOfRange(data, 1, data.length);
        String msg = new String(data, StandardCharsets.US_ASCII);


        if ("minecraft:brand".equals(channel)) {
            if (!msg.toLowerCase().contains("vannila") && !msg.toLowerCase().contains("lunarclient") && !msg.toLowerCase().contains("fabric") && !msg.toLowerCase().contains("feather") && !msg.toLowerCase().contains("forge")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, msg));
                Tools.log(b+"["+n+"SERVER"+b+"] "+h+p.getName()+t+" client "+h+msg);
            }
        } else {
            Tools.log(b+"["+n+"SERVER"+b+"] "+h+p.getName()+t+ " sent "+h+channel+" "+t+msg);
        }
    }
}
