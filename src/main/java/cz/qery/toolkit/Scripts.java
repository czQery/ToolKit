package cz.qery.toolkit;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class Scripts {
    static Plugin plugin = Main.getPlugin(Main.class);
    static String b = plugin.getConfig().getString("color.bracket");
    static String n = plugin.getConfig().getString("color.name");
    static String t = plugin.getConfig().getString("color.text");

    @SuppressWarnings("deprecation")
    public static void bCheck(Player p) {
        World world = p.getWorld();
        int x = p.getLocation().getBlockX();
        int y = p.getLocation().getBlockY();
        int z = p.getLocation().getBlockZ();

        Location d1 = new Location(world, x, y-1, z);
        Location d2 = new Location(world, x, y-2, z);

        if (p.isOnGround()) {
            if (p.getLocation().getY() % 1 > 0.25) {
                p.setMetadata("crawl", new FixedMetadataValue(plugin, false));
                p.sendMessage(Utils.chat(b+"["+n+"CRAWL"+b+"]"+t+" Crawl mode has been turned &cOFF"+t+"!"));
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
                if (loc.getBlock().getType() == Material.BARRIER){loc.getBlock().setType(Material.AIR);}
            }
        }

        if (d1.getBlock().isEmpty() && d2.getBlock().isEmpty()) {
            p.setMetadata("crawl", new FixedMetadataValue(plugin, false));
            p.sendMessage(Utils.chat(b+"["+n+"CRAWL"+b+"]"+t+" Crawl mode has been turned &cOFF"+t+"!"));
            Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
            if (loc.getBlock().getType() == Material.BARRIER){loc.getBlock().setType(Material.AIR);}
        }


        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                Location l0 = new Location(world, x, y+i, z);
                if (l0.getBlock().getType() == Material.BARRIER){l0.getBlock().setType(Material.AIR);}
            }
            Location l1 = new Location(world, x+1, y+i, z);
            if (l1.getBlock().getType() == Material.BARRIER){l1.getBlock().setType(Material.AIR);}
            //
            Location l2 = new Location(world, x-1, y+i, z);
            if (l2.getBlock().getType() == Material.BARRIER){l2.getBlock().setType(Material.AIR);}
            //
            Location l3 = new Location(world, x, y+i, z+1);
            if (l3.getBlock().getType() == Material.BARRIER){l3.getBlock().setType(Material.AIR);}
            //
            Location l4 = new Location(world, x, y+i, z-1);
            if (l4.getBlock().getType() == Material.BARRIER){l4.getBlock().setType(Material.AIR);}
            //
            Location l5 = new Location(world, x+1, y+i, z+1);
            if (l5.getBlock().getType() == Material.BARRIER){l5.getBlock().setType(Material.AIR);}
            //
            Location l6 = new Location(world, x+1, y+i, z-1);
            if (l6.getBlock().getType() == Material.BARRIER){l6.getBlock().setType(Material.AIR);}
            //
            Location l7 = new Location(world, x-1, y+i, z+1);
            if (l7.getBlock().getType() == Material.BARRIER){l7.getBlock().setType(Material.AIR);}
            //
            Location l8 = new Location(world, x-1, y+i, z-1);
            if (l8.getBlock().getType() == Material.BARRIER){l8.getBlock().setType(Material.AIR);}
        }
    }
}
