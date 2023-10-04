package me.ocel.capturetheflag.game.shop.specialItems;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.Uttils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LuckyBlock implements Listener {

    private final CaptureTheFlag plugin;

    private final ItemStack[] items = new ItemStack[8];
    public LuckyBlock(CaptureTheFlag plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        fillArray();
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {

        if (e.getBlock().getType() == Material.SPONGE) {

            e.setDropItems(false);

            Random random = new Random();

            ItemStack randomItem = items[random.nextInt(8)];

            Player player = e.getPlayer();

            player.getWorld().dropItemNaturally(e.getBlock().getLocation(), randomItem);

            if (randomItem.getType() == Material.DIAMOND_SWORD) {
                plugin.getServer().broadcastMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " získal legendární meč!");
            }
        }
    }

    private void fillArray() {

        ItemStack legendarySword = Uttils.getItem(new ItemStack(Material.DIAMOND_SWORD), "&b&lLegendární meč");

        if (legendarySword == null) {
            return;
        }

        legendarySword.addEnchantment(Enchantment.DAMAGE_ALL, 3);

        items[0] = legendarySword;

        ItemStack bowLvl2 = Uttils.getItem(new ItemStack(Material.BOW), "&6Luk lv 2");

        if (bowLvl2 == null) {
            return;
        }

        bowLvl2.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
        bowLvl2.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        bowLvl2.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        items[1] = bowLvl2;

        ItemStack knockbackStick = Uttils.getItem(new ItemStack(Material.STICK), "&5Odhazující tyčka", "", "&6&l16 bronzů", "");

        if (knockbackStick == null) {
            return;
        }

        knockbackStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);

        items[2] = knockbackStick;

        ItemStack stoneSword = Uttils.getItem(new ItemStack(Material.STONE_SWORD), "&7Kamenný meč");

        items[3] = stoneSword;

        ItemStack ironPickaxe = Uttils.getItem(new ItemStack(Material.IRON_PICKAXE), "&fŽelezný krumpáč");

        items[4] = ironPickaxe;

        ItemStack goldenApple = Uttils.getItem(new ItemStack(Material.GOLDEN_APPLE), "&eZlaté jablko");

        items[5] = goldenApple;

        ItemStack bread = Uttils.getItem(new ItemStack(Material.BREAD), "&6Chleba");

        items[6] = bread;

        ItemStack tnt = Uttils.getItem(new ItemStack(Material.TNT), "&6TNT");

        items[7] = tnt;
    }

}
