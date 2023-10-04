package me.ocel.capturetheflag;

import me.ocel.capturetheflag.gameMap.GameMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Uttils {

    public static Item spawnItem(Location location, ItemStack itemStack, GameMap gameMap) {

        Item item = gameMap.getWorld().dropItemNaturally(location, itemStack);

        return item;
    }

    public static ItemStack getItem(ItemStack item, String name, String ...lore) {

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

    public static int getTeam(Player player) {

        String playerListName = player.getPlayerListName();
        int team;

        if (playerListName.equalsIgnoreCase(ChatColor.BLUE + player.getName())) {
            team = 0;
        } else if (playerListName.equalsIgnoreCase(ChatColor.RED + player.getName())) {
            team = 1;
        } else {
            team = 2;
        }

        return team;
    }



}
