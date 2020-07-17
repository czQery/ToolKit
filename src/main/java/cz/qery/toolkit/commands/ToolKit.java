package cz.qery.toolkit.commands;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ToolKit implements CommandExecutor {
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p =(Player) sender;
        String version = Bukkit.getServer().getPluginManager().getPlugin("ToolKit").getDescription().getVersion();

        Plugin plugin = Main.getPlugin(Main.class);
        String b = plugin.getConfig().getString("color.bracket");
        String n = plugin.getConfig().getString("color.name");
        String t = plugin.getConfig().getString("color.text");
        String h = plugin.getConfig().getString("color.highlight");


        TextComponent github = Utils.schat(b+"- "+t+"GitHub "+h+"https://github.com/czQery/ToolKit");
        github.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.schat(t+"Click to open")).create()));
        github.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/czQery/ToolKit"));

        p.sendMessage(Utils.chat(b+"-------["+n+"TOOLKIT"+b+"]-------"));
        p.sendMessage(Utils.chat(b+"- "+t+"Very useful plugin :))"));
        p.sendMessage(Utils.chat(b+"- "+t+"Version "+h+version));
        p.spigot().sendMessage(github);
        p.sendMessage(Utils.chat(b+"- "+t+"Made by "+h+"czQery"));
        p.sendMessage(Utils.chat(b+"-----------------------"));
        return false;
    }
}
