package me.ocel.capturetheflag.game.shop.guis;

import me.ocel.capturetheflag.Uttils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FoodGui {

    public void showGui(Inventory inventory) {

        inventory.setItem(9, Uttils.getItem(new ItemStack(Material.BREAD), "&6Chleba", "", "&6&l2 bronzy", ""));
        inventory.setItem(10, Uttils.getItem(new ItemStack(Material.COOKED_BEEF), "&fMaso", "", "&6&l4 bronzy", ""));
        inventory.setItem(11, Uttils.getItem(new ItemStack(Material.GOLDEN_APPLE), "&eZlaté jablko", "", "&b&l1 diamant", ""));
        inventory.setItem(12, null);
        inventory.setItem(13, null);
        inventory.setItem(14, null);
        inventory.setItem(15, null);
        inventory.setItem(16, null);

    }
}
