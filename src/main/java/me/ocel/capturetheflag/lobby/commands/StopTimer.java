package me.ocel.capturetheflag.lobby.commands;

import me.ocel.capturetheflag.CaptureTheFlag;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopTimer implements CommandExecutor {

    private final CaptureTheFlag plugin;

    public StopTimer(CaptureTheFlag plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] name) {


        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!(player.getPlayer().hasPermission("capturetheflag.stoptimer"))) {
            player.sendMessage(ChatColor.RED + "Na tohle nemáš oprávnění!");
        }

        plugin.getServer().getScheduler().cancelTasks(plugin);

        player.sendMessage(ChatColor.DARK_GREEN + "Časovač byl zastaven.");

        return true;
    }
}
