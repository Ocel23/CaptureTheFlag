package me.ocel.capturetheflag.lobby.utils;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.Uttils;
import me.ocel.capturetheflag.lobby.Lobby;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.WHITE_BANNER) {
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

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        if (e.getTo() == null) {
            return;
        }

        if (player.getWorld().getName().equalsIgnoreCase(this.configuration.getString("nameOfLobbyWorld"))) {


            if (e.getTo().getBlockY() <= 0) {

                Location spawnLobbyLocation = new Location(plugin.getServer().getWorld(this.configuration.getString("nameOfLobbyWorld")), this.configuration.getDouble("lobbySpawnLocation.x"), this.configuration.getDouble("lobbySpawnLocation.y"), this.configuration.getDouble("lobbySpawnLocation.z"), (float) this.configuration.getDouble("lobbySpawnLocation.yaw"), (float) this.configuration.getDouble("lobbySpawnLocation.pitch"));

                player.teleport(spawnLobbyLocation);

            }
        }

    }
}
