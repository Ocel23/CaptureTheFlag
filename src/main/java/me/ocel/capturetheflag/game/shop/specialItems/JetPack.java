package me.ocel.capturetheflag.game.shop.specialItems;

import me.ocel.capturetheflag.CaptureTheFlag;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class JetPack implements Listener {

    private final CaptureTheFlag plugin;

    public JetPack(CaptureTheFlag plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent e) {

        if (e.getItem() == null) {
            return;
        }

        if (e.getItem().getType() == Material.FIREWORK_ROCKET) {

            Player player = e.getPlayer();

            player.setVelocity(player.getEyeLocation().getDirection().setY(1));

            ItemStack chestplate = player.getInventory().getChestplate();

            player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));

            Inventory inventory = player.getInventory();

            for (ItemStack item : inventory.getContents()) {

                if (item == null) {
                    continue;
                }

                if (item.getType() == Material.FIREWORK_ROCKET) {

                    int amount = item.getAmount();

                    item.setAmount(amount - 1);
                    break;

                }
            }

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (chestplate != null) {
                    player.getInventory().setChestplate(chestplate);
                }
                player.spawnParticle(Particle.CLOUD, player.getLocation(), 50);
            }, 28L);
        }
    }
}
