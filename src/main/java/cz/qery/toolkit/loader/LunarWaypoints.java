package cz.qery.toolkit.loader;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.BukkitApollo;
import com.lunarclient.apollo.common.location.ApolloBlockLocation;
import com.lunarclient.apollo.module.waypoint.WaypointModule;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LunarWaypoints {

    public static WaypointModule waypointModule = null;
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");
    static String h = Main.colors.get("h");
    static String t = Main.colors.get("t");

    static boolean Check() {
        if (waypointModule == null) {
            Other.Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypointModule is null!");
            return true;
        }

        return false;
    }

    public static void Load() {
        waypointModule = Apollo.getModuleManager().getModule(WaypointModule.class);
        Other.Tools.log(b + "[" + n + "ToolKit" + b + "] &aApolloAPI waypoints loaded!");
    }

    public static void Send() {
        if (Check()) {
            return;
        }

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        for (Player p : players) {
            SendOne(p);
        }
    }

    public static void SendOne(Player p) {
        if (Check()) {
            return;
        }

        for (Waypoints waypoint : Waypoints.list) {
            if (Bukkit.getWorld(waypoint.world()) == null) {
                Other.Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypoint " + h + waypoint.name() + t + " has invalid world " + h + waypoint.world());
                continue;
            }
            if (!waypoint.color().contains("#") && !waypoint.color().matches("^[a-fA-F0-9#]{0,7}$")) {
                Other.Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypoint " + h + waypoint.name() + t + " has invalid color " + h + waypoint.color());
                continue;
            }

            BukkitApollo.runForPlayer(p, apolloPlayer -> waypointModule.displayWaypoint(apolloPlayer, com.lunarclient.apollo.module.waypoint.Waypoint.builder()
                    .name(waypoint.name())
                    .location(ApolloBlockLocation.builder()
                            .world(waypoint.world())
                            .x(waypoint.x())
                            .y(waypoint.y())
                            .z(waypoint.z())
                            .build()
                    )

                    .color(java.awt.Color.decode(waypoint.color()))
                    .preventRemoval(true)
                    .hidden(false)
                    .build()
            ));
        }
    }

    public static void Remove(String name) {
        if (Check()) {
            return;
        }

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player pp : players) {
            BukkitApollo.runForPlayer(pp, apolloPlayer -> waypointModule.removeWaypoint(apolloPlayer, name));
        }

        Waypoints.Update();
    }
}
