package cz.qery.toolkit.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cz.qery.toolkit.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

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
        p.getScheduler().execute(plugin, () -> {
            // Sit check
            if (!Objects.equals(p.getMetadata("sit").toString(), "[]")) {
                if (p.getMetadata("sit").getFirst().asInt() != 0) {
                    Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                    for (Entity ent : loc.getChunk().getEntities()) {
                        if (ent.getEntityId() == p.getMetadata("sit").getFirst().asInt()) {
                            ent.getScheduler().execute(plugin, ent::remove, null, 0);
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
        }, null, 0);
    }

    public static void sCheck(Player p) {
        p.getScheduler().execute(plugin, () -> {
            Location loc = p.getLocation();
            int id = p.getMetadata("sit").getFirst().asInt();

            for (Entity ent : loc.getChunk().getEntities()) {
                if (ent.getEntityId() == id) {
                    ent.getScheduler().execute(plugin, ent::remove, null, 0);

                    p.teleportAsync(loc.add(0, 1.7, 0));
                    p.setMetadata("sit", new FixedMetadataValue(plugin, 0));
                    p.sendMessage(Tools.chat(b + "[" + n + "SIT" + b + "]" + t + " Sit mode has been turned &cOFF" + t + "!"));
                    break;
                }
            }
        }, null, 0);
    }

    @SuppressWarnings("deprecation")
    public static void bCheck(Player p) {
        p.getScheduler().execute(plugin, () -> {

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
        }, null, 0);
    }

    public static void bDisable(Player p, Boolean self) {
        p.getScheduler().execute(plugin, () -> {
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
        }, null, 0);
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
        p.getScheduler().execute(plugin, () -> {
            Location loc = p.getLocation();

            Particle[] particles = {
                    Particle.DRAGON_BREATH,
                    Particle.EXPLOSION,
                    Particle.TOTEM_OF_UNDYING,
                    Particle.FIREWORK,
            };

            for (Particle particle : particles) {
                if (particle.name().equals("DRAGON_BREATH")) {
                    p.spawnParticle(particle, loc, Integer.MAX_VALUE, 0, 0, 0, 0, Float.MAX_VALUE, true);
                } else {
                    p.spawnParticle(particle, loc, Integer.MAX_VALUE, 0, 0, 0, 0, null, true);
                }
            }
        }, null, 0);
    }

    public static void addTrueClient(Player p, String client) {
        p.getScheduler().execute(plugin, () -> {
            if (Objects.equals(p.getMetadata("client").toString(), "[]")) {
                p.setMetadata("client", new FixedMetadataValue(plugin, client));
                Tools.log(b + "[" + n + "SERVER" + b + "] " + h + p.getName() + t + " client " + h + client);
            } else if (Objects.equals(p.getMetadata("trueclient").toString(), "[]") && !p.getMetadata("client").getFirst().asString().equalsIgnoreCase(client)) {
                p.setMetadata("trueclient", new FixedMetadataValue(plugin, client));
                Tools.log(b + "[" + n + "SERVER" + b + "] " + h + p.getName() + t + " true client " + h + client);
            }
        }, null, 0);
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
            p.getScheduler().execute(plugin, () -> p.teleportAsync(location), null, 0);
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
