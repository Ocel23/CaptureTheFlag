package me.ocel.capturetheflag.game.shop.specialItems;

import me.ocel.capturetheflag.CaptureTheFlag;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KamikazePig implements Listener {

    private final CaptureTheFlag plugin;

    public KamikazePig(CaptureTheFlag plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerInteract(BlockPlaceEvent e) {

        Player player = e.getPlayer();

        if (e.getBlock().getType() == Material.PINK_GLAZED_TERRACOTTA) {
            Pig pig = (Pig) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.PIG);
            pig.setInvulnerable(true);
            pig.setCustomNameVisible(true);
            pig.setCustomName(ChatColor.LIGHT_PURPLE + "Kamikaze prase");
            player.getInventory().getItemInMainHand().setType(Material.AIR);
            Block block = e.getBlock();

            pig.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 1, false, true));

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {

                Location location = pig.getLocation();

                pig.getWorld().spawnEntity(location.add(5, 0, 0), EntityType.PRIMED_TNT);
                location = pig.getLocation();
                pig.getWorld().spawnEntity(location.add(-5, 0, 0), EntityType.PRIMED_TNT);
                location = pig.getLocation();
                pig.getWorld().spawnEntity(location.add(0, 0, 5), EntityType.PRIMED_TNT);
                location = pig.getLocation();
                pig.getWorld().spawnEntity(location.add(0, 0, -5), EntityType.PRIMED_TNT);
                location = pig.getLocation();

                pig.getWorld().spawnParticle(Particle.CLOUD, location, 150);

                pig.setHealth(0);

                block.setType(Material.AIR);

                block.getWorld().spawnParticle(Particle.CLOUD, block.getLocation(), 150);


            }, 100L);

        }
    }



}
