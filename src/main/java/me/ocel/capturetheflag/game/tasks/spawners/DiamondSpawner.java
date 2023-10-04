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

    private Location hologramLocation1;

    private Location hologramLocation2;

    private int seconds;

    public DiamondSpawner(CaptureTheFlag plugin, GameMap gameMap) {
        this.seconds = 400;
        this.plugin = plugin;
        this.gameMap = gameMap;
        this.configuration = plugin.getConfig();
        spawnerlocation = new Location(gameMap.getWorld(), this.configuration.getDouble("diamondSpawnerLocation.x"), configuration.getDouble("diamondSpawnerLocation.y"), configuration.getDouble("diamondSpawnerLocation.z"));
        hologramLocation1 = new Location(gameMap.getWorld(), this.configuration.getDouble("diamondSpawnerLocation.x") + 0.5, configuration.getDouble("diamondSpawnerLocation.y") - 1, configuration.getDouble("diamondSpawnerLocation.z") + 0.5);
        hologramLocation2 = new Location(gameMap.getWorld(), this.configuration.getDouble("diamondSpawnerLocation.x") + 0.5, configuration.getDouble("diamondSpawnerLocation.y"), configuration.getDouble("diamondSpawnerLocation.z") + 0.5);

    }

    public void createHolograms() {
        diamondSpawnerHologram1 = (ArmorStand) gameMap.getWorld().spawnEntity(hologramLocation1, EntityType.ARMOR_STAND);
        diamondSpawnerHologram1.setVisible(false);
        diamondSpawnerHologram1.setCustomNameVisible(true);
        diamondSpawnerHologram2 = (ArmorStand) gameMap.getWorld().spawnEntity(hologramLocation2, EntityType.ARMOR_STAND);
        diamondSpawnerHologram2.setVisible(false);
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
