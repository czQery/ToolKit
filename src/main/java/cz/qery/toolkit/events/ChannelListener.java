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
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player p, byte[] data) {
        Plugin plugin = Main.getPlugin(Main.class);
        String b = plugin.getConfig().getString("color.bracket");
        String n = plugin.getConfig().getString("color.name");
        String t = plugin.getConfig().getString("color.text");
        String h = plugin.getConfig().getString("color.highlight");

        data = Arrays.copyOfRange(data, 1, data.length);
        String msg = new String(data, StandardCharsets.US_ASCII);

        /*
        String array = "";
        for (int i = 0; i < data.length; ++i) {
            array = array+" "+data[i];
        }
        */

        if ("minecraft:brand".equals(channel)) {
            if (msg.toLowerCase().contains("vanilla")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, "Vanilla"));
            } else if (msg.toLowerCase().contains("lunarclient")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, "LunarClient"));
            } else if (msg.toLowerCase().contains("forge")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, "Forge"));
            }  else if (msg.toLowerCase().contains("liteloader")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, "LiteLoader"));
            } else if (msg.toLowerCase().contains("rift")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, "Rift"));
            } else if (msg.toLowerCase().contains("fabric")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, "Fabric"));
            } else if (msg.toLowerCase().contains("worlddownloader")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, "WorldDownloader"));
            } else if (msg.toLowerCase().contains("badlion")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, "Badlion"));
            } else {
                Tools.log(b + "[" + n + "SERVER" + b + "] " + h + p.getName() + t + " sent " + h + channel + " " + t + msg);
            }
        } else {
            Tools.log(b + "[" + n + "SERVER" + b + "] " + h + p.getName() + t + " sent " + h + channel + " " + t + msg);
        }
    }
}
