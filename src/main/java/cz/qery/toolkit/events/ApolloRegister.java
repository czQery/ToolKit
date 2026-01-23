package cz.qery.toolkit.events;

import com.lunarclient.apollo.event.ApolloListener;
import com.lunarclient.apollo.event.EventBus;
import com.lunarclient.apollo.event.Listen;
import com.lunarclient.apollo.event.player.ApolloRegisterPlayerEvent;
import com.lunarclient.apollo.module.staffmod.StaffMod;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.loader.LunarNotification;
import cz.qery.toolkit.loader.LunarStaff;
import cz.qery.toolkit.loader.LunarWaypoints;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ApolloRegister implements ApolloListener {

    static Plugin plugin = Main.getPlugin(Main.class);

    public ApolloRegister() {
        EventBus.getBus().register(this);
    }

    @Listen
    @SuppressWarnings("unused")
    public void onApolloRegister(ApolloRegisterPlayerEvent e) {
        Player p = plugin.getServer().getPlayer(e.getPlayer().getUniqueId());

        Objects.requireNonNull(p).getScheduler().runDelayed(plugin, (task) -> {
            if (p.hasPermission("toolkit.lunar.staff")) {
                LunarStaff.modSettingModuleStaff.enableStaffMods(e.getPlayer(), Collections.singletonList(StaffMod.XRAY));

                LunarNotification.Cancel(p);
                LunarNotification.Send(p, "Joined in staff mode!");
            }
            LunarWaypoints.SendOne(p);

        }, null, 20);
    }
}