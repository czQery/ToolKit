package cz.qery.toolkit.loader;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.BukkitApollo;
import com.lunarclient.apollo.module.notification.NotificationModule;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;

public class LunarNotification {

    public static NotificationModule notificationModule;
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");

    public static void Load() {
        notificationModule = Apollo.getModuleManager().getModule(NotificationModule.class);
        Other.Tools.log(b + "[" + n + "ToolKit" + b + "] &aApolloAPI notifications loaded!");
    }

    public static void Send(Player p, String msg) {
        BukkitApollo.runForPlayer(p, apolloPlayer -> notificationModule.displayNotification(apolloPlayer, com.lunarclient.apollo.module.notification.Notification.builder()
                .titleComponent(Component.text("Server"/*, NamedTextColor.RED*/))
                .descriptionComponent(Component.text(msg))
                .displayTime(Duration.ofSeconds(5))
                .build()));
    }

    public static void Cancel(Player p) {
        BukkitApollo.runForPlayer(p, apolloPlayer -> notificationModule.resetNotifications(apolloPlayer));
    }
}
