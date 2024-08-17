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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ToolKit implements CommandExecutor {
    String b = Main.colors.get("b");
    String n = Main.colors.get("n");
    String t = Main.colors.get("t");
    String h = Main.colors.get("h");

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        String version = Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("ToolKit")).getDescription().getVersion();

        final @NotNull TextComponent link = Component.text()
                .content(Tools.chat(b + "- " + t + "GitHub " + h + "https://github.com/czQery/ToolKit"))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/czQery/ToolKit"))
                .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("open")))
                .build();

        sender.sendMessage(Tools.chat(b + "[" + n + "ToolKit" + b + "]"));
        sender.sendMessage(Tools.chat(b + "- " + t + "Set of useful tools"));
        sender.sendMessage(Tools.chat(b + "- " + t + "Version " + h + version));
        sender.sendMessage(link);
        sender.sendMessage(Tools.chat(b + "- " + t + "Made by " + h + "czQery"));
        return false;
    }
}
