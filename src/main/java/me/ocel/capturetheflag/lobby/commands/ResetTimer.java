package me.ocel.capturetheflag.lobby.commands;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.lobby.Lobby;
import me.ocel.capturetheflag.lobby.tasks.Timer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetTimer implements CommandExecutor {

    private final CaptureTheFlag plugin;

    private final Timer timer;

    private final Lobby lobby;

    public ResetTimer(CaptureTheFlag plugin, Timer timer, Lobby lobby) {
        this.plugin = plugin;
        this.timer = timer;
        this.lobby = lobby;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] name) {

        String message = ChatColor.DARK_GREEN + "Časovač byl resetován.";

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!(player.hasPermission("capturetheflag.resettimer"))) {
                player.sendMessage(ChatColor.RED + "Na tohle nemáš oprávnění!");
                return false;
            }
            player.sendMessage(message);
            return true;
        }

        plugin.getServer().getConsoleSender().sendMessage(message);

        timer.resetTimer();

        lobby.startCounter();

        return true;
    }
}
