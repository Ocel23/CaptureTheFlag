package me.ocel.capturetheflag.game.shop.guis;

import me.ocel.capturetheflag.Uttils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlocksGui {


    public void showGui(Inventory inventory, Player player) {

        if (player.getPlayerListName().equalsIgnoreCase(ChatColor.BLUE + player.getName())) {
            inventory.setItem(9, Uttils.getItem(new ItemStack(Material.BLUE_WOOL, 4), "&9Modrá vlna", "", "&6&l4 bronzy", ""));
        } else {
            inventory.setItem(9, Uttils.getItem(new ItemStack(Material.RED_WOOL, 4), "&cČervená vlna", "", "&6&l4 bronzy", ""));
        }

        inventory.setItem(10, Uttils.getItem(new ItemStack(Material.TERRACOTTA, 12), "&cTerakota",  "", "&6&l30 bronzů", ""));
        inventory.setItem(11, Uttils.getItem(new ItemStack(Material.OAK_PLANKS, 16), "&6Plenky",  "", "&f&l4 železa", ""));
        inventory.setItem(12, Uttils.getItem(new ItemStack(Material.END_STONE, 8), "&eEndstone",  "", "&b&l1 diamant", ""));
        inventory.setItem(13, null);
        inventory.setItem(14, null);
        inventory.setItem(15, null);
        inventory.setItem(16, null);

    }

}
