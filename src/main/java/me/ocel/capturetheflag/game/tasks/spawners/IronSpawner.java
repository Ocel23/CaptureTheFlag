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

public class IronSpawner implements Spawner{

    private final CaptureTheFlag plugin;
    private ArmorStand ironSpawnerHologram1;

    private ArmorStand ironSpawnerHologram2;

    private final GameMap gameMap;

    private final FileConfiguration configuration;

    private Location spawnerlocation;

    private Location hologramLocation1;

    private Location hologramLocation2;

    private int seconds;

    public IronSpawner(CaptureTheFlag plugin, GameMap gameMap, String x, String y, String z) {
        this.seconds = 200;
        this.plugin = plugin;
        this.gameMap = gameMap;
        this.configuration = plugin.getConfig();
        spawnerlocation = new Location(gameMap.getWorld(), this.configuration.getDouble(x), configuration.getDouble(y), configuration.getDouble(z));
        hologramLocation1 = new Location(gameMap.getWorld(),  this.configuration.getDouble(x) + 0.5, configuration.getDouble(y) - 1, configuration.getDouble(z) + 0.5);
        hologramLocation2 = new Location(gameMap.getWorld(),  this.configuration.getDouble(x) + 0.5, configuration.getDouble(y), configuration.getDouble(z) + 0.5);
    }

    public void createHolograms() {
        ironSpawnerHologram1 = (ArmorStand) gameMap.getWorld().spawnEntity(hologramLocation1, EntityType.ARMOR_STAND);
        ironSpawnerHologram1.setVisible(false);
        ironSpawnerHologram1.setCustomNameVisible(true);
        ironSpawnerHologram2 = (ArmorStand) gameMap.getWorld().spawnEntity(hologramLocation2, EntityType.ARMOR_STAND);
        ironSpawnerHologram2.setVisible(false);
        ironSpawnerHologram2.setCustomNameVisible(true);
        ironSpawnerHologram2.setCustomName(ChatColor.GRAY + "" + ChatColor.BOLD + "Iron");
    }

    public void spawnItem() {
        if (seconds == 0) {
            ironSpawnerHologram1.setCustomName(ChatColor.GRAY + "Spawn");
            Uttils.spawnItem(spawnerlocation, new ItemStack(Material.IRON_INGOT), gameMap);
            seconds = 200;
        }
        seconds = seconds - 20;
        ironSpawnerHologram1.setCustomName(ChatColor.WHITE + "Další spawn za " + ChatColor.GRAY + seconds / 20 + ChatColor.WHITE + " s");
    }
}
