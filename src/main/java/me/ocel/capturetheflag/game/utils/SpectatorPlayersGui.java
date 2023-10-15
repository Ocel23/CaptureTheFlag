package me.ocel.capturetheflag.game.utils;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.Uttils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SpectatorPlayersGui implements Listener {

    private final CaptureTheFlag plugin;

    public SpectatorPlayersGui(CaptureTheFlag plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent e) {

        Player player = e.getPlayer();

        if (e.getItem() == null) {
            return;
        }

        if (e.getItem().getType() == Material.COMPASS) {
            showGui(player);
        } else if (e.getItem().getType() == Material.CLOCK) {
            player.performCommand("server lobby");
        }
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GRAY + "Hráči:")) {

            if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null) {
                return;
            }

            String headName = e.getCurrentItem().getItemMeta().getDisplayName();

            headName = headName.substring(2);


            Player target = plugin.getServer().getPlayer(headName);

            if (target == null ) {
                return;
            }

            p.teleport(target.getLocation());

            p.sendMessage(ChatColor.DARK_GREEN + "Teleportuji tě k hráči " + ChatColor.GOLD + target.getName());

            p.closeInventory();
        }
    }

    public void showGui(Player player) {

        Inventory inventory = Bukkit.createInventory(player, 9, ChatColor.GRAY + "Hráči:");

        int blueStartIndex = 0;

        int redStartIndex = 8;

        for (Player p : plugin.getServer().getOnlinePlayers()) {

            int team = Uttils.getTeam(player);

            if (team == 0) {

                ItemStack itemStack = Uttils.getItem(getPlayerHead(p.getName()), ChatColor.BLUE + p.getName());
                inventory.setItem(blueStartIndex, itemStack);
                blueStartIndex++;

            } else if (team == 1) {

                ItemStack itemStack = Uttils.getItem(getPlayerHead(p.getName()), ChatColor.RED + p.getName());
                inventory.setItem(redStartIndex, itemStack);
                redStartIndex--;

            }

        }

        player.openInventory(inventory);

    }

    public ItemStack getPlayerHead(String player) {

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwner(player);

        return item;
    }

}
