package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.helper.Other;
import cz.qery.toolkit.loader.Commands;
import cz.qery.toolkit.loader.CommandsBlock;
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
    String b = Main.colors.get("b");
    String n = Main.colors.get("n");
    String t = Main.colors.get("t");
    String h = Main.colors.get("h");

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        CommandSender p;

        if ((sender instanceof Player)) {
            p = sender;
            if (!Commands.hasPermission(sender, cmd)) {
                return false;
            }
        } else {
            p = sender;
        }

        if (args.length < 1) {
            p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Please use " + h + "/cmdblock <tool>"));
        } else {
            switch (args[0].toLowerCase()) {
                case "help" -> {
                    p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]"));
                    p.sendMessage(Other.Tools.chat(b + "- " + t + "add"));
                    p.sendMessage(Other.Tools.chat(b + "- " + t + "remove"));
                    p.sendMessage(Other.Tools.chat(b + "- " + t + "list"));
                }
                case "add" -> {
                    if (args.length < 2) {
                        p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Please use " + h + "/cmdblock add <cmd>"));
                    } else {
                        for (CommandsBlock cmdb : CommandsBlock.cmdlist) {
                            if (cmdb.name().equals(args[1])) {
                                p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Command is already blocked!"));
                                return false;
                            }
                        }
                        CommandsBlock cmdc = new CommandsBlock(args[1]);
                        CommandsBlock.cmdlist.add(cmdc);
                        Bukkit.getAsyncScheduler().runNow(plugin, (task) -> CommandsBlock.Update());
                        p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Command added to block list!"));
                    }
                }
                case "remove" -> {
                    if (args.length < 2) {
                        p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Please use " + h + "/cmdblock remove <cmd>"));
                    } else {
                        for (CommandsBlock cmdb : CommandsBlock.cmdlist) {
                            if (cmdb.name().equals(args[1])) {
                                CommandsBlock.cmdlist.remove(cmdb);
                                Bukkit.getAsyncScheduler().runNow(plugin, (task) -> CommandsBlock.Update());
                                p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Command removed!"));
                                return false;
                            }
                        }
                        p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Command with this name does not exists!"));
                    }
                }
                case "list" -> {
                    if (!CommandsBlock.cmdlist.isEmpty()) {
                        p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]"));
                        for (CommandsBlock cmdb : CommandsBlock.cmdlist) {
                            p.sendMessage(Other.Tools.chat(b + "- " + t + cmdb.name()));
                        }
                    } else {
                        sender.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " There are no blocked commands!"));
                    }
                }
                default ->
                        p.sendMessage(Other.Tools.chat(b + "[" + n + "CMDBLOCK" + b + "]" + t + " Please use " + h + "/cmdblock <tool>"));
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
            case 2 -> list.add("name");
        }

        if (!list.isEmpty()) {
            return list;
        }

        return null;

    }
}
