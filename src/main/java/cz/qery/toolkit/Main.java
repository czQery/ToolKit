package cz.qery.toolkit;

import cz.qery.toolkit.commands.*;
import cz.qery.toolkit.events.*;
import cz.qery.toolkit.lunar.Mod;
import cz.qery.toolkit.lunar.Waypoint;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

public final class Main extends JavaPlugin {

    private BukkitAudiences adventure;

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);

        loadConfiguration();

        Tools.paperCheck();

        try {
            CommandHandler.load(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

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

        //Dynmap
        if(getServer().getPluginManager().getPlugin("dynmap") != null) {
            new DyListener();
        }

        Waypoint.Load();
        Mod.Load();
        CommandBlock.Load();
        Bukkit.getScheduler().runTaskAsynchronously(this, Scripts::checkForUpdate);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, Waypoint::Send, 0, 1200);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, Mod::Send, 0, 36000);
        Bukkit.getScheduler().runTaskTimer(this, Scripts::closeSpam, 0, 1);
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
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scripts.cleanup(p);
        }

        HandlerList.unregisterAll(this);
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
