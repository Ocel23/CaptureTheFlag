package me.ocel.capturetheflag.game.tasks.spawners;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.Uttils;
import me.ocel.capturetheflag.gameMap.GameMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class DiamondSpawner implements Spawner{

    private final CaptureTheFlag plugin;
    private ArmorStand diamondSpawnerHologram1;

    private ArmorStand diamondSpawnerHologram2;

    private final GameMap gameMap;

    private final FileConfiguration configuration;

    private Location spawnerlocation;

    private Location hologramLocation;

    private int seconds;

    public DiamondSpawner(CaptureTheFlag plugin, GameMap gameMap) {
        this.seconds = 400;
        this.plugin = plugin;
        this.gameMap = gameMap;
        this.configuration = plugin.getConfig();
    }

    public void createHolograms() {
        this.spawnerlocation = new Location(gameMap.getWorld(), this.configuration.getDouble("diamondSpawnerLocation.x"), configuration.getDouble("diamondSpawnerLocation.y"), configuration.getDouble("diamondSpawnerLocation.z"));
        this.hologramLocation = spawnerlocation;
        hologramLocation.subtract(0, 1, 0);
        hologramLocation.add(0.5,0, 0.5);
        diamondSpawnerHologram1 = (ArmorStand) gameMap.getWorld().spawnEntity(hologramLocation, EntityType.ARMOR_STAND);
        diamondSpawnerHologram1.setVisible(false);
        diamondSpawnerHologram1.setGravity(false);
        diamondSpawnerHologram1.setCollidable(false);
        diamondSpawnerHologram1.setCustomNameVisible(true);
        hologramLocation.add(0,0.5, 0);
        diamondSpawnerHologram2 = (ArmorStand) gameMap.getWorld().spawnEntity(hologramLocation, EntityType.ARMOR_STAND);
        diamondSpawnerHologram2.setVisible(false);
        diamondSpawnerHologram2.setGravity(false);
        diamondSpawnerHologram2.setCollidable(false);
        diamondSpawnerHologram2.setCustomNameVisible(true);
        diamondSpawnerHologram2.setCustomName(ChatColor.AQUA + "" + ChatColor.BOLD + "Diamond");
    }


    public void spawnItem() {
        if (seconds == 0) {
            diamondSpawnerHologram1.setCustomName(ChatColor.AQUA + "Spawn");
            Uttils.spawnItem(spawnerlocation, new ItemStack(Material.DIAMOND), gameMap);
            seconds = 400;
        }
        seconds = seconds - 20;
        diamondSpawnerHologram1.setCustomName(ChatColor.WHITE + "Další spawn za " + ChatColor.AQUA + seconds / 20 + ChatColor.WHITE + " s");
    }

}
