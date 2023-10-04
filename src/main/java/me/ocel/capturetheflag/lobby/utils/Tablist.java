package me.ocel.capturetheflag.lobby.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class Tablist {

    //method for create tablist for player
    public void createTablist(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        player.setPlayerListHeader(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Capture the Flag");
        player.setPlayerListFooter(ChatColor.GOLD + "play.pigger.eu");

    }
}
