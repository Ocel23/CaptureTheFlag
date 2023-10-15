package me.ocel.capturetheflag.game.shop.guis;

import me.ocel.capturetheflag.Uttils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WeaponsGui {

    public void showGui(Inventory inventory) {

        ItemStack knockbackStick = Uttils.getItem(new ItemStack(Material.STICK), "&5Odhazující tyčka", "", "&6&l16 bronzů", "");

        if (knockbackStick == null) {
            return;
        }

        knockbackStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);

        inventory.setItem(9, knockbackStick);
        inventory.setItem(10, Uttils.getItem(new ItemStack(Material.WOODEN_SWORD), "&6Dřevěný meč", "", "&6&l8 bronzů", ""));
        inventory.setItem(11, Uttils.getItem(new ItemStack(Material.STONE_SWORD), "&7Kamenný meč", "", "&6&l24 bronzů", ""));
        inventory.setItem(12, Uttils.getItem(new ItemStack(Material.IRON_SWORD), "&fŽelezný meč", "", "&f&l4 železa", ""));
        inventory.setItem(13, Uttils.getItem(new ItemStack(Material.DIAMOND_SWORD), "&bDiamantový meč", "", "&b&l4 diamanty", ""));
        inventory.setItem(14, null);
        inventory.setItem(15, null);
        inventory.setItem(16, null);

    }

}
