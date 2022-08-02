package cz.qery.toolkit;

import cz.qery.toolkit.commands.*;
import cz.qery.toolkit.events.*;
import cz.qery.toolkit.lunar.Mod;
import cz.qery.toolkit.lunar.Waypoint;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
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
        Objects.requireNonNull(getCommand("cmdblock")).setExecutor(new Cmdblock());
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new Vanish());

        //Aliases
        Objects.requireNonNull(getCommand("gmc")).setExecutor(new Aliases());
        Objects.requireNonNull(getCommand("gms")).setExecutor(new Aliases());
        Objects.requireNonNull(getCommand("gma")).setExecutor(new Aliases());
        Objects.requireNonNull(getCommand("gmsp")).setExecutor(new Aliases());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new Aliases());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new Aliases());

        new Interact(this);
        new Join(this);
        new Leave(this);
        new Move(this);
        new ChannelRegister(this);
        new EntityDismount(this);
        new WorldChange(this);
        new Command(this);
        new Silent(this);
        new Respawn(this);

        Messenger ms = Bukkit.getMessenger();

        ms.registerIncomingPluginChannel(this, "minecraft:brand", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "fml:handshake", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "fml:loginwrapper", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "fml:play", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "lunarclient:pm", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "badlion:mods", new ChannelListener());

        ms.registerOutgoingPluginChannel(this, "minecraft:brand");
        ms.registerOutgoingPluginChannel(this, "fml:handshake");
        ms.registerOutgoingPluginChannel(this, "fml:loginwrapper");
        ms.registerOutgoingPluginChannel(this, "fml:play");
        ms.registerOutgoingPluginChannel(this, "lunarclient:pm");
        ms.registerOutgoingPluginChannel(this, "badlion:mods");

        //bStats
        new Metrics(this, 11896);

        Waypoint.Load();
        Mod.Load();
        CommandBlock.Load();
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> Scripts.checkForUpdate());
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> Waypoint.Send(), 0, 1200);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> Mod.Send(), 0, 36000);
    }

    public void loadConfiguration() {
        saveDefaultConfig();

        //SAVE
        getConfig().options().copyDefaults(true);
        saveConfig();

        String b = this.getConfig().getString("color.bracket");
        String n = this.getConfig().getString("color.name");
        Tools.log(b+"["+n+"ToolKit"+b+"] &aConfig loaded!");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
