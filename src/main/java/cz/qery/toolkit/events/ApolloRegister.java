package cz.qery.toolkit.events;

import com.lunarclient.apollo.event.ApolloListener;
import com.lunarclient.apollo.event.EventBus;
import com.lunarclient.apollo.event.Listen;
import com.lunarclient.apollo.event.player.ApolloRegisterPlayerEvent;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.lunar.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ApolloRegister implements ApolloListener {

    static Plugin plugin = Main.getPlugin(Main.class);

    public ApolloRegister() {
        EventBus.getBus().register(this);
    }

    @Listen
    public void onApolloRegister(ApolloRegisterPlayerEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Waypoint.SendOne(Bukkit.getPlayer(e.getPlayer().getUniqueId())));
    }
}