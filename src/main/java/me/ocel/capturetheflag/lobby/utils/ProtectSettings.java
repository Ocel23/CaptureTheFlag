package me.ocel.capturetheflag.lobby.utils;

import me.ocel.capturetheflag.CaptureTheFlag;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ProtectSettings implements Listener {

    private final CaptureTheFlag plugin;

    private final FileConfiguration configuration;

    public ProtectSettings(CaptureTheFlag plugin) {
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
    }


    //protect drop items from player
    @EventHandler
    private void onDropItem(PlayerDropItemEvent e) {
        if (e.getItemDrop().getWorld().getName().equalsIgnoreCase(configuration.getString("nameOfLobbyWorld")) && !(e.getPlayer().hasPermission("capturetheflag.*"))) {
            e.setCancelled(true);
        }
    }


    //protect player hunger increasing
    @EventHandler
    private void onHungerBarChange(FoodLevelChangeEvent e) {
        if (e.getEntity().getWorld().getName().equalsIgnoreCase(configuration.getString("nameOfLobbyWorld")) && e.getEntity().getType() == EntityType.PLAYER) {
            e.setCancelled(true);
        }
    }

    //protect player damage
    @EventHandler
    private void onDamage(EntityDamageEvent e) {
        if (e.getEntity().getWorld().getName().equalsIgnoreCase(configuration.getString("nameOfLobbyWorld")) && e.getEntity().getType() == EntityType.PLAYER) {
            e.setCancelled(true);
        }
    }
}
