package cz.qery.toolkit.lunar;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketUpdateWorld;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointAdd;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointRemove;
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
                if (Bukkit.getWorld(waypoint.getWorld()) == null) {
                    Tools.log(b+"["+n+"ToolKit"+b+"] "+t+"Lunar waypoint "+h+waypoint.getName()+t+" has invalid world "+h+waypoint.getWorld());
                } else if (!waypoint.getColor().contains("#") && !waypoint.getColor().matches("^[a-fA-F0-9#]{0,7}$")) {
                    Tools.log(b+"["+n+"ToolKit"+b+"] "+t+"Lunar waypoint "+h+waypoint.getName()+t+" has invalid color "+h+waypoint.getColor());
                } else {
                    Tools.sendLunarPacket(p, new LCPacketWaypointAdd(waypoint.getName(), Bukkit.getWorld(waypoint.getWorld()).getUID().toString(), Integer.parseInt(waypoint.getColor().replaceFirst("#", ""), 16), waypoint.getX(), waypoint.getY(), waypoint.getZ(), true, true));
                }
            }
        }
    }

    public static void SendOne(Player p) {
        for (Waypoint waypoint : waypoints) {
            Tools.sendLunarPacket(p, new LCPacketUpdateWorld(p.getWorld().getUID().toString()));
            if (Bukkit.getWorld(waypoint.getWorld()) == null) {
                Tools.log(b+"["+n+"ToolKit"+b+"] "+t+"Lunar waypoint "+h+waypoint.getName()+t+" has invalid world "+h+waypoint.getWorld());
            } else if (!waypoint.getColor().contains("#") && !waypoint.getColor().matches("^[a-fA-F0-9#]{0,7}$")) {
                Tools.log(b+"["+n+"ToolKit"+b+"] "+t+"Lunar waypoint "+h+waypoint.getName()+t+" has invalid color "+h+waypoint.getColor());
            } else {
                Tools.sendLunarPacket(p, new LCPacketWaypointAdd(waypoint.getName(), Bukkit.getWorld(waypoint.getWorld()).getUID().toString(), Integer.parseInt(waypoint.getColor().replaceFirst("#", ""), 16), waypoint.getX(), waypoint.getY(), waypoint.getZ(), true, true));
            }
        }
    }

    public static void Remove(String name, String world) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player pp : players) {
            Tools.sendLunarPacket(pp, new LCPacketWaypointRemove(name, world));
        }
        Update();
    }
}
