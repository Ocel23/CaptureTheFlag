package me.ocel.capturetheflag.game;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.game.events.DeathMessages;
import me.ocel.capturetheflag.game.shop.ShopGuiManager;
import me.ocel.capturetheflag.game.tasks.SpawnersManager;
import me.ocel.capturetheflag.game.utils.ProtectSettings;
import me.ocel.capturetheflag.GameStatus;
import me.ocel.capturetheflag.Scoreboard;
import me.ocel.capturetheflag.Uttils;
import me.ocel.capturetheflag.gameMap.GameMap;
import me.ocel.capturetheflag.lobby.utils.TeamDecideManager;
import me.ocel.capturetheflag.lobby.utils.TeamSelectGui;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.ocel.capturetheflag.game.tasks.Timer;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class Game implements Listener {

    private final CaptureTheFlag plugin;

    private final Scoreboard scoreboard;

    private String statusOfRedFlag;

    private String statusOfBlueFlag;

    private final TeamDecideManager teamDecide;

    private final SpawnersManager spawners;

    private final Timer timer;

    private final GameStatus status;

    private final GameMap gameMap;

    private final FileConfiguration configuration;
    public Game(CaptureTheFlag plugin, Scoreboard scoreboard, GameStatus status, GameMap gameMap) {

        this.gameMap = gameMap;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        plugin.getServer().getPluginManager().registerEvents(new ProtectSettings(plugin, gameMap), plugin);

        plugin.getServer().getPluginManager().registerEvents(new DeathMessages(), plugin);

        plugin.getCommand("shop").setExecutor(new ShopGuiManager(plugin));

        this.scoreboard = scoreboard;
        this.timer = new Timer(plugin, this);
        this.teamDecide = new TeamDecideManager(plugin, gameMap);
        this.spawners = new SpawnersManager(plugin, gameMap);
        this.configuration = plugin.getConfig();
        this.status = status;
        this.plugin = plugin;
        this.statusOfBlueFlag = "na místě";
        this.statusOfRedFlag = "na místě";
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent e) {
        clickOnTheFlag(e);
    }


    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent e) {

        addItemsFromDeadPlayerToKiller(e);

        setFlagOnDeath(e);

        Player player = e.getPlayer();

        waitForSpawn(player, e);

    }

    //method for start the game
    public void startGame() {
        this.status.setStatus("Playing");
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 20);
            sendInfoMessage(player);
            player.setGameMode(GameMode.SURVIVAL);
        }
        teamDecide.pickRandomLocations();
        spawners.createHolograms();
        spawners.spawnIngots();
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            timer.updateTimer();
            scoreboard.updateGameScoreboards(ChatColor.GOLD + "Konec hry za: " + ChatColor.WHITE + timer.getStatus(), statusOfBlueFlag, statusOfRedFlag);
        }, 0L, 20L);
    }

    //method for end game
    private void endGame(Player player) {

        String team = "";

        if (player.getPlayerListName().equalsIgnoreCase(ChatColor.BLUE + player.getName())) {
            team = ChatColor.BLUE + "modrý";
        } else {
            team = ChatColor.RED + "červený";
        }

        for (Player p : plugin.getServer().getOnlinePlayers()) {
            p.sendTitle(ChatColor.GOLD + "Vyhrál", team + ChatColor.WHITE + " team!", 80, 20, 20);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 20);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                Location location = new Location(plugin.getServer().getWorld(this.configuration.getString("nameOfLobbyWorld")), this.configuration.getDouble("lobbySpawnLocation.x"), this.configuration.getDouble("lobbySpawnLocation.y"), this.configuration.getDouble("lobbySpawnLocation.z"));
                p.teleport(location);
                TeamSelectGui.addItem(p);
                gameMap.restoreFromSource();
            }, 40L);
            resetPlayerSettings(p);
            resetGameSettings();
        }
    }


    //method for end game - if is draw
    public void drawEndGame() {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            p.sendTitle(ChatColor.GOLD + "Remíza", "", 80, 20, 20);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 20);
            resetPlayerSettings(p);
            resetGameSettings();
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                Location location = new Location(plugin.getServer().getWorld(this.configuration.getString("nameOfLobbyWorld")), this.configuration.getDouble("lobbySpawnLocation.x"), this.configuration.getDouble("lobbySpawnLocation.y"), this.configuration.getDouble("lobbySpawnLocation.z"));
                p.teleport(location);
                TeamSelectGui.addItem(p);
                gameMap.restoreFromSource();
            }, 40L);
        }

    }


    private ItemStack getBlueFlag() {

        return Uttils.getItem(new ItemStack(Material.BLUE_BANNER), ChatColor.BLUE + "Modrá vlajka", ChatColor.WHITE + "Klikni s ní na červenou flajku.");

    }
    private ItemStack getRedFlag() {

        return Uttils.getItem(new ItemStack(Material.RED_BANNER), ChatColor.RED + "Červená vlajka", ChatColor.WHITE + "Klikni s ní na červenou flajku.");

    }

    private void sendInfoMessage(Player player) {
        player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "      Capture the Flag");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "----------------------------");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "Tvý úkolem je ukořistit soupeřovu vlajku a donést ji ke své vlajce.");
        player.sendMessage(ChatColor.GRAY + "Na středu se spawnují diamanty a irony.");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "---------------------------");
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "            Hodně štestí");
    }

    private void resetPlayerSettings(Player player) {
        player.setHealth(20);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.setGameMode(GameMode.ADVENTURE);
        player.setInvisible(false);
        player.showPlayer(plugin, player);
        player.getInventory().clear();
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setPlayerListName(ChatColor.WHITE + player.getName());
        player.setGlowing(false);
        player.setFoodLevel(20);
    }

    private void resetGameSettings() {
        teamDecide.resetSettings();
        plugin.getServer().getScheduler().cancelTasks(plugin);
        statusOfBlueFlag = "na místě";
        statusOfRedFlag = "na místě";
        status.setStatus("Waiting");
        timer.resetTimerSettings();
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "resettimer");
    }


    //method for teleport player when is dead - cool down to spawn
    private void waitForSpawn(Player player, PlayerRespawnEvent e) {
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setInvisible(true);
        Location location2 = new Location(gameMap.getWorld(), this.configuration.getDouble("waitForSpawnLocation.x"), this.configuration.getDouble("waitForSpawnLocation.y"), this.configuration.getDouble("waitForSpawnLocation.z"), (float) this.configuration.getDouble("waitForSpawnLocation.yaw"), (float) this.configuration.getDouble("waitForSpawnLocation.pitch"));
        e.setRespawnLocation(location2);
        double x = this.configuration.getDouble("waitForSpawnLocation.x");
        double y = this.configuration.getDouble("waitForSpawnLocation.y");
        double z = this.configuration.getDouble("waitForSpawnLocation.z");
        float pitch = (float) this.configuration.getDouble("waitForSpawnLocation.pitch");
        float yaw = (float) this.configuration.getDouble("waitForSpawnLocation.yaw");

        System.out.println("Read from config: x=" + x + ", y=" + y + ", z=" + z + ", pitch=" + pitch + ", yaw=" + yaw);
        Location location1;
        if (player.getPlayerListName().equalsIgnoreCase(ChatColor.BLUE + player.getName())) {
            location1 = new Location(gameMap.getWorld(), this.configuration.getDouble("blueTeamSpawnLocation.x"), this.configuration.getDouble("blueTeamSpawnLocation.y"), this.configuration.getDouble("blueTeamSpawnLocation.z"), (float) this.configuration.getDouble("blueTeamSpawnLocation.yaw"), (float) this.configuration.getDouble("blueTeamSpawnLocation.pitch"));
        } else {
            location1 = new Location(gameMap.getWorld(), this.configuration.getDouble("redTeamSpawnLocation.x"), this.configuration.getDouble("redTeamSpawnLocation.y"), this.configuration.getDouble("redTeamSpawnLocation.z"), (float) this.configuration.getDouble("redTeamSpawnLocation.yaw"), (float) this.configuration.getDouble("redTeamSpawnLocation.pitch"));
        }

        player.getInventory().clear();

        new BukkitRunnable() {
            int seconds = 5;

            @Override
            public void run() {
                seconds--;
                player.sendTitle(ChatColor.GOLD + "Znova se spawnneš za ",seconds + " sekund", 5, 20, 5);
                if (seconds == 0) {
                    player.teleport(location1);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.setGlowing(false);
                    player.setInvisible(false);
                    cancel();
                }
            }

        }.runTaskTimer(plugin, 0L, 20L);
    }

    //method for add item from dead player to killer
    private void addItemsFromDeadPlayerToKiller(PlayerRespawnEvent e) {

        Player killerPlayer = e.getPlayer().getKiller();

        Player killedPlayer = e.getPlayer();


        if (killerPlayer == null) {
            return;
        }

        Inventory killedPlayerInventory = killedPlayer.getInventory();

        int countOfCoppersIngot = 0;

        int countOfIronsIngot = 0;

        int countOfDiamonds = 0;

        Inventory killerPlayerInventory = killerPlayer.getInventory();

        for (ItemStack item : killedPlayerInventory.getContents()) {

            if (item == null) {
                continue;
            }

            if (item.getType() == Material.COPPER_INGOT) {

                ItemStack newItem1 = new ItemStack(item.getType(), item.getAmount());

                countOfCoppersIngot = countOfCoppersIngot + item.getAmount();

                killerPlayerInventory.addItem(newItem1);

            } else if (item.getType() == Material.IRON_INGOT) {

                ItemStack newItem2 = new ItemStack(item.getType(), item.getAmount());

                countOfIronsIngot = countOfIronsIngot + item.getAmount();

                killerPlayerInventory.addItem(newItem2);

            } else if (item.getType() == Material.DIAMOND) {

                ItemStack newItem3 = new ItemStack(item.getType(), item.getAmount());

                countOfDiamonds = countOfDiamonds + item.getAmount();

                killerPlayerInventory.addItem(newItem3);
            }

        }


        if (countOfCoppersIngot != 0) killerPlayer.sendMessage(ChatColor.GOLD + "Získal si " + ChatColor.WHITE + countOfCoppersIngot + ChatColor.GOLD + " copper ingotů.");
        if (countOfIronsIngot != 0) killerPlayer.sendMessage(ChatColor.GRAY + "Získal si " + ChatColor.WHITE + countOfIronsIngot + ChatColor.GRAY + " iron ingotů.");
        if (countOfDiamonds != 0) killerPlayer.sendMessage(ChatColor.BLUE + "Získal si " + ChatColor.WHITE + countOfDiamonds + ChatColor.GOLD + " diamond ingotů.");
    }

    //method to placed again flag if player dead
    private void setFlagOnDeath(PlayerRespawnEvent e) {

        ItemStack blueFlag = getBlueFlag();

        ItemStack redFlag = getRedFlag();

        Player player = e.getPlayer();

        Inventory inventory = player.getInventory();

        if (inventory.contains(redFlag)) {

            Location redFlaglocation = new Location(gameMap.getWorld(), this.configuration.getDouble("redFlagLocation.x"), this.configuration.getDouble("redFlagLocation.y"), this.configuration.getDouble("redFlagLocation.z"));

            Block redFlagBlock = redFlaglocation.getBlock();

            redFlagBlock.setType(Material.RED_BANNER);

            statusOfRedFlag = "na místě";

        } else if (inventory.contains(blueFlag)) {

            Location blueFlaglocation = new Location(gameMap.getWorld(), this.configuration.getDouble("blueFlagLocation.x"), this.configuration.getDouble("blueFlagLocation.y"), this.configuration.getDouble("blueFlagLocation.z"));

            Block blueFlagBlock = blueFlaglocation.getBlock();

            blueFlagBlock.setType(Material.BLUE_BANNER);

            statusOfBlueFlag = "na místě";
        }
    }

    //method for handle, when player stole the flag
    private void clickOnTheFlag(PlayerInteractEvent e) {

        Player player = e.getPlayer();

        Block block = e.getClickedBlock();

        Inventory inventory = player.getInventory();

        ItemStack blueFlag = getBlueFlag();

        ItemStack redFlag = getRedFlag();

        if (block == null || e.getItem() == null) {
            return;
        }

        if (block.getType() == Material.BLUE_BANNER) {

            int team  = Uttils.getTeam(player);

            if (team == 0) {
                if (inventory.contains(redFlag)) {
                    endGame(player);
                    return;
                }
                player.sendMessage(ChatColor.RED + "Svoji vlajku nemůžeš zničit!");
                return;

            } else if (player.getPlayerListName().equalsIgnoreCase(ChatColor.GRAY + player.getName())){
                return;
            }

            block.setType(Material.AIR);

            inventory.addItem(blueFlag);

            player.setGlowing(true);
            player.sendMessage(ChatColor.BLUE + "Vzal si modrou vlajku. Rychle utíkej!");

            this.statusOfBlueFlag = "ukradena";

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                p.sendMessage(ChatColor.WHITE + player.getName() + ChatColor.BLUE + " vzal modrou vlajku.");
                p.sendTitle(ChatColor.WHITE + player.getName(), ChatColor.BLUE + " vzal modrou vlajku.",40, 10, 10 );
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 50, 10);
            }

        } else if (block.getType() == Material.RED_BANNER) {


            int team = Uttils.getTeam(player);

            if (team == 1) {
                if (inventory.contains(blueFlag)) {
                    endGame(player);
                    return;
                }
                player.sendMessage(ChatColor.RED + "Svoji vlajku nemůžeš zničit!");
                return;
            } else if (player.getPlayerListName().equalsIgnoreCase(ChatColor.GRAY + player.getName())){
                return;
            }

            block.setType(Material.AIR);

            inventory.addItem(redFlag);

            this.statusOfRedFlag = "ukradena";

            player.setGlowing(true);

            player.sendMessage(ChatColor.RED + "Vzal si červenou vlajku. Rychle utíkej.");

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                p.sendMessage(ChatColor.WHITE + player.getName() + ChatColor.RED + " vzal červenou vlajku.");
                p.sendTitle(ChatColor.WHITE + player.getName(), ChatColor.RED + " vzal červenou vlajku.",40, 10, 10 );
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 50, 10);
            }


        }
    }

}
