package me.ocel.capturetheflag.game.shop.guis;

import me.ocel.capturetheflag.Uttils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ToolsGui {

    public void showGui(Inventory inventory) {

        inventory.setItem(9, Uttils.getItem(new ItemStack(Material.STONE_PICKAXE), "&7Kamenný krumpáč", "", "&6&l8 bronzů", ""));
        inventory.setItem(10, Uttils.getItem(new ItemStack(Material.IRON_PICKAXE), "&fIron krumpáč", "", "&f&l4 železa", ""));
        inventory.setItem(11, Uttils.getItem(new ItemStack(Material.DIAMOND_PICKAXE), "&bDiamantový krumpáč", "", "&b&l4 diamanty", ""));
        inventory.setItem(12, Uttils.getItem(new ItemStack(Material.STONE_AXE), "&7Kamenná sekera", "", "&6&l8 bronzů", ""));
        inventory.setItem(13, Uttils.getItem(new ItemStack(Material.IRON_AXE), "&fŽelezná sekera", "", "&f&l4 železa", ""));
        inventory.setItem(14, Uttils.getItem(new ItemStack(Material.DIAMOND_AXE), "&bDiamantová sekera", "", "&b&l4 diamanty", ""));
        inventory.setItem(15, Uttils.getItem(new ItemStack(Material.SHEARS), "&6Nůžky", "", "&f&l2 železa", ""));
        inventory.setItem(16, null);

    }

}
