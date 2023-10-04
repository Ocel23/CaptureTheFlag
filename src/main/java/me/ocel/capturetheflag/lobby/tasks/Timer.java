package me.ocel.capturetheflag.lobby.tasks;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class Timer {

    private String status;
    private int seconds;

    private final CaptureTheFlag plugin;

    private final Game game;

    private final FileConfiguration configuration;

    public Timer(CaptureTheFlag plugin, Game game) {
        status = "Čekaní na hráče...";
        this.configuration = plugin.getConfig();
        seconds = this.configuration.getInt("lobbyTimerSeconds");
        this.plugin = plugin;
        this.game = game;
    }

    //method for updateTimer
    public void updateTimer() {
        this.seconds--;
        this.status = "Hra začíná za " + this.seconds + "s";
        if (this.seconds <= 10) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10 , 50);
            }
        }
        if (this.seconds == 0) {
            this.status = "Hra začala";
            this.plugin.getServer().getScheduler().cancelTasks(plugin);
            game.startGame();
        }
        if (this.seconds <= 5) {
            for (Player player: plugin.getServer().getOnlinePlayers()) {
                player.sendTitle(ChatColor.GOLD + "Hra začíná za", ChatColor.WHITE + "" + this.seconds, 3, 20, 3);
            }
        }

    }

    //method for resetTimer values
    public void resetTimer() {
        plugin.getServer().getScheduler().cancelTasks(this.plugin);
        this.seconds = this.configuration.getInt("lobbyTimerSeconds");
        this.status = "Čekání na hráče...";
    }

    //getter for status
    public String getStatus() {
        return status;
    }
}
