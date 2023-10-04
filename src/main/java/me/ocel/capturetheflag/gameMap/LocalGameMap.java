package me.ocel.capturetheflag.gameMap;

import me.ocel.capturetheflag.CaptureTheFlag;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

public class LocalGameMap implements GameMap {
    private final File sourceWorldFolder;

    private File activeWorldFolder;

    private World bukkitWorld;

    private final CaptureTheFlag plugin;

    public LocalGameMap(File worldFolder, String worldName, boolean loadOnInit, CaptureTheFlag plugin) {
        this.plugin = plugin;
        this.sourceWorldFolder = new File(
                worldFolder,
                worldName
        );

        if (loadOnInit) load();
    }

    public boolean load() {


        System.out.println(isLoaded());

        if (isLoaded()) return true;

        System.out.println(isLoaded());
        System.out.println("Loading");

        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer().getParentFile(),
                sourceWorldFolder.getName() + "_active_" + System.currentTimeMillis()
        );

        System.out.println("Folder created.");


        try {
            FileUtil.copy(sourceWorldFolder, activeWorldFolder);
            System.out.println("Folder copied.");
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to load GameMap from source folder " + sourceWorldFolder);
            e.printStackTrace();
            return false;
        }

        this.bukkitWorld = Bukkit.createWorld(
                new WorldCreator(activeWorldFolder.getName())
        );


        System.out.println("World created");

        if (bukkitWorld != null) this.bukkitWorld.setAutoSave(false);


        System.out.println("auto save = false");

        System.out.println(isLoaded());
        return isLoaded();
    }

    public void unLoad() {
        System.out.println("Unloading");
        if (bukkitWorld != null) Bukkit.unloadWorld(bukkitWorld, false);
        if (activeWorldFolder != null) FileUtil.delete(activeWorldFolder);
        System.out.println("Deleting folder");

        bukkitWorld = null;
        activeWorldFolder = null;
    }

    public boolean restoreFromSource() {
        System.out.println("Unloading");
        unLoad();
        System.out.println("Loading");
        return load();
    }

    @Override
    public boolean isLoaded() {
       return getWorld() != null;
    }

    @Override
    public World getWorld() {
        return bukkitWorld;
    }
}
