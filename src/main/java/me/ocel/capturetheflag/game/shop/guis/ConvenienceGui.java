package me.ocel.capturetheflag.game.shop.guis;

import me.ocel.capturetheflag.Uttils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class ConvenienceGui {

    public void showGui(Inventory inventory) {

        inventory.setItem(9, Uttils. getItem(new ItemStack(Material.TNT), "&6TNT", "", "&f&l6 železa", ""));
        inventory.setItem(10, Uttils.getItem(new ItemStack(Material.ENDER_PEARL), "&5Ender perla", "", "&b&l4 diamanty", ""));
        inventory.setItem(11, Uttils.getItem(new ItemStack(Material.COBWEB), "&fPavučina", "", "&f&l2 železa", ""));
        inventory.setItem(12, Uttils.getItem(new ItemStack(Material.PINK_GLAZED_TERRACOTTA), "&dKamikaze prase", "", "&f&l4 železa", ""));
        inventory.setItem(13, Uttils.getItem(new ItemStack(Material.SNOWBALL), "&eToxická koule", "", "&f&l2 železa", ""));
        inventory.setItem(14, Uttils.getItem(new ItemStack(Material.FIREWORK_ROCKET), "&fJetpack", "", "&b&l1 diamant", ""));
        inventory.setItem(15, Uttils.getItem(new ItemStack(Material.WARPED_PRESSURE_PLATE), "&bMrazivá momba", "", "&f&l6 železa", ""));
        inventory.setItem(16, Uttils.getItem(new ItemStack(Material.SPONGE), "&eLucky blok", "", "&f&l8 železa"));

    }

}
