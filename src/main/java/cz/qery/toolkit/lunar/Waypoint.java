package cz.qery.toolkit.lunar;

import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointRemove;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Scripts;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Waypoint(String name, int x, int y, int z, String world, String color) {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String h = plugin.getConfig().getString("color.highlight");
    static String t = plugin.getConfig().getString("color.text");

    public static List<Waypoint> waypoints = new ArrayList<>();


    public static void Load() {
        if (!plugin.getConfig().contains("lunar.waypoints")) {
            return;
        }

        List<Map<?, ?>> maps = plugin.getConfig().getMapList("lunar.waypoints");
        for (Map<?, ?> map : maps) {
            for (Object entry : map.values()) {

                if (!(entry instanceof HashMap)) {
                    continue;
                }

                HashMap<String, Object> point = (HashMap<String, Object>)entry;

                Waypoint waypoint = new Waypoint(point.get("name").toString(), (int) point.get("x"), (int) point.get("y"), (int) point.get("z"), point.get("world").toString(), point.get("color").toString());
                waypoints.add(waypoint);
            }
        }
    }

    public static void Update() {
        List<HashMap<String, HashMap<String, Object>>> l = new ArrayList<>();
        for (Waypoint wp : waypoints) {
            HashMap<String, HashMap<String, Object>> waypoint_temp = new HashMap<String, HashMap<String, Object>>();
            HashMap<String, Object> waypoint_temp2 = new HashMap<String, Object>();
            waypoint_temp2.put("name", wp.name());
            waypoint_temp2.put("color", wp.color());
            waypoint_temp2.put("world", wp.world());
            waypoint_temp2.put("x", wp.x());
            waypoint_temp2.put("y", wp.y());
            waypoint_temp2.put("z", wp.z());
            waypoint_temp.put(wp.name(), waypoint_temp2);
            l.add(waypoint_temp);
        }
        plugin.getConfig().set("lunar.waypoints", null);
        plugin.getConfig().set("lunar.waypoints", l);
        plugin.saveConfig();

        Send();
    }

    public static void Send() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : players) {
            Scripts.sendWaypoints(p, waypoints);
        }
    }

    public static void SendOne(Player p) {
        Scripts.sendWaypoints(p, waypoints);
    }

    public static void Remove(String name, String world) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player pp : players) {
            Tools.sendLunarPacket(pp, new LCPacketWaypointRemove(name, world));
        }
        Update();
    }
}
