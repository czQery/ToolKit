package cz.qery.toolkit;

import cz.qery.toolkit.commands.*;
import cz.qery.toolkit.events.*;
import cz.qery.toolkit.lunar.Waypoint;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.Objects;

public final class Main extends JavaPlugin {


    @Override
    public void onEnable() {

        loadConfiguration();


        Objects.requireNonNull(getCommand("crash")).setExecutor(new Crash());
        Objects.requireNonNull(getCommand("crawl")).setExecutor(new Crawl());
        Objects.requireNonNull(getCommand("toolkit")).setExecutor(new ToolKit());
        Objects.requireNonNull(getCommand("skick")).setExecutor(new SKick());
        Objects.requireNonNull(getCommand("sit")).setExecutor(new Sit());
        Objects.requireNonNull(getCommand("troll")).setExecutor(new Troll());
        Objects.requireNonNull(getCommand("pinfo")).setExecutor(new PInfo());
        Objects.requireNonNull(getCommand("rp")).setExecutor(new RP());
        Objects.requireNonNull(getCommand("lunar")).setExecutor(new Lunar());

        //Aliases
        Objects.requireNonNull(getCommand("gmc")).setExecutor(new Aliases());
        Objects.requireNonNull(getCommand("gms")).setExecutor(new Aliases());
        Objects.requireNonNull(getCommand("gma")).setExecutor(new Aliases());
        Objects.requireNonNull(getCommand("gmsp")).setExecutor(new Aliases());

        new Interact(this);
        new Join(this);
        new Leave(this);
        new Move(this);
        new ChannelRegister(this);
        new EntityDismount(this);
        new WorldChange(this);

        Messenger ms = Bukkit.getMessenger();

        ms.registerIncomingPluginChannel(this, "minecraft:brand", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "fml:handshake", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "fml:loginwrapper", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "fml:play", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "lunarclient:pm", new ChannelListener());

        ms.registerOutgoingPluginChannel(this, "minecraft:brand");
        ms.registerOutgoingPluginChannel(this, "fml:handshake");
        ms.registerOutgoingPluginChannel(this, "fml:loginwrapper");
        ms.registerOutgoingPluginChannel(this, "fml:play");
        ms.registerOutgoingPluginChannel(this, "lunarclient:pm");



        //bStats
        int pluginId = 11896;
        Metrics metrics = new Metrics(this, pluginId);

        Waypoint.Load();
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> Waypoint.Send(), 0, 1200);
    }

    public void loadConfiguration() {
        saveDefaultConfig();

        //SAVE
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        String b = this.getConfig().getString("color.bracket");
        String n = this.getConfig().getString("color.name");
        Tools.log(b+"["+n+"ToolKit"+b+"] &aConfig loaded!");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
