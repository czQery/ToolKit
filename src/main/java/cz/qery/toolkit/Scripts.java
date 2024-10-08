package cz.qery.toolkit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.protocol.game.PacketPlayOutExplosion;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.EntityTrackerEntry;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Scripts {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = Main.colors.get("b");
    static String n = Main.colors.get("n");
    static String t = Main.colors.get("t");
    static String h = Main.colors.get("h");

    public static void cleanup(Player p) {
        // Sit check
        if (!Objects.equals(p.getMetadata("sit").toString(), "[]")) {
            if (p.getMetadata("sit").get(0).asInt() != 0) {
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                for (Entity ent : loc.getChunk().getEntities()) {
                    if (ent.getEntityId() == p.getMetadata("sit").get(0).asInt()) {
                        ent.remove();
                    }
                }
            }
        }

        // Crawl check
        if (!Objects.equals(p.getMetadata("crawl").toString(), "[]")) {
            if (p.getMetadata("crawl").get(0).asBoolean()) {
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
            if (ent.getEntityId() == p.getMetadata("sit").get(0).asInt()) {
                ent.remove();

                p.teleport(loc.add(0, 1.7, 0));
                p.setMetadata("sit", new FixedMetadataValue(plugin, 0));
                p.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Sit mode has been turned &cOFF" + t + "!"));
            }
        }
    }

    public static HashMap<UUID, Location[]> bMap = new HashMap<>();

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
            Scripts.bCheck(p);
        }

        Scripts.bMap.remove(p.getUniqueId());
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
        EntityPlayer p_entity = ((CraftPlayer) p).getHandle();
        Location loc = p.getLocation();

        List<BlockPosition> list = new ArrayList<>();
        list.add(BlockPosition.a(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()));

        Vec3D vec = new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

        for (int i = 0; i < 100; i++) {
            p_entity.c.b(new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, list, vec, Explosion.Effect.a, Particles.x, Particles.x, SoundEffects.ap));
        }

        Thread.sleep(500);

        for (int i = 0; i < 100; i++) {
            p.spawnParticle(Particle.EXPLOSION, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.TOTEM_OF_UNDYING, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.SMOKE, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.WHITE_SMOKE, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.LARGE_SMOKE, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.DRAGON_BREATH, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.CLOUD, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.CRIT, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.ENCHANTED_HIT, loc, Integer.MAX_VALUE);
        }

        Thread.sleep(500);

        for (int i = 0; i < 30000; i++) {
            EntityExperienceOrb dd = new EntityExperienceOrb(p_entity.cN(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), Integer.MAX_VALUE);
            p_entity.c.b(new PacketPlayOutSpawnEntityExperienceOrb(dd, new EntityTrackerEntry(p_entity.A(), p_entity, Integer.MAX_VALUE, true, null, null)));
        }
    }

    public static void addTrueClient(Player p, String client) {
        if (Objects.equals(p.getMetadata("client").toString(), "[]")) {
            p.setMetadata("client", new FixedMetadataValue(plugin, client));
            Tools.log(b + "[" + n + "SERVER" + b + "] " + h + p.getName() + t + " client " + h + client);
        } else if (Objects.equals(p.getMetadata("trueclient").toString(), "[]") && !p.getMetadata("client").get(0).asString().equalsIgnoreCase(client)) {
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
                if (p.getMetadata("closespam").get(0).asBoolean()) {
                    p.closeInventory();
                }
            }
        }
    }
}
