package cz.qery.toolkit.loader;

import cz.qery.toolkit.Main;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Waypoints(String name, int x, int y, int z, String world, String color) {
    public static List<Waypoints> list = new ArrayList<>();
    static Main plugin = Main.getPlugin(Main.class);

    public static void Load() {
        if (!plugin.getConfig().contains("waypoints")) {
            return;
        }

        List<Map<?, ?>> maps = plugin.getConfig().getMapList("waypoints");
        for (Map<?, ?> map : maps) {
            map.forEach((k, v) -> {
                if (!(v instanceof HashMap)) {
                    return;
                }

                @SuppressWarnings("unchecked")
                HashMap<String, Object> point = (HashMap<String, Object>) v;

                Waypoints waypoint = new Waypoints(k.toString(), (int) point.get("x"), (int) point.get("y"), (int) point.get("z"), point.get("world").toString(), point.get("color").toString());
                list.add(waypoint);
            });
        }
    }

    public static void Update() {
        List<HashMap<String, HashMap<String, Object>>> l = new ArrayList<>();
        for (Waypoints wp : list) {
            HashMap<String, HashMap<String, Object>> waypoint_temp = new HashMap<>();
            HashMap<String, Object> waypoint_temp2 = new HashMap<>();
            waypoint_temp2.put("color", wp.color);
            waypoint_temp2.put("world", wp.world);
            waypoint_temp2.put("x", wp.x);
            waypoint_temp2.put("y", wp.y);
            waypoint_temp2.put("z", wp.z);
            waypoint_temp.put(wp.name, waypoint_temp2);
            l.add(waypoint_temp);
        }
        plugin.getConfig().set("waypoints", null);
        plugin.getConfig().set("waypoints", l);
        plugin.saveConfig();

        if (Main.ApolloLoaded) {
            LunarWaypoints.Send();
        }
    }
}
