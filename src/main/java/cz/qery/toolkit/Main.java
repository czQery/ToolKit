package cz.qery.toolkit;

import cz.qery.toolkit.commands.*;
import cz.qery.toolkit.events.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfiguration();

        getCommand("crasher").setExecutor(new Crasher());
        getCommand("crawl").setExecutor(new Crawl());
        getCommand("sit").setExecutor(new Sit());
        getCommand("skick").setExecutor(new SKick());
        getCommand("toolkit").setExecutor(new ToolKit());
        getCommand("troll").setExecutor(new Troll());

        new Interact(this);
        new Join(this);
        new Leave(this);
        new Move(this);
    }

    public void loadConfiguration(){
        //JOIN
        this.getConfig().addDefault("join.alert", true);
        this.getConfig().addDefault("join.message", "&8[&cSERVER&8]&6 %player%&7 joined!");
        this.getConfig().addDefault("join.teleport", true);
        this.getConfig().addDefault("join.world", "world");
        //LEAVE
        this.getConfig().addDefault("leave.alert", true);
        this.getConfig().addDefault("leave.message", "&8[&cSERVER&8]&6 %player%&7 disconnected!");
        //COLORS
        this.getConfig().addDefault("color.bracket", "&8");
        this.getConfig().addDefault("color.name", "&c");
        this.getConfig().addDefault("color.text", "&7");
        this.getConfig().addDefault("color.highlight", "&6");

        //SAVE
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    @Override
    public void onDisable() {
    }
}
