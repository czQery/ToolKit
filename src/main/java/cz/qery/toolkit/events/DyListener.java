package cz.qery.toolkit.events;

import cz.qery.toolkit.Dy;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import cz.qery.toolkit.lunar.Waypoint;
import org.bukkit.Bukkit;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;

public class DyListener extends DynmapCommonAPIListener {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String t = plugin.getConfig().getString("color.text");
    static String h = plugin.getConfig().getString("color.highlight");

    public DyListener() {
        DynmapCommonAPIListener.register(this);
    }

    @Override
    public void apiEnabled(DynmapCommonAPI api) {
        Dy.api = api;
        api.getMarkerAPI().createMarkerSet("toolkit.lunar", "Lunar", null, false);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Waypoint::Update);
        Tools.log(b+"["+n+"ToolKit"+b+"] &aDynmapAPI loaded!");
    }
}
