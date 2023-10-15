package me.ocel.capturetheflag.gameMap;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public interface GameMap {
    boolean load();
    void unLoad();

    boolean restoreFromSource();

    boolean isLoaded();
    World getWorld();
}
