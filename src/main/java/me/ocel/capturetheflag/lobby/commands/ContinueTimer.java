package me.ocel.capturetheflag.lobby.commands;

import me.ocel.capturetheflag.lobby.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ContinueTimer implements CommandExecutor {

    private final Lobby lobby;

    public ContinueTimer(Lobby lobby) {
        this.lobby = lobby;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] name) {


        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!(player.hasPermission("capturetheflag.continuetimer"))) {
            player.sendMessage(ChatColor.RED + "Na tohle nemáš oprávnění!");
            return false;
        }

        lobby.startCounter();
        player.sendMessage(ChatColor.DARK_GREEN + "Časovač byl znovu aktivován.");

        return true;
    }
}
