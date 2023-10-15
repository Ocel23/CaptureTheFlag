package me.ocel.capturetheflag.game.utils;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.gameMap.GameMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;


import java.util.Arrays;
import java.util.Iterator;

public class ProtectSettings implements Listener {

    private final CaptureTheFlag plugin;
    
    private final FileConfiguration configuration;

    private final GameMap gameMap;

    private final Material[] materials = new Material[]{Material.BLUE_WOOL, Material.RED_WOOL, Material.OAK_PLANKS, Material.TERRACOTTA, Material.END_STONE, Material.SPONGE};
    
    public ProtectSettings(CaptureTheFlag plugin, GameMap gameMap) {
        this.gameMap = gameMap;
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
    }

    //protect placing blocks from important object in game
    @EventHandler
    private void onPlace(BlockPlaceEvent e) {

        Block block = e.getBlock();

        Location blockLocation = block.getLocation();

        if (e.getPlayer().hasPermission("capturetheflag.*")) {
            return;
        }

        if (protectLocation(blockLocation)) {
            e.setCancelled(true);
        }
    }


    //protect blocks breaking, only allow to block in shops
    @EventHandler
    private void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(gameMap.getWorld().getName()) && !(e.getPlayer().hasPermission("capturetheflag.*"))) {
            if (Arrays.asList(materials).contains(e.getBlock().getType())) {
                e.setCancelled(false);
                return;
            }
            e.setCancelled(true);
        }
    }

    //method for cancel drop item in spectators mode
    @EventHandler
    private void onDropItem(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType() == Material.CLOCK || e.getItemDrop().getItemStack().getType() == Material.COMPASS) {
            e.setCancelled(true);
        }
    }

    //protect tnt breaking blocks, only allow blocks from shop
    @EventHandler
    private void onExplode(EntityExplodeEvent e) {
            Iterator<Block> iterator = e.blockList().iterator();
            while (iterator.hasNext()) {
                Block block = iterator.next();
                if (!(Arrays.asList(materials).contains(block.getType()))) {
                    iterator.remove();
                }
            }
    }

    @EventHandler
    private void onEntityDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity().getType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.PLAYER) {

            Player damagedPlayer = (Player) e.getEntity();

            Player damagerPlayer = (Player) e.getDamager();

            String damagedPlayerTeamColor = damagedPlayer.getPlayerListName().substring(1, 2);

            String damagerPlayerTeamColor = damagerPlayer.getPlayerListName().substring(1, 2);

            if (damagedPlayerTeamColor.equalsIgnoreCase(damagerPlayerTeamColor)) {
                e.setCancelled(true);
            }
        }
    }

    //helper function for protect single location
    private boolean protectLocation(Location blockLocation) {

        Location[] locations = new Location[7];

        Location diamondLocation = createLocation(this.configuration.getDouble("diamondSpawnerLocation.x"), this.configuration.getDouble("diamondSpawnerLocation.y"), this.configuration.getDouble("diamondSpawnerLocation.z"));

        Location ironLocation1 = createLocation(this.configuration.getDouble("ironSpawner1Location.x"), this.configuration.getDouble("ironSpawner1Location.y"), this.configuration.getDouble("ironSpawner1Location.z"));

        Location ironLocation2 = createLocation(this.configuration.getDouble("ironSpawner2Location.x"), this.configuration.getDouble("ironSpawner2Location.y"), this.configuration.getDouble("ironSpawner2Location.z"));

        Location copperLocation1 = createLocation(this.configuration.getDouble("copperSpawner1Location.x"), this.configuration.getDouble("copperSpawner1Location.y"), this.configuration.getDouble("copperSpawner1Location.z"));
        Location copperLocation2 = createLocation(this.configuration.getDouble("copperSpawner2Location.x"), this.configuration.getDouble("copperSpawner2Location.y"), this.configuration.getDouble("copperSpawner2Location.z"));

        Location redFlaglocation = createLocation(this.configuration.getDouble("redFlagLocation.x"), this.configuration.getDouble("redFlagLocation.y"), this.configuration.getDouble("redFlagLocation.z"));

        Location blueFlaglocation =  createLocation(this.configuration.getDouble("blueFlagLocation.x"), this.configuration.getDouble("blueFlagLocation.y"),this.configuration.getDouble("blueFlagLocation.z"));


        locations[0] = diamondLocation;
        locations[1] = ironLocation1;
        locations[2] = ironLocation2;
        locations[3] = copperLocation1;
        locations[4] = copperLocation2;
        locations[5] = redFlaglocation;
        locations[6] = blueFlaglocation;


        boolean cancel = false;

        for (Location location : locations) {
            if (
                    blockLocation.getBlockX() <= location.getBlockX() + 3 && blockLocation.getBlockX() >= location.getBlockX() - 3
                            && blockLocation.getBlockZ() <= location.getBlockZ() + 3 && blockLocation.getBlockZ() >= location.getBlockZ() - 3
                            && blockLocation.getBlockY() - 3 <= location.getBlockY()
            ) {
                cancel = true;
            }
        }

        return cancel;
    }

    private Location createLocation(double x, double y, double z) {

        return new Location(gameMap.getWorld(), x, y, z);

    }

}
