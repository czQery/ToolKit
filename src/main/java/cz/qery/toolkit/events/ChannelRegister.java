package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

public class ChannelRegister implements Listener {

    public ChannelRegister(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRegisterChannel(PlayerRegisterChannelEvent event) {
        Player p = event.getPlayer();
        String h = Main.colors.get("h");
        String b = Main.colors.get("b");
        String n = Main.colors.get("n");
        String t = Main.colors.get("t");

        switch (event.getChannel().split(":")[0]) {
            case "lunar", "lunarclient" -> Scripts.addTrueClient(p, "LunarClient");
            case "feather" -> Scripts.addTrueClient(p, "FeatherClient");
            case "fabric", "fabric-screen-handler-api", "fabric-screen-handler-api-v1", "noxesium-v2" -> Scripts.addTrueClient(p, "Fabric");
            case "fml" -> Scripts.addTrueClient(p, "Forge");
            default -> Tools.log(b + "[" + n + "SERVER" + b + "] " + h + event.getPlayer().getName() + t + " registered channel " + h + event.getChannel());
        }
    }
}
