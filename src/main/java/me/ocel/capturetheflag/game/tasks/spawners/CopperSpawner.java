package me.ocel.capturetheflag.game.tasks.spawners;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.Uttils;
import me.ocel.capturetheflag.gameMap.GameMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class CopperSpawner implements Spawner{

    private final CaptureTheFlag plugin;

    private final GameMap gameMap;

    private final FileConfiguration configuration;

    private Location spawnerlocation;

    public CopperSpawner(CaptureTheFlag plugin, GameMap gameMap, String x, String y, String z) {
        this.plugin = plugin;
        this.gameMap = gameMap;
        this.configuration = plugin.getConfig();
        spawnerlocation = new Location(gameMap.getWorld(), this.configuration.getDouble(x), configuration.getDouble(y), configuration.getDouble(z));
    }

    public void spawnItem() {
        Uttils.spawnItem(spawnerlocation, new ItemStack(Material.COPPER_INGOT), gameMap);
    }

}
