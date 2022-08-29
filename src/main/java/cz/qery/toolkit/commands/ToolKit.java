package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Tools;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ToolKit implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    String b = plugin.getConfig().getString("color.bracket");
    String n = plugin.getConfig().getString("color.name");
    String t = plugin.getConfig().getString("color.text");
    String h = plugin.getConfig().getString("color.highlight");

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        String version = Bukkit.getServer().getPluginManager().getPlugin("ToolKit").getDescription().getVersion();

        final @NotNull TextComponent link = Component.text()
                .content(Tools.chat(b+"- "+t+"GitHub "+h+"https://github.com/czQery/ToolKit"))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/czQery/ToolKit"))
                .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("")))
                .build();

        sender.sendMessage(Tools.chat(b+"-------["+n+"TOOLKIT"+b+"]-------"));
        sender.sendMessage(Tools.chat(b+"- "+t+"Set of useful tools"));
        sender.sendMessage(Tools.chat(b+"- "+t+"Version "+h+version));
        if (Tools.isPaper) {
            sender.sendMessage(link);
        } else {
            sender.sendMessage(Tools.chat(b+"- "+t+"GitHub "+h+"https://github.com/czQery/ToolKit"));
        }
        sender.sendMessage(Tools.chat(b+"- "+t+"Made by "+h+"czQery"));
        sender.sendMessage(Tools.chat(b+"----------------------"));
        return false;
    }
}
