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
import org.bukkit.potion.PotionEffect;

public class SpectatorMode implements Listener {

    private final CaptureTheFlag plugin;

    private Location spectatorSpawn;

    private final FileConfiguration configuration;

    private final SpectatorPlayersGui spectatorPlayersGui;

    private final GameMap gameMap;

    private boolean isTriggered;

    public SpectatorMode(CaptureTheFlag plugin, GameMap gameMap) {
        this.gameMap = gameMap;
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
        this.spectatorPlayersGui = new SpectatorPlayersGui(plugin);
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.isTriggered = false;
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
    private void onPlayerMove(PlayerMoveEvent e) {

            Player player = e.getPlayer();

            if (e.getTo() == null) {
                return;
            }

            if (isSpectator(player)) {

                if (e.getTo().getBlockY() >= -100 && !isTriggered) {

                    isTriggered = true;

                    player.teleport(this.spectatorSpawn);

                    Inventory inventory = player.getInventory();

                    plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                        player.setGameMode(GameMode.ADVENTURE);
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.setGlowing(false);
                        player.hidePlayer(plugin, player);

                    }, 20L);

                }
            }
    }

    public void setSpectatorMode(Player player) {

        this.spectatorSpawn = new Location(gameMap.getWorld(), this.configuration.getDouble("waitForSpawnLocation.x"), this.configuration.getDouble("waitForSpawnLocation.y"), this.configuration.getDouble("waitForSpawnLocation.z"));

        player.getInventory().clear();

        player.teleport(this.spectatorSpawn);

        player.sendMessage(ChatColor.DARK_GREEN + "Hra už probíhá. Počkej jsi prosím na další.");

        player.getInventory().clear();

        Inventory inventory = player.getInventory();

        inventory.setItem(3, Uttils.getItem(new ItemStack(Material.CLOCK), "&2Zpátky na lobby", "&7Kliknutím se teleportuješ na lobby serveru."));

        inventory.setItem(5, Uttils.getItem(new ItemStack(Material.COMPASS), "&7Teleport menu", "&fPravím kliknutím zobrazíš menu pro teleportaci k hráči."));

        player.setPlayerListName(ChatColor.GRAY + player.getName());

        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setGlowing(false);
        player.hidePlayer(plugin, player);
        player.setInvisible(true);
        player.setFoodLevel(20);

        player.setHealth(20);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

    }

    public boolean isSpectator(Player player) {

        if (player.getPlayerListName().equalsIgnoreCase(ChatColor.GRAY + player.getName())) return true;

        return false;
    }
}
