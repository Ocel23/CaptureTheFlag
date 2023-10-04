package me.ocel.capturetheflag.game.shop.specialItems;

import me.ocel.capturetheflag.CaptureTheFlag;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SnowBall implements Listener {

    private final CaptureTheFlag plugin;

    public SnowBall(CaptureTheFlag plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    private void onProjectileHit(ProjectileHitEvent e) {

        if (e.getHitEntity() == null) {
            return;
        }

        if (e.getEntity().getType() == EntityType.SNOWBALL && e.getHitEntity().getType() == EntityType.PLAYER) {

            Player player = (Player) e.getHitEntity();

            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10, Integer.MAX_VALUE));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, Integer.MAX_VALUE));
            player.sendMessage(ChatColor.GREEN + "Dostal jsi zásah toxickou koulí!");

        }

    }
}
