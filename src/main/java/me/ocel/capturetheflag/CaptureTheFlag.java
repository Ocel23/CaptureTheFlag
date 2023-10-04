package me.ocel.capturetheflag;

import me.ocel.capturetheflag.game.Game;
import me.ocel.capturetheflag.gameMap.GameMap;
import me.ocel.capturetheflag.gameMap.LocalGameMap;
import me.ocel.capturetheflag.lobby.Lobby;

import me.ocel.capturetheflag.lobby.utils.Tablist;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class CaptureTheFlag extends JavaPlugin {

    private GameMap map;

    private Lobby lobby;

    private Game game;

    private Scoreboard scoreboard;

    private Tablist tablist;

    private GameStatus gameStatus;

    private FileConfiguration configuration;

    @Override
    public void onEnable() {

        getDataFolder().mkdirs();

        File gameMapsFolder = new File(getDataFolder(), "gameMaps");

        if (!gameMapsFolder.exists()) {
            gameMapsFolder.mkdirs();
        }

        saveDefaultConfig();
        this.configuration = this.getConfig();

        map = new LocalGameMap(gameMapsFolder, this.configuration.getString("nameOfGameWorld"), true, this);

        getServer().getConsoleSender().sendMessage("enabled.");

        gameStatus = new GameStatus();
        scoreboard = new Scoreboard(this);
        game = new Game(this, scoreboard, gameStatus, map);
        lobby = new Lobby(this, scoreboard, game, gameStatus, map, tablist);

    }

    @Override
    public void onDisable() {
        map.unLoad();
    }
}
