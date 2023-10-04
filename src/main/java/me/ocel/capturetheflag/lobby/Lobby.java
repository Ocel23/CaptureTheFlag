package me.ocel.capturetheflag.lobby;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.game.Game;
import me.ocel.capturetheflag.game.utils.SpectatorMode;
import me.ocel.capturetheflag.GameStatus;
import me.ocel.capturetheflag.gameMap.GameMap;
import me.ocel.capturetheflag.lobby.commands.ContinueTimer;
import me.ocel.capturetheflag.lobby.commands.ResetTimer;
import me.ocel.capturetheflag.lobby.commands.StopTimer;
import me.ocel.capturetheflag.lobby.events.JoinMessage;
import me.ocel.capturetheflag.lobby.events.QuitMessage;
import me.ocel.capturetheflag.lobby.tasks.Timer;
import me.ocel.capturetheflag.lobby.utils.ProtectSettings;
import me.ocel.capturetheflag.Scoreboard;
import me.ocel.capturetheflag.lobby.utils.Tablist;
import me.ocel.capturetheflag.lobby.utils.TeamSelectGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Lobby implements Listener {

    private final CaptureTheFlag plugin;

    private final Scoreboard scoreboard;

    private final Tablist tablist;

    private final Timer timer;

    private final TeamSelectGui gui;

    private final Game game;

    private final GameStatus statusOfGame;

    private final SpectatorMode spectatorMode;

    private final GameMap gameMap;

    private final FileConfiguration configuration;

    public Lobby(CaptureTheFlag plugin, Scoreboard scoreboard, Game game, GameStatus statusOfGame, GameMap gameMap, Tablist tablist) {

        this.plugin = plugin;
        this.game = game;
        this.gameMap = gameMap;
        this.gui = new TeamSelectGui(plugin);
        this.timer = new Timer(plugin, game);
        this.scoreboard = scoreboard;
        this.tablist = tablist;
        this.spectatorMode = new SpectatorMode(plugin, gameMap);
        this.statusOfGame = statusOfGame;
        this.configuration = plugin.getConfig();


        plugin.getServer().getPluginManager().registerEvents(new JoinMessage(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new QuitMessage(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getPluginManager().registerEvents(new ProtectSettings(plugin), plugin);

        plugin.getCommand("stoptimer").setExecutor(new StopTimer(plugin));

        plugin.getCommand("resettimer").setExecutor(new ResetTimer(plugin, timer, this));

        plugin.getCommand("continuetimer").setExecutor(new ContinueTimer(this));
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
            e.getPlayer().getInventory().clear();
            //create tablist for player
            tablist.createTablist(e);
            //check status of game
            if (this.statusOfGame.getStatus().equalsIgnoreCase("Playing")) {
                spectatorMode.setSpectatorMode(e.getPlayer());
                return;
            }
            //open team decide gui for player
            gui.showGui(e.getPlayer());
            TeamSelectGui.addItem(e.getPlayer());
            //if game already starting then return
            if (this.statusOfGame.getStatus().equalsIgnoreCase("Starting")) {
               return;
            }
            startCounter();
    }

    public void startCounter() {
        //check if is enough players
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (Bukkit.getOnlinePlayers().size() == configuration.getInt("countPlayersForStartGame")) {
                this.statusOfGame.setStatus("Starting");
                //update timer every seconds
                timer.updateTimer();
            }
            //update scoreboard every second
            scoreboard.updateLobbyScoreboard(
                    ChatColor.GOLD + "Stav: " + ChatColor.WHITE + timer.getStatus(),
                    ChatColor.GOLD + "Hráči: " + ChatColor.WHITE + plugin.getServer().getOnlinePlayers().size() + ChatColor.GOLD + "/" + ChatColor.WHITE + "6",
                    ChatColor.GOLD + "Mapa: " + ChatColor.WHITE + "Castle"
            );
        }, 0L, 20L);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent e) {
        //if is not enough player then reset timer
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(configuration.getString("nameOfLobbyWorld"))) {
            if (plugin.getServer().getOnlinePlayers().size() == 1) {
                timer.resetTimer();
            }
        }
    }


}
