package cz.qery.toolkit.lunar;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.BukkitApollo;
import com.lunarclient.apollo.common.location.ApolloBlockLocation;
import com.lunarclient.apollo.module.waypoint.WaypointModule;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dynmap.markers.Marker;

import java.util.*;
import java.util.List;

public record Waypoint(String name, int x, int y, int z, String world, String color) {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");
    static String h = Main.colors.get("h");
    static String t = Main.colors.get("t");

    public static List<Waypoint> waypoints = new ArrayList<>();

    public static WaypointModule waypointModule;

    public static void Load() {
        if (!plugin.getConfig().contains("lunar.waypoints")) {
            return;
        }

        waypointModule = Apollo.getModuleManager().getModule(WaypointModule.class);

        List<Map<?, ?>> maps = plugin.getConfig().getMapList("lunar.waypoints");
        for (Map<?, ?> map : maps) {
            map.forEach((k, v) -> {
                if (!(v instanceof HashMap)) {
                    return;
                }

                @SuppressWarnings("unchecked")
                HashMap<String, Object> point = (HashMap<String, Object>) v;

                Waypoint waypoint = new Waypoint(k.toString(), (int) point.get("x"), (int) point.get("y"), (int) point.get("z"), point.get("world").toString(), point.get("color").toString());
                waypoints.add(waypoint);
            });
        }

        Tools.log(b+"["+n+"ToolKit"+b+"] &aApolloAPI waypoints loaded!");
    }

    public static void Update() {
        List<HashMap<String, HashMap<String, Object>>> l = new ArrayList<>();
        for (Waypoint wp : waypoints) {
            HashMap<String, HashMap<String, Object>> waypoint_temp = new HashMap<>();
            HashMap<String, Object> waypoint_temp2 = new HashMap<>();
            waypoint_temp2.put("color", wp.color);
            waypoint_temp2.put("world", wp.world);
            waypoint_temp2.put("x", wp.x);
            waypoint_temp2.put("y", wp.y);
            waypoint_temp2.put("z", wp.z);
            waypoint_temp.put(wp.name, waypoint_temp2);
            l.add(waypoint_temp);

            if (Tools.DynApi != null && Tools.DynApi.getMarkerAPI().getMarkerSet("toolkit.lunar").findMarkerByLabel(wp.name) == null) {
                Tools.DynApi.getMarkerAPI().getMarkerSet("toolkit.lunar").createMarker(wp.name, "<span style=\"color: " + wp.color + ";\">⬤ </span><span>" + wp.name + "</span>", true, wp.world, wp.x, wp.y, wp.z, Tools.DynApi.getMarkerAPI().getMarkerIcon("pin"), false);
            }
        }
        plugin.getConfig().set("lunar.waypoints", null);
        plugin.getConfig().set("lunar.waypoints", l);
        plugin.saveConfig();

        Send();
    }

    public static void Send() {
        if (waypointModule == null) {
            Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypointModule is null!");
            return;
        }

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        for (Player p : players) {
            SendOne(p);
        }
    }

    public static void SendOne(Player p) {
        if (waypointModule == null) {
            Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypointModule is null!");
            return;
        }

        for (Waypoint waypoint : waypoints) {
            if (Bukkit.getWorld(waypoint.world()) == null) {
                Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypoint " + h + waypoint.name() + t + " has invalid world " + h + waypoint.world());
                continue;
            }
            if (!waypoint.color().contains("#") && !waypoint.color().matches("^[a-fA-F0-9#]{0,7}$")) {
                Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypoint " + h + waypoint.name() + t + " has invalid color " + h + waypoint.color());
                continue;
            }

            BukkitApollo.runForPlayer(p, apolloPlayer -> {
                Waypoint.waypointModule.displayWaypoint(apolloPlayer, com.lunarclient.apollo.module.waypoint.Waypoint.builder()
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
                );
            });
        }
    }

    public static void Remove(String name) {
        if (Tools.DynApi != null) {
            Marker dyMarker = Tools.DynApi.getMarkerAPI().getMarkerSet("toolkit.lunar").findMarkerByLabel(name);

            if (dyMarker != null) {
                dyMarker.deleteMarker();
            }
        }

        if (waypointModule == null) {
            Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypointModule is null!");
        } else {
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            for (Player pp : players) {
                BukkitApollo.runForPlayer(pp, apolloPlayer -> {
                    Waypoint.waypointModule.removeWaypoint(apolloPlayer, name);
                });
            }
        }

        Update();
    }
}
