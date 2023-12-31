package me.ocel.capturetheflag.lobby.utils;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.Uttils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class TeamSelectGui implements Listener {

    private final CaptureTheFlag plugin;

    private final FileConfiguration configuration;

    public TeamSelectGui(CaptureTheFlag plugin) {
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void showGui(Player p) {

        if (p.getPlayer() == null) {
            return;
        }

        Inventory inventory = Bukkit.createInventory(p, 9 * 3, ChatColor.WHITE + "Výběr teamu:");


        ItemStack blueBanner = new ItemStack(Material.BLUE_BANNER);

        ItemStack redBanner = new ItemStack(Material.RED_BANNER);


        ItemMeta blueBannerItemMeta = blueBanner.getItemMeta();


        ItemMeta redBannerItemMeta = blueBanner.getItemMeta();

        if (blueBannerItemMeta == null || redBannerItemMeta == null) {
            return;
        }

        blueBannerItemMeta.setDisplayName(ChatColor.BLUE + "Modrý team");

        redBannerItemMeta.setDisplayName(ChatColor.RED + "Červený team");

        List<String> redLores = new ArrayList<>();
        List<String> blueLores = new ArrayList<>();


        for (Player player : plugin.getServer().getOnlinePlayers()) {

            int team = Uttils.getTeam(player);

            if (team == 0) {
                blueLores.add(ChatColor.WHITE + player.getName());
            } else if (team == 1){
                redLores.add(ChatColor.WHITE + player.getName());
            }
        }

        blueBannerItemMeta.setLore(blueLores);

        redBannerItemMeta.setLore(redLores);

        blueBanner.setItemMeta(blueBannerItemMeta);

        redBanner.setItemMeta(redBannerItemMeta);

        inventory.setItem(12, blueBanner);

        inventory.setItem(14, redBanner);

        p.openInventory(inventory);
    }

    public static void addItem(Player p) {

        Inventory inventory = p.getInventory();

        inventory.addItem(Uttils.getItem(new ItemStack(Material.WHITE_BANNER), "&2Výběr teamu", "&fPravím kliknutím zobrazíš menu pro vybrání teamu."));
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getItem() == null) {
            return;
        }

        if (e.getItem().getType() == Material.WHITE_BANNER) {
            showGui(p);
        }
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.WHITE + "Výběr teamu:")) {

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.BLUE_BANNER) {
                if (!(protectCount())) {
                    p.sendMessage(ChatColor.RED +  "Tento tento tým je už nyní plný.");
                    p.closeInventory();
                    return;
                }
                p.sendMessage(ChatColor.BLUE + "Připojil jsi se do modrého teamu.");
                p.setPlayerListName(ChatColor.BLUE + p.getName());
                p.closeInventory();

            } else if(e.getCurrentItem().getType() == Material.RED_BANNER) {
                if (!(protectCount())) {
                    p.sendMessage(ChatColor.RED +  "Tento tento tým je už nyní plný.");
                    p.closeInventory();
                    return;
                }
                p.sendMessage(ChatColor.RED + "Připojil jsi se do červeného teamu.");
                p.setPlayerListName(ChatColor.RED + p.getName());
                p.closeInventory();

            }

            e.setCancelled(true);
        }

    }

    //helper method for protect count for max players to team
    private boolean protectCount() {

        int redCountOfPlayers = 0;
        int blueCountOfPlayer = 0;

        int maxCount = configuration.getInt("countPlayersForStartGame") / 2;

        for (Player player : plugin.getServer().getOnlinePlayers()) {

            int team = Uttils.getTeam(player);

            if (team == 0) {
                if (blueCountOfPlayer == maxCount) {
                    return false;
                }
                blueCountOfPlayer++;
            } else if (team == 1) {
                if (redCountOfPlayers == maxCount) {
                    return false;
                }
                redCountOfPlayers++;
            }

        }

        return true;
    }

}
