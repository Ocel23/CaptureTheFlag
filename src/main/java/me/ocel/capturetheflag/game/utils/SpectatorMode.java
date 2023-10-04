package me.ocel.capturetheflag.game.utils;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.Uttils;
import me.ocel.capturetheflag.gameMap.GameMap;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SpectatorMode implements Listener {

    private final CaptureTheFlag plugin;

    private Location spectatorSpawn;

    private final FileConfiguration configuration;

    private final SpectatorPlayersGui spectatorPlayersGui;

    private final GameMap gameMap;

    public SpectatorMode(CaptureTheFlag plugin, GameMap gameMap) {
        this.gameMap = gameMap;
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
        this.spectatorPlayersGui = new SpectatorPlayersGui(plugin);
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    //protect player in spectator mode to pickup items
    @EventHandler
    private void onItemPickup(EntityPickupItemEvent e) {
        if (e.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) e.getEntity();
            if (isSpectator(player)) {
                e.setCancelled(true);
            }
        }

    }

    //method for cancel damage if player is in spectator mode
    @EventHandler
    private void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) e.getEntity();
            if (isSpectator(player)) {
                e.setCancelled(true);
            }
        }
    }


    //teleport player when fall into void to spawn - cancel death in void
    @EventHandler
    private void onPlayerBeforeFallToVoid(PlayerMoveEvent e) {
            Player player = e.getPlayer();

            if (e.getTo() == null) {
                return;
            }

            if (e.getTo().getBlockY() == -100) {

            player.teleport(this.spectatorSpawn);

            ItemStack returnToLobbyServer = Uttils.getItem(new ItemStack(Material.CLOCK), "&2Zpátky na lobby", "&7Kliknutím se teleportuješ na lobby serveru.");

            Inventory inventory = player.getInventory();

            inventory.setItem(4, returnToLobbyServer);

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                player.setGameMode(GameMode.ADVENTURE);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setGlowing(false);
                player.hidePlayer(plugin, player);

            }, 20L);

        }
    }

    public void setSpectatorMode(Player player) {

        this.spectatorSpawn = new Location(gameMap.getWorld(), this.configuration.getDouble("waitForSpawnLocation.x"), this.configuration.getDouble("waitForSpawnLocation.y"), this.configuration.getDouble("waitForSpawnLocation.z"));

        player.getInventory().clear();

        player.teleport(this.spectatorSpawn);

        player.sendMessage(ChatColor.DARK_GREEN + "Hra už probíhá. Počkej jsi prosím na další.");

        player.getInventory().clear();

        ItemStack returnToLobbyServer = Uttils.getItem(new ItemStack(Material.CLOCK), "&2Zpátky na lobby", "&7Kliknutím se teleportuješ na lobby serveru.");

        Inventory inventory = player.getInventory();

        inventory.setItem(3, returnToLobbyServer);

        spectatorPlayersGui.addItem(player);

        player.setPlayerListName(ChatColor.GRAY + player.getName());

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setGlowing(false);
            player.hidePlayer(plugin, player);

        }, 20L);
    }

    public boolean isSpectator(Player player) {

        if (player.getPlayerListName().equalsIgnoreCase(ChatColor.GRAY + player.getName())) return true;

        return false;
    }
}
