package cz.qery.toolkit.lunar;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.BukkitApollo;
import com.lunarclient.apollo.module.notification.NotificationModule;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;

public class Notification {

    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");

    public static NotificationModule notificationModule;

    public static void Load() {
        notificationModule = Apollo.getModuleManager().getModule(NotificationModule.class);
        Tools.log(b + "[" + n + "ToolKit" + b + "] &aApolloAPI notifications loaded!");
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
