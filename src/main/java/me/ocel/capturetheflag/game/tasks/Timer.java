package me.ocel.capturetheflag.game.tasks;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.game.Game;
import org.bukkit.configuration.file.FileConfiguration;

public class Timer {

    private CaptureTheFlag plugin;

    private int minutes;

    private int seconds;

    private String status;

    private Game game;

    private FileConfiguration configuration;
    public Timer(CaptureTheFlag plugin, Game game) {
        this.plugin = plugin;
        this.game = game;
        this.configuration = plugin.getConfig();
        minutes = this.configuration.getInt("gameTimer.minutes");
        seconds = this.configuration.getInt("gameTimer.seconds");;
        status = "Hra začala.";
    }

    //method for update timer
    public void updateTimer() {
        seconds--;
       if (minutes == 0 && seconds == 0) {
           status = "Remíza";
           game.drawEndGame();
       }
       if (seconds == 0) {
            minutes--;
            seconds = this.configuration.getInt("gameTimer.seconds");
        }
        this.status = minutes + "m " + seconds + "s";
    }

    //method for reset timer settings
    public void resetTimerSettings() {
        this.seconds = this.configuration.getInt("gameTimer.seconds");
        this.minutes = this.configuration.getInt("gameTimer.minutes");
    }

    //getter for status
    public String getStatus() {
        return status;
    }
}
