package cz.qery.toolkit.lunar;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketUpdateWorld;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointAdd;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public final class Waypoint {
    private final String name;
    private final int x;
    private final int y;
    private final int z;
    private final String world;
    private final String color;


    static Main plugin = Main.getPlugin(Main.class);
    public static List<Waypoint> waypoints = new ArrayList<>();

    public static void Load() {
        if (!plugin.getConfig().contains("lunar.waypoints")) {
            return;
        }

        List<Map<?, ?>> maps = plugin.getConfig().getMapList("lunar.waypoints");
        for (Map<?, ?> map : maps) {
            for (Object entry : map.values()) {
                HashMap point = (HashMap) entry;

                Waypoint waypoint = new Waypoint(point.get("name").toString(), (int) point.get("x"), (int) point.get("y"), (int) point.get("z"), point.get("world").toString(), point.get("color").toString());
                waypoints.add(waypoint);
            }
        }
    }

    public static void Update() {
        List<HashMap<String, HashMap<String, Object>>> l = new ArrayList();
        for (Waypoint wp : waypoints) {
            HashMap<String, HashMap<String, Object>> waypoint_temp = new HashMap<String, HashMap<String, Object>>();
            HashMap<String, Object> waypoint_temp2 = new HashMap<String, Object>();
            waypoint_temp2.put("name", wp.getName());
            waypoint_temp2.put("color", wp.getColor());
            waypoint_temp2.put("world", wp.getWorld());
            waypoint_temp2.put("x", wp.getX());
            waypoint_temp2.put("y", wp.getY());
            waypoint_temp2.put("z", wp.getZ());
            waypoint_temp.put(wp.getName(), waypoint_temp2);
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
            for (Waypoint waypoint : waypoints) {
                Tools.sendLunarPacket(p, new LCPacketUpdateWorld(p.getWorld().getUID().toString()));
                Tools.sendLunarPacket(p, new LCPacketWaypointAdd(waypoint.getName(), Bukkit.getWorld(waypoint.getWorld()).getUID().toString(), Integer.parseInt(waypoint.getColor().replaceFirst("#", ""), 16), waypoint.getX(), waypoint.getY(), waypoint.getZ(), true, true));
            }
        }
    }

    public static void SendOne(Player p) {
        for (Waypoint waypoint : waypoints) {
            Tools.sendLunarPacket(p, new LCPacketUpdateWorld(p.getWorld().getUID().toString()));
            Tools.sendLunarPacket(p, new LCPacketWaypointAdd(waypoint.getName(), Bukkit.getWorld(waypoint.getWorld()).getUID().toString(), Integer.parseInt(waypoint.getColor().replaceFirst("#", ""), 16), waypoint.getX(), waypoint.getY(), waypoint.getZ(), true, true));
        }
    }
}
