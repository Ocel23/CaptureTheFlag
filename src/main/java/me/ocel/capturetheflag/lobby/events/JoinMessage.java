package me.ocel.capturetheflag.lobby.events;


import me.ocel.capturetheflag.CaptureTheFlag;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class JoinMessage implements Listener {

    private final CaptureTheFlag plugin;

    public JoinMessage(CaptureTheFlag plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {

        e.setJoinMessage(ChatColor.GOLD + e.getPlayer().getName() + ChatColor.WHITE + " se připojil do hry " + ChatColor.GOLD + plugin.getServer().getOnlinePlayers().size() + ChatColor.WHITE + "/" + ChatColor.GOLD + plugin.getServer().getMaxPlayers());

    }
}



