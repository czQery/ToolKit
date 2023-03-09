package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public record CommandBlock(String name) {
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
        if (inp_list == null) {
            Tools.log(b + "[" + n + "SERVER" + b + "] " + t + "Command block list is empty");
            return;
        }

        for (Object cmdc : inp_list) {
            CommandBlock cmdd = new CommandBlock(cmdc.toString());
            cmdlist.add(cmdd);
        }
    }

    public static void Update() {
        List<HashMap<String, HashMap<String, Object>>> l = new ArrayList<>();
        String[] cmdl = new String[cmdlist.size()];
        int cmdi = 0;
        for (CommandBlock cmdc : cmdlist) {
            cmdl[cmdi] = cmdc.name();
            cmdi = cmdi + 1;
        }
        plugin.getConfig().set("commandblock.list", null);
        plugin.getConfig().set("commandblock.list", cmdl);
        plugin.saveConfig();
    }
}