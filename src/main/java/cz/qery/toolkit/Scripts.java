package cz.qery.toolkit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketUpdateWorld;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointAdd;
import cz.qery.toolkit.lunar.Waypoint;
import net.minecraft.network.protocol.game.PacketPlayOutExplosion;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Scripts {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String t = plugin.getConfig().getString("color.text");
    static String h = plugin.getConfig().getString("color.highlight");

    public static void cleanup(Player p) {
        // Sit check
        if (!Objects.equals(p.getMetadata("sit").toString(), "[]")) {
            if (p.getMetadata("sit").get(0).asInt() != 0) {
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                for (Entity ent: loc.getChunk().getEntities()){
                    if (ent.getEntityId() == p.getMetadata("sit").get(0).asInt()) {
                        ent.remove();
                    }
                }
            }
        }

        // Crawl check
        if (!Objects.equals(p.getMetadata("crawl").toString(), "[]")) {
            if (p.getMetadata("crawl").get(0).asBoolean()) {
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
                p.setMetadata("crawl", new FixedMetadataValue(plugin, false));
                Scripts.bCheck(p);
                if (loc.getBlock().getType() == Material.BARRIER){loc.getBlock().setType(Material.AIR);}
            }
        }

        // Client
        p.removeMetadata("client", plugin);
        p.removeMetadata("trueclient", plugin);
    }

    public static void sCheck(Player p, Boolean nw) {
        if (!Objects.equals(p.getMetadata("sit").toString(), "[]") && p.getMetadata("sit").get(0).asInt() != 0 || nw) {
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
    }

   /* public static HashMap<UUID, BlockPosition[]> bMap = new HashMap<UUID,BlockPosition[]>();*/

    @SuppressWarnings("deprecation")
    public static void bCheck(Player p) {
        World world = p.getWorld();
        int x = p.getLocation().getBlockX();
        int y = p.getLocation().getBlockY();
        int z = p.getLocation().getBlockZ();

        Location d1 = new Location(world, x, y - 1, z);
        Location d2 = new Location(world, x, y - 2, z);

        if (d1.getBlock().isEmpty() && d2.getBlock().isEmpty() || (p.isOnGround() && p.getLocation().getY() % 1 > 0.25)) {
            p.setMetadata("crawl", new FixedMetadataValue(plugin, false));
            p.sendMessage(Tools.chat(b + "[" + n + "CRAWL" + b + "]" + t + " Crawl mode has been turned &cOFF" + t + "!"));
            Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() + 1, p.getLocation().getBlockZ());
            if (loc.getBlock().getType() == Material.BARRIER) {
                loc.getBlock().setType(Material.AIR);
            }
        }


        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                Location l0 = new Location(world, x, y + i, z);
                if (l0.getBlock().getType() == Material.BARRIER) {
                    l0.getBlock().setType(Material.AIR);
                }
            }
            Location l1 = new Location(world, x + 1, y + i, z);
            if (l1.getBlock().getType() == Material.BARRIER) {
                l1.getBlock().setType(Material.AIR);
            }
            //
            Location l2 = new Location(world, x - 1, y + i, z);
            if (l2.getBlock().getType() == Material.BARRIER) {
                l2.getBlock().setType(Material.AIR);
            }
            //
            Location l3 = new Location(world, x, y + i, z + 1);
            if (l3.getBlock().getType() == Material.BARRIER) {
                l3.getBlock().setType(Material.AIR);
            }
            //
            Location l4 = new Location(world, x, y + i, z - 1);
            if (l4.getBlock().getType() == Material.BARRIER) {
                l4.getBlock().setType(Material.AIR);
            }
            //
            Location l5 = new Location(world, x + 1, y + i, z + 1);
            if (l5.getBlock().getType() == Material.BARRIER) {
                l5.getBlock().setType(Material.AIR);
            }
            //
            Location l6 = new Location(world, x + 1, y + i, z - 1);
            if (l6.getBlock().getType() == Material.BARRIER) {
                l6.getBlock().setType(Material.AIR);
            }
            //
            Location l7 = new Location(world, x - 1, y + i, z + 1);
            if (l7.getBlock().getType() == Material.BARRIER) {
                l7.getBlock().setType(Material.AIR);
            }
            //
            Location l8 = new Location(world, x - 1, y + i, z - 1);
            if (l8.getBlock().getType() == Material.BARRIER) {
                l8.getBlock().setType(Material.AIR);
            }
        }
    }

    public static void checkForUpdate() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://api.github.com/repos/czQery/ToolKit/releases/latest")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            String version = Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("ToolKit")).getDescription().getVersion();
            if (!("v" + version).equals(json.get("tag_name").getAsString())) {
                Tools.log(b + "-------[" + n + "TOOLKIT" + b + "]-------");
                Tools.log(b + "- " + t + "Latest version " + h + json.get("tag_name").getAsString().replaceFirst("v", ""));
                Tools.log(b + "- " + t + "This version " + h + version);
                Tools.log(b + "- " + t + "Download newer version from GitHub " + h + "https://github.com/czQery/ToolKit/releases");
                Tools.log(b + "----------------------");
            }
        } catch (Exception e) {
            Tools.log(b + "[" + n + "ToolKit" + b + "] &cAPI version check failed");
        }
    }

    public static void crash(Player p) throws InterruptedException {
        EntityPlayer p_entity = ((CraftPlayer) p).getHandle();
        Location loc = p.getLocation();

        for (int i = 0; i < 100; i++) {
            PacketPlayOutExplosion packet = new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, Collections.emptyList(), new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE));
            p_entity.b.a(packet);
        }

        Thread.sleep(500);

        for (int i = 0; i < 100; i++) {
            p.spawnParticle(Particle.EXPLOSION_HUGE, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.EXPLOSION_LARGE, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.EXPLOSION_NORMAL, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.TOTEM, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.SMOKE_LARGE, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.SMOKE_NORMAL, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.DRAGON_BREATH, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.CLOUD, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.CRIT, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.CRIT_MAGIC, loc, Integer.MAX_VALUE);
            p.spawnParticle(Particle.MOB_APPEARANCE, loc, Integer.MAX_VALUE);
        }

        Thread.sleep(500);

        for (int i = 0; i < 30000; i++) {
            EntityExperienceOrb dd = new EntityExperienceOrb(p_entity.s, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), Integer.MAX_VALUE);
            PacketPlayOutSpawnEntityExperienceOrb packet = new PacketPlayOutSpawnEntityExperienceOrb(dd);
            p_entity.b.a(packet);
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

    public static void sendWaypoints(Player p, List<Waypoint> waypoints) {
        for (Waypoint waypoint : waypoints) {
            Tools.sendLunarPacket(p, new LCPacketUpdateWorld(p.getWorld().getUID().toString()));
            if (Bukkit.getWorld(waypoint.world()) == null) {
                Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypoint " + h + waypoint.name() + t + " has invalid world " + h + waypoint.world());
            } else if (!waypoint.color().contains("#") && !waypoint.color().matches("^[a-fA-F0-9#]{0,7}$")) {
                Tools.log(b + "[" + n + "ToolKit" + b + "] " + t + "Lunar waypoint " + h + waypoint.name() + t + " has invalid color " + h + waypoint.color());
            } else {
                Tools.sendLunarPacket(p, new LCPacketWaypointAdd(waypoint.name(), Objects.requireNonNull(Bukkit.getWorld(waypoint.world())).getUID().toString(), Integer.parseInt(waypoint.color().replaceFirst("#", ""), 16), waypoint.x(), waypoint.y(), waypoint.z(), true, true));
            }
        }
    }
}
