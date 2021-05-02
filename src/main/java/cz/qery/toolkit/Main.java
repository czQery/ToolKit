package cz.qery.toolkit;

import cz.qery.toolkit.commands.*;
import cz.qery.toolkit.events.*;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfiguration();

        String b = this.getConfig().getString("color.bracket");
        String n = this.getConfig().getString("color.name");


        getCommand("crash").setExecutor(new Crash());
        getCommand("crawl").setExecutor(new Crawl());
        getCommand("toolkit").setExecutor(new ToolKit());
        getCommand("skick").setExecutor(new SKick());
        getCommand("sit").setExecutor(new Sit());
        getCommand("troll").setExecutor(new Troll());
        getCommand("pinfo").setExecutor(new PInfo());
        getCommand("rp").setExecutor(new RP());

        new Interact(this);
        new Join(this);
        new Leave(this);
        new Move(this);
        new ChannelRegister(this);

        Messenger ms = Bukkit.getMessenger();

        ms.registerIncomingPluginChannel(this, "minecraft:brand", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "fml:handshake", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "fml:loginwrapper", new ChannelListener());
        ms.registerIncomingPluginChannel(this, "fml:play", new ChannelListener());

        ms.registerOutgoingPluginChannel(this, "minecraft:brand");
        ms.registerOutgoingPluginChannel(this, "fml:handshake");
        ms.registerOutgoingPluginChannel(this, "fml:loginwrapper");
        ms.registerOutgoingPluginChannel(this, "fml:play");
    }

    public void loadConfiguration() {
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

        String b = this.getConfig().getString("color.bracket");
        String n = this.getConfig().getString("color.name");
        Tools.log(b+"["+n+"ToolKit"+b+"] &aConfig loaded!");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
