package me.ocel.capturetheflag.game.shop.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    public void showGui(Inventory inventory) {

        inventory.setItem(0, getItem(new ItemStack(Material.WHITE_WOOL), "&f&lBloky"));

        inventory.setItem(1, getItem(new ItemStack(Material.IRON_SWORD), "&f&lZbraně"));

        inventory.setItem(2, getItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "&7&lBrnění"));

        inventory.setItem(3, getItem(new ItemStack(Material.APPLE), "&c&lJídlo"));

        inventory.setItem(4, getItem(new ItemStack(Material.WOODEN_PICKAXE), "&2&lNástroje"));

        inventory.setItem(5, getItem(new ItemStack(Material.BOW), "&a&lLuky"));

        inventory.setItem(6, getItem(new ItemStack(Material.CHEST), "&e&lTruhly"));

        inventory.setItem(7, getItem(new ItemStack(Material.POTION), "&5&lLektvary"));

        inventory.setItem(8, getItem(new ItemStack(Material.TNT), "&6&lVymoženosti"));

    }

    private ItemStack getItem(ItemStack item, String name, String ...lore) {

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        List<String> lores = new ArrayList<>();
        for (String s : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(lores);

        item.setItemMeta(meta);

        return item;
    }
}
