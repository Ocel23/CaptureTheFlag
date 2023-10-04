package me.ocel.capturetheflag.game.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessages implements Listener {

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {

        if (e.getDeathMessage() == null) {
            return;
        }

        Player killerPlayer = e.getEntity().getKiller();

        Player killedPlayer = e.getEntity();

        //set custom death messages by type of player dead

        if (e.getDeathMessage().contains("fell out")) {
            e.setDeathMessage(ChatColor.DARK_GREEN + killedPlayer.getName() + ChatColor.WHITE + " zemřel při pádu do voidu.");
        } else if (e.getDeathMessage().contains("fell from")) {
            e.setDeathMessage(ChatColor.DARK_GREEN + killedPlayer.getName() + ChatColor.WHITE + " spadl z velké výšky.");
        } else if (e.getDeathMessage().contains("blew up")) {
            e.setDeathMessage(ChatColor.DARK_GREEN + killedPlayer.getName() + ChatColor.WHITE + " vybouchl.");
        } else {

            String itemName = "";

            if (killerPlayer == null) {
                return;
            }

            if (killerPlayer.getInventory().getItemInMainHand().getType() == Material.AIR) {
                itemName = "ruky";
            } else {
                if (killerPlayer.getInventory().getItemInMainHand().getItemMeta() == null) {
                    return;
                }
               if (killerPlayer.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("")) {
                   itemName = killerPlayer.getInventory().getItemInMainHand().getType().toString().toLowerCase();
               } else {
                   itemName = killerPlayer.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
               }

            }

            e.setDeathMessage(ChatColor.DARK_GREEN + killedPlayer.getName() + ChatColor.WHITE + " byl ukrutně zabit " + ChatColor.GOLD + killerPlayer.getName() + ChatColor.WHITE + " pomocí " + ChatColor.GOLD + itemName);
        }
    }
}
