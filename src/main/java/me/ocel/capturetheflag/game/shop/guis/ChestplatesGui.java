package me.ocel.capturetheflag.game.shop.guis;

import me.ocel.capturetheflag.Uttils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ChestplatesGui {


    public void showGui(Inventory inventory, Player player) {

        inventory.setItem(9, getLeatherArmor(Uttils.getItem(new ItemStack(Material.LEATHER_BOOTS), "&6Kožené boty", "", "&6&l1 bronz", ""), player));
        inventory.setItem(10, getLeatherArmor(Uttils.getItem(new ItemStack(Material.LEATHER_LEGGINGS), "&6Kožené kalhoty", "", "&6&l1 bronz", ""), player));
        inventory.setItem(11, getLeatherArmor(Uttils.getItem(new ItemStack(Material.LEATHER_HELMET), "&6Kožená helma", "", "&6&l1 bronz", ""), player));
        inventory.setItem(12, getLeatherArmor(Uttils.getItem(new ItemStack(Material.LEATHER_CHESTPLATE), "&6Kožené brnění", "", "&6&l1 bronz", ""), player));
        inventory.setItem(13, Uttils.getItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "&7Kroužkové brnění", "", "&f&l3 železa", ""));
        inventory.setItem(14, Uttils.getItem(new ItemStack(Material.DIAMOND_CHESTPLATE), "&bDiamantové brnění", "", "&b&l3 diamanty", ""));
        inventory.setItem(15, null);
        inventory.setItem(16, null);

    }

    private ItemStack getLeatherArmor(ItemStack leatherArmor, Player player) {

        if (leatherArmor == null) {
            return null;
        }

        ItemMeta leatherBootsMeta = leatherArmor.getItemMeta();

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherBootsMeta;

        if (leatherBootsMeta == null) {
            return null;
        }

        if (player.getPlayerListName().equalsIgnoreCase(ChatColor.BLUE + player.getName())) {
            leatherArmorMeta.setColor(Color.fromRGB(71, 127, 255));
        } else {
            leatherArmorMeta.setColor(Color.fromRGB(246, 37, 37));
        }

        leatherArmor.setItemMeta(leatherArmorMeta);

        return leatherArmor;
    }
}
