package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandHandler {
    static Plugin plugin = Main.getPlugin(Main.class);

    public static final Map<String, CommandExecutor> commands = new HashMap<>();
    static {
        commands.put("toolkit", new ToolKit());
        commands.put("crash", new Crash());
        commands.put("crawl", new Crawl());
        commands.put("skick", new SKick());
        commands.put("sit", new Sit());
        commands.put("troll", new Troll());
        commands.put("pinfo", new PInfo());
        commands.put("rp", new RP());
        commands.put("lunar", new Lunar());
        commands.put("cmdblock", new Cmdblock());
        commands.put("vanish", new Vanish());

        //Aliases
        commands.put("gmc", new Aliases());
        commands.put("gms", new Aliases());
        commands.put("gma", new Aliases());
        commands.put("gmsp", new Aliases());
        commands.put("spawn", new Aliases());
        commands.put("fly", new Aliases());
        commands.put("wc", new Aliases());
        commands.put("ic", new Aliases());

    }

    public static String getExistingCommand(String command, Map<String, org.bukkit.command.Command> knownCommands) {
        for (String kc : knownCommands.keySet()) {
            if (kc.contains(":"+command) && !kc.equals("toolkit:"+command)) {
                return kc;
            }
        }

        return null;
    }

    public static void load(Main main) throws NoSuchFieldException, IllegalAccessException {
        SimpleCommandMap commandMap = (SimpleCommandMap)  main.getServer().getCommandMap();
        final HashMap<String, Command> knownCommands = (HashMap<String, Command>) commandMap.getKnownCommands();

        for (String cmdName : commands.keySet()) {
            if (main.getConfig().getBoolean("commands."+cmdName) || cmdName.equals("toolkit")) {
                Objects.requireNonNull(main.getCommand(cmdName)).setExecutor(commands.get(cmdName));
            } else {
                knownCommands.remove("toolkit:"+cmdName);

                String ec = getExistingCommand(cmdName, knownCommands);

                if (ec == null) {
                    knownCommands.remove(cmdName);
                } else {
                    knownCommands.replace(cmdName, commandMap.getCommand(ec));
                }
            }
        }
    }

    private static boolean hasPermissionBase(CommandSender sender, Command cmd, String prefix) {
        if (cmd.getPermission() == null) {
            return false;
        }

        if ((sender instanceof Player) && !sender.hasPermission(cmd.getPermission()+prefix)) {
            if (!prefix.equals(".bypass")) {
                sender.sendMessage(Tools.chat(plugin.getConfig().getString("commandblock.message")));
            }
            return false;
        }

        return true;
    }

    public static boolean hasPermission(CommandSender sender, Command cmd) {
        return hasPermissionBase(sender, cmd, "");
    }

    public static boolean hasPermissionOther(CommandSender sender, Command cmd) {
        return hasPermissionBase(sender, cmd, ".other");
    }

    public static boolean hasPermissionBypass(CommandSender sender, Command cmd) {
        return hasPermissionBase(sender, cmd, ".bypass");
    }
}
