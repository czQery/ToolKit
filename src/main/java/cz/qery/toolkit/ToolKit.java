package cz.qery.toolkit;

import cz.qery.toolkit.commands.Crasher;
import cz.qery.toolkit.commands.Help;
import cz.qery.toolkit.commands.SKick;
import cz.qery.toolkit.commands.Troll;
import cz.qery.toolkit.events.Join;
import cz.qery.toolkit.events.Leave;
import org.bukkit.plugin.java.JavaPlugin;


public final class ToolKit extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfiguration();

        getCommand("skick").setExecutor(new SKick());
        getCommand("crasher").setExecutor(new Crasher());
        getCommand("toolkit").setExecutor(new Help());
        getCommand("troll").setExecutor(new Troll());


        new Join(this);
        new Leave(this);
    }
    public void loadConfiguration(){
        this.getConfig().addDefault("world", "world");
        this.getConfig().addDefault("welcome", false);
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    @Override
    public void onDisable() {
    }
}
