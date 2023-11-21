package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Cmdblock implements TabExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        CommandSender p;

        if ((sender instanceof Player)) {
            p = (Player) sender;
            if (!CommandHandler.hasPermission(sender, cmd)) {
                return false;
            }
        } else {
            p = sender;
        }

        if (args.length < 1) {
            p.sendMessage(Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Please use " + h + "/cmdblock <tool>"));
        } else {
            switch (args[0].toLowerCase()) {
                case "help" -> {
                    p.sendMessage(Tools.chat(b + "-------[" + n + "CMDBLOCK" + b + "]--------"));
                    p.sendMessage(Tools.chat(b + "- " + t + "add"));
                    p.sendMessage(Tools.chat(b + "- " + t + "remove"));
                    p.sendMessage(Tools.chat(b + "- " + t + "list"));
                    p.sendMessage(Tools.chat(b + "-------------------------"));
                }
                case "add" -> {
                    if (args.length < 2) {
                        p.sendMessage(Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Please use " + h + "/cmdblock add <cmd>"));
                    } else {
                        for (CommandBlock cmdb : CommandBlock.cmdlist) {
                            if (cmdb.name().equals(args[1])) {
                                p.sendMessage(Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Command is already blocked!"));
                                return false;
                            }
                        }
                        CommandBlock cmdc = new CommandBlock(args[1]);
                        CommandBlock.cmdlist.add(cmdc);
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, CommandBlock::Update);
                        p.sendMessage(Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Command added to block list!"));
                    }
                }
                case "remove" -> {
                    if (args.length < 2) {
                        p.sendMessage(Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Please use " + h + "/cmdblock remove <cmd>"));
                    } else {
                        for (CommandBlock cmdb : CommandBlock.cmdlist) {
                            if (cmdb.name().equals(args[1])) {
                                CommandBlock.cmdlist.remove(cmdb);
                                Bukkit.getScheduler().runTaskAsynchronously(plugin, CommandBlock::Update);
                                p.sendMessage(Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Command removed!"));
                                return false;
                            }
                        }
                        p.sendMessage(Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Command with this name does not exists!"));
                    }
                }
                case "list" -> {
                    p.sendMessage(Tools.chat(b + "-------[" + n + "CMDBLOCK" + b + "]--------"));
                    for (CommandBlock cmdb : CommandBlock.cmdlist) {
                        p.sendMessage(Tools.chat(b + "- " + t + cmdb.name()));
                    }
                    p.sendMessage(Tools.chat(b + "-------------------------"));
                }
                default ->
                        p.sendMessage(Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Please use " + h + "/cmdblock <tool>"));
            }
        }

        return false;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        List<String> list = new ArrayList<>();

        switch (args.length) {
            case 1 -> {
                list.add("add");
                list.add("remove");
                list.add("list");
            }
            case 2 -> {
                for (CommandBlock cmdb : CommandBlock.cmdlist) {
                    list.add(cmdb.name());
                }
            }
        }

        if (!list.isEmpty()) {
            return list;
        }

        return null;

    }
}
