package me.ocel.capturetheflag.game.shop.guis;

import me.ocel.capturetheflag.Uttils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class PotionsGui {

    public void showGui(Inventory inventory) {

        ItemStack healPotion = Uttils.getItem(new ItemStack(Material.POTION), "&cOživující lektvar", "", "&f&l6 železa", "");
        ItemStack speedPotion = Uttils.getItem(new ItemStack(Material.POTION), "&bZrychlující lektvar", "", "&f&l8 železa", "");
        ItemStack strengthPotion = Uttils.getItem(new ItemStack(Material.POTION), "&eLektvar síly", "", "&b&l2 diamanty", "");

        if (healPotion == null || speedPotion == null || strengthPotion == null) {
            return;
        }

        PotionMeta healPotionMeta = (PotionMeta) healPotion.getItemMeta();
        PotionMeta speedPotionMeta = (PotionMeta) speedPotion.getItemMeta();
        PotionMeta strengPotionMeta = (PotionMeta) strengthPotion.getItemMeta();


        if (healPotionMeta == null || speedPotionMeta == null || strengPotionMeta == null) {
            return;
        }

        healPotionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        speedPotionMeta.setBasePotionData(new PotionData(PotionType.SPEED));
        strengPotionMeta.setBasePotionData(new PotionData(PotionType.STRENGTH));

        healPotion.setItemMeta(healPotionMeta);
        speedPotion.setItemMeta(speedPotionMeta);
        strengthPotion.setItemMeta(strengPotionMeta);

        inventory.setItem(9, healPotion);
        inventory.setItem(10, speedPotion);
        inventory.setItem(11, strengthPotion);
        inventory.setItem(12, null);
        inventory.setItem(13, null);
        inventory.setItem(14, null);
        inventory.setItem(15, null);
        inventory.setItem(16, null);

    }

}
