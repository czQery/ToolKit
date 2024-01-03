package cz.qery.toolkit.events;

import com.lunarclient.apollo.event.ApolloListener;
import com.lunarclient.apollo.event.EventBus;
import com.lunarclient.apollo.event.Listen;
import com.lunarclient.apollo.event.player.ApolloRegisterPlayerEvent;
import com.lunarclient.apollo.module.staffmod.StaffMod;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.lunar.Mod;
import cz.qery.toolkit.lunar.Notification;
import cz.qery.toolkit.lunar.Staff;
import cz.qery.toolkit.lunar.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;

public class ApolloRegister implements ApolloListener {

    static Plugin plugin = Main.getPlugin(Main.class);

    public ApolloRegister() {
        EventBus.getBus().register(this);
    }

    @Listen
    @SuppressWarnings("unused")
    public void onApolloRegister(ApolloRegisterPlayerEvent e) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            if (e.getPlayer().hasPermission("toolkit.lunar.staff")) {
                Staff.modSettingModuleStaff.enableStaffMods(e.getPlayer(), Collections.singletonList(StaffMod.XRAY));

                Player p = Bukkit.getPlayer(e.getPlayer().getUniqueId());

                Mod.Bypass(p);
                Notification.Cancel(p);
                Notification.Send(p, "Joined in staff mode!");
            }
            Waypoint.SendOne(Bukkit.getPlayer(e.getPlayer().getUniqueId()));
        }, 15);
    }
}