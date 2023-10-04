package me.ocel.capturetheflag.game.shop.guis;

import me.ocel.capturetheflag.Uttils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestsGui {

    public void showGui(Inventory inventory) {

        inventory.setItem(9, Uttils.getItem(new ItemStack(Material.CHEST), "&6Dřevěná truhla", "", "&f&l2 železa", ""));
        inventory.setItem(10, Uttils.getItem(new ItemStack(Material.ENDER_CHEST), "&5Ender truhla", "", "&b&l1 diamant", ""));
        inventory.setItem(11, null);
        inventory.setItem(12, null);
        inventory.setItem(13, null);
        inventory.setItem(14, null);
        inventory.setItem(15, null);
        inventory.setItem(16, null);

    }

}
