package cz.qery.toolkit.lunar;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.staffmod.StaffModModule;
import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;

public class Staff {
    
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");

    public static StaffModModule modSettingModuleStaff;

    public static void Load() {
        modSettingModuleStaff = Apollo.getModuleManager().getModule(StaffModModule.class);
        Tools.log(b + "[" + n + "ToolKit" + b + "] &aApolloAPI staff loaded!");
    }
}
