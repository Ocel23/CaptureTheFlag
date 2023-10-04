package me.ocel.capturetheflag.lobby.events;

import me.ocel.capturetheflag.CaptureTheFlag;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitMessage implements Listener {

    private final CaptureTheFlag plugin;
    public QuitMessage(CaptureTheFlag plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent e) {

        Player p = e.getPlayer();
        e.setQuitMessage(ChatColor.GOLD + p.getName() + ChatColor.WHITE + " se odpojil ze hry " + ChatColor.GOLD + plugin.getServer().getOnlinePlayers().size() + ChatColor.WHITE + "/" + ChatColor.GOLD + plugin.getServer().getMaxPlayers());

    }
}
