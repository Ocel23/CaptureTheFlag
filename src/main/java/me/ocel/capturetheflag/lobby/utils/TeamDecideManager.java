package me.ocel.capturetheflag.lobby.utils;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.Uttils;
import me.ocel.capturetheflag.gameMap.GameMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamDecideManager {

    private  Location[] redLocations = new Location[3];

    private  Location[] blueLocations = new Location[3];

    private int countOfBluePlayers;

    private int countOfRedPlayers;

    private final CaptureTheFlag plugin;

    private final FileConfiguration configuration;

    private final GameMap gameMap;

    private final List<Integer> blueRandomIndexs = new ArrayList<>();

    private final List<Integer> redRandomIndexs = new ArrayList<>();

    public TeamDecideManager(CaptureTheFlag plugin, GameMap gameMap) {
        this.gameMap = gameMap;
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
        this.countOfBluePlayers = 0;
        this.countOfRedPlayers = 0;
    }


    //method for pick random locations for player
    public void pickRandomLocations() {

        redLocations = createSpawnLocations(this.configuration.getDouble("spawnsTeamsLocations.redX"));
        blueLocations = createSpawnLocations(this.configuration.getDouble("spawnsTeamsLocations.blueX"));


        Random random = new Random();

        for (Player player : plugin.getServer().getOnlinePlayers()) {

            player.getInventory().clear();

            int team = Uttils.getTeam(player);

            if (team == 0) {
                teleportBlue(player, random);
                countOfBluePlayers++;
            } else if (team == 1) {
                teleportRed(player, random);
                countOfRedPlayers++;
            } else {
                int randomNumber = random.nextInt(2);
                if (countOfBluePlayers == 3) {
                    randomNumber = 1;
                }
                if (randomNumber == 0) {
                    player.setPlayerListName(ChatColor.BLUE + player.getName());
                    teleportBlue(player, random);
                    countOfBluePlayers++;
                } else {
                    player.setPlayerListName(ChatColor.RED + player.getName());
                    teleportRed(player, random);
                    countOfRedPlayers++;
                }
            }
        }
    }

    private void teleportRed(Player player, Random random) {
        int randomIndex = random.nextInt(3);
        while (redRandomIndexs.contains(randomIndex)) {
            randomIndex = random.nextInt(3);
        }
        redRandomIndexs.add(randomIndex);
        Location location = redLocations[randomIndex];
        player.teleport(location);
    }

    private void teleportBlue(Player player, Random random) {
        int randomIndex = random.nextInt(3);
        while (blueRandomIndexs.contains(randomIndex)) {
            randomIndex = random.nextInt(3);
        }
        blueRandomIndexs.add(randomIndex);
        Location location = blueLocations[randomIndex];
        player.teleport(location);
    }

    //method for reset settings
    public void resetSettings() {
        this.countOfBluePlayers = 0;
        this.countOfRedPlayers = 0;
        this.redRandomIndexs.clear();
        this.blueRandomIndexs.clear();
    }

    //help method for create single location
    private Location[] createSpawnLocations(double x) {
        Location[] locations = new Location[3];
        for (int i = 0; i < 3; i++) {
            locations[i] = new Location(gameMap.getWorld(), x ,this.configuration.getDouble("spawnsTeamsLocations.y") , this.configuration.getDouble("spawnsTeamsLocations.z") + i + 1);
        }
        return locations;
    }
}
