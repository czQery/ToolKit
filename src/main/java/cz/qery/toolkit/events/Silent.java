package cz.qery.toolkit.events;

import cz.qery.toolkit.Main;
import cz.qery.toolkit.Vnsh;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Silent implements Listener {
    private Main plugin;

    public Silent(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player && Vnsh.Enabled((Player) e.getTarget())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && Vnsh.Enabled((Player)e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (Vnsh.Enabled(e.getEntity().getKiller()) || Vnsh.Enabled(e.getEntity().getPlayer())) {
            e.deathMessage(Component.text(""));
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player && Vnsh.Enabled((Player)e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (Vnsh.Enabled(e.getPlayer()) && e.getAction() == Action.PHYSICAL) {
            switch (e.getClickedBlock().getBlockData().getMaterial()) {
                case POLISHED_BLACKSTONE_PRESSURE_PLATE, ACACIA_PRESSURE_PLATE, BIRCH_PRESSURE_PLATE, CRIMSON_PRESSURE_PLATE, DARK_OAK_PRESSURE_PLATE, HEAVY_WEIGHTED_PRESSURE_PLATE, JUNGLE_PRESSURE_PLATE, LIGHT_WEIGHTED_PRESSURE_PLATE, MANGROVE_PRESSURE_PLATE, OAK_PRESSURE_PLATE, SPRUCE_PRESSURE_PLATE, STONE_PRESSURE_PLATE, WARPED_PRESSURE_PLATE:
                    e.setCancelled(true);
                    break;
                case TRIPWIRE:
                    e.setCancelled(true);
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent e) {
        if (Vnsh.Enabled(e.getPlayer())) {
            e.message(null);
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player && Vnsh.Enabled((Player)e.getEntity())) {
            e.setCancelled(true);
        }
    }
}
