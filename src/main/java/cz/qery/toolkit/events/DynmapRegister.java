package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import cz.qery.toolkit.lunar.Waypoint;
import org.bukkit.Bukkit;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;

public class DynmapRegister extends DynmapCommonAPIListener {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");

    public DynmapRegister() {
        DynmapCommonAPIListener.register(this);
    }

    @Override
    public void apiEnabled(DynmapCommonAPI api) {
        Tools.DynApi = api;
        api.getMarkerAPI().createMarkerSet("toolkit.lunar", "Lunar", null, false);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Waypoint::Update);
        Tools.log(b+"["+n+"ToolKit"+b+"] &aDynmapAPI loaded!");
    }
}
