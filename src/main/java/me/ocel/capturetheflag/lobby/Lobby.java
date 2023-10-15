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
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    private final List<UUID> playersOfCompleteParkur = new ArrayList<>();

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
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
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
            Location lobbyLocation = new Location(plugin.getServer().getWorld(this.configuration.getString("nameOfLobbyWorld")), this.configuration.getDouble("lobbySpawnLocation.x"), this.configuration.getDouble("lobbySpawnLocation.y"), this.configuration.getDouble("lobbySpawnLocation.z"), (float) this.configuration.getDouble("lobbySpawnLocation.yaw"), (float) this.configuration.getDouble("lobbySpawnLocation.pitch"));
            e.getPlayer().teleport(lobbyLocation);
            scoreboard.updateLobbyScoreboard(
                    ChatColor.GOLD + "Stav: " + ChatColor.WHITE + timer.getStatus(),
                    ChatColor.GOLD + "Hráči: " + ChatColor.WHITE + plugin.getServer().getOnlinePlayers().size() + ChatColor.GOLD + "/" + ChatColor.WHITE + "6",
                    ChatColor.GOLD + "Mapa: " + ChatColor.WHITE + "Castle"
            );
            if (plugin.getServer().getOnlinePlayers().size() == this.configuration.getInt("countPlayersForStartGame")) {
                startCounter();
            }
    }

    public void startCounter() {
            this.statusOfGame.setStatus("Starting");
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                //update timer every seconds
                timer.updateTimer();
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

        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(this.configuration.getString("nameOfLobbyWorld"))) {
            //if is not enough player then reset timer
            if (plugin.getServer().getOnlinePlayers().size() == configuration.getInt("countPlayersForStartGame")) {
                timer.resetTimer();
            }
        }
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        Location location = player.getLocation();

        if (player.getWorld().getName().equalsIgnoreCase(configuration.getString("nameOfLobbyWorld")) && location.getBlock().getRelative(BlockFace.DOWN).getType() == Material.DIAMOND_BLOCK) {

            if (playersOfCompleteParkur.contains(player.getUniqueId())) {
                return;
            }

            playersOfCompleteParkur.add(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "Dokončil jsi parkur!");
            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 100);
            player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 100, 20);
            player.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 70, 20);
        }

    }


}
