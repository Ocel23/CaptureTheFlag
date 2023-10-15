package me.ocel.capturetheflag.gameMap;

import me.ocel.capturetheflag.CaptureTheFlag;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.ClickRedirectTrait;
import net.citizensnpcs.trait.CommandTrait;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.io.File;
import java.io.IOException;

public class LocalGameMap implements GameMap, Listener {
    private final File sourceWorldFolder;

    private File activeWorldFolder;

    private World bukkitWorld;

    private final CaptureTheFlag plugin;

    public LocalGameMap(File worldFolder, String worldName, boolean loadOnInit, CaptureTheFlag plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.sourceWorldFolder = new File(
                worldFolder,
                worldName
        );

        if (loadOnInit) load();
    }

    public boolean load() {

        if (isLoaded()) return true;

        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer().getParentFile(),
                sourceWorldFolder.getName() + "_active_" + System.currentTimeMillis()
        );

        try {
            FileUtil.copy(sourceWorldFolder, activeWorldFolder);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to load GameMap from source folder " + sourceWorldFolder);
            e.printStackTrace();
            return false;
        }

        this.bukkitWorld = Bukkit.createWorld(
                new WorldCreator(activeWorldFolder.getName())
        );

        this.bukkitWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        this.bukkitWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        this.bukkitWorld.setGameRule(GameRule.KEEP_INVENTORY, true);
        this.bukkitWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        this.bukkitWorld.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
        this.bukkitWorld.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
        this.bukkitWorld.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);


        /*
        NPC redNpc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "&c&lObchod");

        redNpc.spawn(new Location(getWorld(), -4.178, 65.0, 50.522, 90.3F, -0.9F));
        */


        FileConfiguration configuration = plugin.getConfig();

        Location redVillagerLocation = new Location(
                getWorld()
                , configuration.getDouble("redVillagerShopLocation.x")
                , configuration.getDouble("redVillagerShopLocation.y")
                , configuration.getDouble("redVillagerShopLocation.z")
                , (float) configuration.getDouble("redVillagerShopLocation.yaw")
                , (float) configuration.getDouble("redVillagerShopLocation.pitch")
        );

        Villager redVillager = (Villager) redVillagerLocation.getWorld().spawnEntity(redVillagerLocation, EntityType.VILLAGER);

        Location blueVillagerLocation = new Location(
                getWorld()
                , configuration.getDouble("blueVillagerShopLocation.x"),
                configuration.getDouble("blueVillagerShopLocation.y"),
                configuration.getDouble("blueVillagerShopLocation.z"),
                (float) configuration.getDouble("blueVillagerShopLocation.yaw"),
                (float) configuration.getDouble("blueVillagerShopLocation.pitch"));

        Villager blueVillager = (Villager) blueVillagerLocation.getWorld().spawnEntity(blueVillagerLocation, EntityType.VILLAGER);

        redVillager.setCanPickupItems(false);
        redVillager.setInvulnerable(true);
        redVillager.setCustomNameVisible(true);
        redVillager.setCustomName(ChatColor.RED + "" + ChatColor.BOLD + "Obchod");

        blueVillager.setCanPickupItems(false);
        blueVillager.setInvulnerable(true);
        blueVillager.setCustomNameVisible(true);
        blueVillager.setCustomName(ChatColor.BLUE + "" + ChatColor.BOLD + "Obchod");

        if (bukkitWorld != null) this.bukkitWorld.setAutoSave(false);

        return isLoaded();
    }

    public void unLoad() {
        if (bukkitWorld != null) Bukkit.unloadWorld(bukkitWorld, false);
        if (activeWorldFolder != null) FileUtil.delete(activeWorldFolder);

        bukkitWorld = null;
        activeWorldFolder = null;
    }

    public boolean restoreFromSource() {
        unLoad();
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
