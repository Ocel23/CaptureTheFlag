package me.ocel.capturetheflag.game.shop.specialItems;

import me.ocel.capturetheflag.CaptureTheFlag;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeBomb implements Listener {

    private final CaptureTheFlag plugin;

    private String status;

    public FreezeBomb(CaptureTheFlag plugin) {
        this.plugin = plugin;
        this.status = "inactive";
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        if (player.getLocation().getBlock().getType() == Material.WARPED_PRESSURE_PLATE) {

            e.setCancelled(true);

            if (status.equalsIgnoreCase("inactive")) {

                this.status = "active";
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 20);
                player.setFreezeTicks(Integer.MAX_VALUE);
                player.sendMessage(ChatColor.WHITE + "Šlápl si na " + ChatColor.AQUA + "mrznoucí bombu" + ChatColor.WHITE + "! "+ "Znova odmrzneš za " + ChatColor.AQUA + "5" + ChatColor.WHITE + " sekund.");

            }

            player.getServer().getScheduler().runTaskLater(plugin, () ->  {
                player.getLocation().getBlock().setType(Material.AIR);
                player.setFreezeTicks(0);
                e.setCancelled(false);
                this.status = "inactive";

            }, 100L);
        }
    }

}
