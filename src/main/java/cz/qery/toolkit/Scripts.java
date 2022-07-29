package cz.qery.toolkit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cz.qery.toolkit.lunar.Waypoint;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Scripts {
    static Main plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String t = plugin.getConfig().getString("color.text");
    static String h = plugin.getConfig().getString("color.highlight");


    public static List<Waypoint> waypoints = new ArrayList<>();

    @SuppressWarnings("deprecation")
    public static void bCheck(Player p) {
        World world = p.getWorld();
        int x = p.getLocation().getBlockX();
        int y = p.getLocation().getBlockY();
        int z = p.getLocation().getBlockZ();

        Location d1 = new Location(world, x, y - 1, z);
        Location d2 = new Location(world, x, y - 2, z);

        if (p.isOnGround()) {
            if (p.getLocation().getY() % 1 > 0.25) {
                p.setMetadata("crawl", new FixedMetadataValue(plugin, false));
                p.sendMessage(Tools.chat(b + "[" + n + "CRAWL" + b + "]" + t + " Crawl mode has been turned &cOFF" + t + "!"));
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() + 1, p.getLocation().getBlockZ());
                if (loc.getBlock().getType() == Material.BARRIER) {
                    loc.getBlock().setType(Material.AIR);
                }
            }
        }

        if (d1.getBlock().isEmpty() && d2.getBlock().isEmpty()) {
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

            JsonObject json = new JsonParser().parse(response.body()).getAsJsonObject();
            String version = Bukkit.getServer().getPluginManager().getPlugin("ToolKit").getDescription().getVersion();
            if (!("v"+version).equals(json.get("tag_name").getAsString())) {
                Tools.log(b+"-------["+n+"TOOLKIT"+b+"]-------");
                Tools.log(b+"- "+t+"Latest version "+h+json.get("tag_name").getAsString().replaceFirst("v", ""));
                Tools.log(b+"- "+t+"This version "+h+version);
                Tools.log(b+"- "+t+"Download newer version from GitHub "+h+"https://github.com/czQery/ToolKit/releases");
                Tools.log(b+"----------------------");
            }
        } catch (Exception e) {
            Tools.log(b+"["+n+"ToolKit"+b+"] &cAPI version check failed");
        }
    }

    public static void crash(Player p) throws InterruptedException {
        EntityPlayer p_entity = ((CraftPlayer) p).getHandle();
        Location loc = p.getLocation();
        /*

        for (int i = 0; i < 100; i++) {
            PacketPlayOutExplosion packet = new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, Collections.EMPTY_LIST, new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE));
            p_entity.b.a(packet);
        }


        Thread.sleep(1000);

         */

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
        }
        /*

        Thread.sleep(1000);

        for (int i = 0; i < 30000; i++) {
            EntityExperienceOrb dd = new EntityExperienceOrb(p_entity.s, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), Integer.MAX_VALUE);
            PacketPlayOutSpawnEntityExperienceOrb packet = new PacketPlayOutSpawnEntityExperienceOrb(dd);
            p_entity.b.a(packet);
        }

         */

    }
}
