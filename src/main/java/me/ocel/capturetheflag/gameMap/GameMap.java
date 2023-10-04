package me.ocel.capturetheflag.gameMap;

import org.bukkit.World;

public interface GameMap {
    boolean load();
    void unLoad();

    boolean restoreFromSource();

    boolean isLoaded();
    World getWorld();
}
