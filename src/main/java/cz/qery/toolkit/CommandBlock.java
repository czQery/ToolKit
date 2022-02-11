package cz.qery.toolkit;

import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointRemove;
import cz.qery.toolkit.lunar.Waypoint;
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
public final class CommandBlock {
    private final String name;

    static Main plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String h = plugin.getConfig().getString("color.highlight");
    static String t = plugin.getConfig().getString("color.text");

    public static List<CommandBlock> cmdlist = new ArrayList<>();

    public static void Load() {
        if (!plugin.getConfig().contains("commandblock.list")) {
            return;
        }
        List<?> inp_list = plugin.getConfig().getList("commandblock.list");
        for (Object cmdc : inp_list) {
            CommandBlock cmdd = new CommandBlock(cmdc.toString());
            cmdlist.add(cmdd);
        }
    }

    public static void Update() {
        List<HashMap<String, HashMap<String, Object>>> l = new ArrayList();
        String[] cmdl = new String[cmdlist.size()];
        int cmdi = 0;
        for (CommandBlock cmdc : cmdlist) {
            cmdl[cmdi] = cmdc.getName();
            cmdi = cmdi+1;
        }
        plugin.getConfig().set("commandblock.list", null);
        plugin.getConfig().set("commandblock.list", cmdl);
        plugin.saveConfig();
    }
}