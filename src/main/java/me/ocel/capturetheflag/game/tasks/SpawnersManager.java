package me.ocel.capturetheflag.game.tasks;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.game.tasks.spawners.CopperSpawner;
import me.ocel.capturetheflag.game.tasks.spawners.DiamondSpawner;
import me.ocel.capturetheflag.game.tasks.spawners.IronSpawner;
import me.ocel.capturetheflag.gameMap.GameMap;
import org.bukkit.configuration.file.FileConfiguration;

public class SpawnersManager {

    private final CaptureTheFlag plugin;

    private final FileConfiguration configuration;

    private final GameMap gameMap;

    private final DiamondSpawner diamondSpawner;

    private final IronSpawner ironSpawner1;

    private final IronSpawner ironSpawner2;

    private final CopperSpawner copperSpawner1;

    private final CopperSpawner copperSpawner2;

    public SpawnersManager(CaptureTheFlag plugin, GameMap gameMap) {
        this.gameMap = gameMap;
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
        this.diamondSpawner = new DiamondSpawner(plugin, gameMap);
        this.ironSpawner1 = new IronSpawner(plugin, gameMap, "ironSpawner1Location.x", "ironSpawner1Location.y", "ironSpawner1Location.z");
        this.ironSpawner2 = new IronSpawner(plugin, gameMap, "ironSpawner2Location.x", "ironSpawner2Location.y", "ironSpawner2Location.z");
        this.copperSpawner1 = new CopperSpawner(plugin, gameMap, "copperSpawner1Location.x", "copperSpawner1Location.y", "copperSpawner1Location.z");
        this.copperSpawner2 = new CopperSpawner(plugin, gameMap, "copperSpawner2Location.x", "copperSpawner2Location.y", "copperSpawner2Location.z");
    }


    //method for create holograms
    public void createHolograms() {
        diamondSpawner.createHolograms();
        ironSpawner1.createHolograms();
        ironSpawner2.createHolograms();

    }

    //method for spawn items
    public void spawnIngots() {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            diamondSpawner.spawnItem();
            ironSpawner1.spawnItem();
            ironSpawner2.spawnItem();
            copperSpawner1.spawnItem();
            copperSpawner2.spawnItem();
        }, 0L, 20L);
    }



}
