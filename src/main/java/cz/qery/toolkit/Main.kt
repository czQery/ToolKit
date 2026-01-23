package cz.qery.toolkit

import cz.qery.toolkit.loader.CommandsBlock
import cz.qery.toolkit.loader.Commands
import cz.qery.toolkit.events.*
import cz.qery.toolkit.helper.Other
import cz.qery.toolkit.loader.Pl3xWaypoint
import cz.qery.toolkit.loader.LunarNotification
import cz.qery.toolkit.loader.LunarStaff
import cz.qery.toolkit.loader.LunarWaypoint
import net.pl3x.map.core.Pl3xMap
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin


class Main : JavaPlugin() {
    override fun onEnable() {
        loadConfiguration()

        try {
            Commands.load(this)
        } catch (e: NoSuchFieldException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

        Interact(this)
        Join(this)
        Leave(this)
        Move(this)
        ChannelRegister(this)
        EntityDismount(this)
        Command(this)
        Silent(this)
        Respawn(this)

        val ms = Bukkit.getMessenger()

        ms.registerIncomingPluginChannel(this, "minecraft:brand", ChannelListener())
        ms.registerIncomingPluginChannel(this, "fml:handshake", ChannelListener())
        ms.registerIncomingPluginChannel(this, "fml:loginwrapper", ChannelListener())
        ms.registerIncomingPluginChannel(this, "fml:play", ChannelListener())
        ms.registerIncomingPluginChannel(this, "lunarclient:pm", ChannelListener())
        ms.registerIncomingPluginChannel(this, "badlion:mods", ChannelListener())

        ms.registerOutgoingPluginChannel(this, "minecraft:brand")
        ms.registerOutgoingPluginChannel(this, "fml:handshake")
        ms.registerOutgoingPluginChannel(this, "fml:loginwrapper")
        ms.registerOutgoingPluginChannel(this, "fml:play")
        ms.registerOutgoingPluginChannel(this, "lunarclient:pm")
        ms.registerOutgoingPluginChannel(this, "badlion:mods")

        // bStats
        Metrics(this, 11896)

        LunarWaypoint.Load()
        LunarStaff.Load()
        LunarNotification.Load()
        CommandsBlock.Load()

        // Pl3xMap
        if (server.pluginManager.getPlugin("pl3xmap") != null) {
            Pl3xMap.api().worldRegistry.get("world")?.layerRegistry?.register("toolkit_world",
                Pl3xWaypoint("toolkit_world")
            )
            Pl3xMap.api().worldRegistry.get("world_nether")?.layerRegistry?.register("toolkit_world_nether",
                Pl3xWaypoint("toolkit_world_nether")
            )
            Pl3xMap.api().worldRegistry.get("world_the_end")?.layerRegistry?.register("toolkit_world_the_end",
                Pl3xWaypoint("toolkit_world_the_end")
            )
        }

        ApolloRegister()

        Bukkit.getScheduler().runTaskAsynchronously(this, Runnable { Other.checkForUpdate() })
        Bukkit.getScheduler().runTaskTimer(this, Runnable { Other.closeSpam() }, 0, 1)
    }

    fun loadConfiguration() {
        saveDefaultConfig()

        //SAVE
        config.options().copyDefaults(true)
        saveConfig()

        colors["b"] = this.config.getString("color.bracket")
        colors["n"] = this.config.getString("color.name")
        colors["t"] = this.config.getString("color.text")
        colors["h"] = this.config.getString("color.highlight")

        val b: String? = colors["b"]
        val n: String? = colors["n"]

        Other.Tools.log(b + "[" + n + "ToolKit" + b + "] &aConfig loaded!")
    }

    override fun onDisable() {
        for (p in Bukkit.getOnlinePlayers()) {
            Other.cleanup(p)
        }

        HandlerList.unregisterAll(this)
    }

    companion object {
        @JvmField
        var colors: MutableMap<String?, String?> = HashMap()
    }
}
