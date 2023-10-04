package me.ocel.capturetheflag;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Scoreboard implements Listener {

    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final CaptureTheFlag plugin;

    public Scoreboard(CaptureTheFlag plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {

        //crate scoreboard for player
        Player player = e.getPlayer();
        FastBoard board = new FastBoard(player);

        board.updateTitle(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Capture the Flag");

        boards.put(player.getUniqueId(), board);

    }

    @EventHandler
    private  void onPlayerQuit(PlayerQuitEvent e) {

        //remove scoreboard to player
        Player player = e.getPlayer();
        FastBoard board = boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }

    }

    //method for display team for player
    private String displayTeam(Player player) {

        int team = Uttils.getTeam(player);

        String teamName;
        if (team == 0) {
            teamName = ChatColor.BLUE + "modrý";
        } else if (team == 1) {
            teamName = ChatColor.RED + "červený";
        } else {
            teamName = ChatColor.GRAY + "žádný";
        }

        return teamName;

    }

    //method for display status of player flag
    private String displayFlagStatusOfTeam(Player player, String statusOfRedFlag, String statusOfBlueFlag) {

        String status = "";

        int team = Uttils.getTeam(player);

        if (team == 0) {
            status = ChatColor.WHITE + statusOfBlueFlag;
        } else if (team == 1) {
            status = ChatColor.WHITE + statusOfRedFlag;
        } else {
            status = ChatColor.GRAY + "žádný";
        }

        return status;

    }

    //method for update Game scoreboard
    public void updateGameScoreboards(String line1, String statusOfBlueFlag, String statusOfRedFlag) {
        for (FastBoard board : this.boards.values()) {

            Player player = board.getPlayer();

            updateScoreboard(
                    line1,
                    ChatColor.GOLD + "Team: " + displayTeam(player)
                    , ChatColor.GOLD + "Status tvé vlajky: " + displayFlagStatusOfTeam(player, statusOfRedFlag, statusOfBlueFlag)
                    , board);
        }
    }

    //method for update Scoreboard
    private void updateScoreboard(String line1, String line2, String line3, FastBoard board) {
        board.updateLines(
            "",
            line1,
            "",
            line2,
            "",
            line3,
            "",
            ChatColor.WHITE + "     play.pigger.eu"
        );
    }


    //method for update Lobby scoreboard
    public void updateLobbyScoreboard(String line1, String line2, String line3) {
        for (FastBoard board : boards.values()) {
            board.updateLines(
                    "",
                    line1,
                    "",
                    line2,
                    "",
                    line3,
                    "",
                    ChatColor.WHITE + "     play.pigger.eu"
            );
        }
    }



}
