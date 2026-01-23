package cz.qery.toolkit.loader;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.staffmod.StaffModModule;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;

public class LunarStaff {

    public static StaffModModule modSettingModuleStaff;
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");

    public static void Load() {
        modSettingModuleStaff = Apollo.getModuleManager().getModule(StaffModModule.class);
        Other.Tools.log(b + "[" + n + "ToolKit" + b + "] &aApolloAPI staff loaded!");
    }
}
