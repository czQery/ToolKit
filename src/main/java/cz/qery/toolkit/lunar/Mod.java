package cz.qery.toolkit.lunar;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketModSettings;
import com.lunarclient.bukkitapi.nethandler.client.obj.ModSettings;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mod {

    static Main plugin = Main.getPlugin(Main.class);
    public static LCPacketModSettings mods = null;

    public static void Load() {
        ModSettings modSettings = new ModSettings();
        for (String modId : plugin.getConfig().getStringList("lunar.disabled_mods")) {
            modSettings.addModSetting(modId, new ModSettings.ModSetting(false, new HashMap<>()));
        }
        mods = new LCPacketModSettings(modSettings);
    }

    public static void Send() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : players) {
            Tools.sendLunarPacket(p, Mod.mods);
        }
    }

    public static void SendOne(Player p) {
        Tools.sendLunarPacket(p, Mod.mods);
    }
}
