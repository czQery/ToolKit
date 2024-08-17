package cz.qery.toolkit.lunar;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.BukkitApollo;
import com.lunarclient.apollo.mods.Mods;
import com.lunarclient.apollo.module.modsetting.ModSettingModule;
import com.lunarclient.apollo.option.SimpleOption;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mod {

    static Main plugin = Main.getPlugin(Main.class);

    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");
    static String h = Main.colors.get("h");
    static String t = Main.colors.get("t");
    public static List<String> mods = new ArrayList<>();
    public static Map<String, SimpleOption<Boolean>> mods_all = new HashMap<>();
    public static ModSettingModule modSettingModule;

    public static void Load() throws NoSuchFieldException, IllegalAccessException {
        modSettingModule = Apollo.getModuleManager().getModule(ModSettingModule.class);
        mods = plugin.getConfig().getStringList("lunar.disabled_mods");

        for (Class<?> modClass : Mods.ALL_MODS) {
            Field field = modClass.getField("ENABLED");

            @SuppressWarnings("unchecked")
            SimpleOption<Boolean> option = (SimpleOption<Boolean>) field.get(field.getClass());

            //Tools.log(modClass.getSimpleName().replaceFirst("Mod", ""));

            mods_all.put(modClass.getSimpleName().toLowerCase().replaceFirst("mod", ""), option);
        }

        Send();

        Tools.log(b + "[" + n + "ToolKit" + b + "] &aApolloAPI mods loaded!");
    }

    public static void Update() {
        plugin.getConfig().set("lunar.disabled_mods", null);
        plugin.getConfig().set("lunar.disabled_mods", mods);
        plugin.saveConfig();

        Send();
    }

    public static void Send() {
        for (String mod : mods) {
            if (!mods_all.containsKey(mod.toLowerCase())) {
                Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar mod invalid name " + h + mod);
                continue;
            }
            Mod.modSettingModule.getOptions().set(mods_all.get(mod.toLowerCase()), false);
        }
    }

    public static void UnSend(String mod) {
        if (!mods_all.containsKey(mod.toLowerCase())) {
            Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar mod invalid name " + h + mod);
            return;
        }
        Mod.modSettingModule.getOptions().set(mods_all.get(mod.toLowerCase()), true);
    }

    public static void Bypass(Player p) {
        for (String mod : mods) {
            if (!mods_all.containsKey(mod.toLowerCase())) {
                continue;
            }
            BukkitApollo.runForPlayer(p, apolloPlayer -> Mod.modSettingModule.getOptions().set(apolloPlayer, mods_all.get(mod.toLowerCase()), true));
        }
    }
}
