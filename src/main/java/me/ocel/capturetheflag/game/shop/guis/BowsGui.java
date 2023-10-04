package me.ocel.capturetheflag.game.shop.guis;

import me.ocel.capturetheflag.Uttils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BowsGui {

    public void showGui(Inventory inventory) {

        ItemStack bowLvl1 = Uttils.getItem(new ItemStack(Material.BOW), "&6Luk lv 1", "", "&b&l2 diamanty", "");
        ItemStack bowLvl2 = Uttils.getItem(new ItemStack(Material.BOW), "&6Luk lv 2",  "", "&b&l4 diamanty", "");
        ItemStack bowLvl3 = Uttils.getItem(new ItemStack(Material.BOW), "&6Luk lv 3",  "", "&b&l6 diamanty", "");

        if (bowLvl1 == null || bowLvl2 == null || bowLvl3 == null) {
            return;
        }

        ItemMeta bowLvl1ItemMeta = bowLvl1.getItemMeta();
        ItemMeta bowLvl2ItemMeta = bowLvl2.getItemMeta();
        ItemMeta bowLvl3ItemMeta = bowLvl3.getItemMeta();

        if (bowLvl1ItemMeta == null || bowLvl2ItemMeta == null || bowLvl3ItemMeta == null) {
            return;
        }

        bowLvl1ItemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        bowLvl1ItemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        bowLvl2ItemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
        bowLvl2ItemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
        bowLvl2ItemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        bowLvl3ItemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
        bowLvl3ItemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        bowLvl3ItemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        bowLvl3ItemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);


        bowLvl1.setItemMeta(bowLvl1ItemMeta);
        bowLvl2.setItemMeta(bowLvl2ItemMeta);
        bowLvl3.setItemMeta(bowLvl3ItemMeta);


        inventory.setItem(9, bowLvl1);
        inventory.setItem(10, bowLvl2);
        inventory.setItem(11, bowLvl3);
        inventory.setItem(12, Uttils.getItem(new ItemStack(Material.ARROW), "&eŠíp", "", "&b&l1 diamant", ""));
        inventory.setItem(13, null);
        inventory.setItem(14, null);
        inventory.setItem(15, null);
        inventory.setItem(16, null);

    }

}
