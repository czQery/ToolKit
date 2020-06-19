package cz.qery.toolkit.commands;

import cz.qery.toolkit.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String version = Bukkit.getServer().getPluginManager().getPlugin("ToolKit").getDescription().getVersion();
        Player p = (Player) sender;
        p.sendMessage(Utils.chat("&8-------[&cTOOLKIT&8]-------"));
        p.sendMessage(Utils.chat("&8- &7Very useful plugin :))"));
        p.sendMessage(Utils.chat("&8- &7Version &6"+version));
        p.sendMessage(Utils.chat("&8- &7Made by &6czQery"));
        p.sendMessage(Utils.chat("&8-----------------------"));
        return false;
    }
}
