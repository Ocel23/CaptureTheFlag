package me.ocel.capturetheflag.game.shop;

import me.ocel.capturetheflag.CaptureTheFlag;
import me.ocel.capturetheflag.game.shop.guis.*;
import me.ocel.capturetheflag.game.shop.specialItems.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ShopGuiManager implements CommandExecutor, Listener {

    private final CaptureTheFlag plugin;

    private Inventory inventory;

    private final MainMenu mainMenu;

    private final BlocksGui blocksGui;

    private final WeaponsGui weaponsGui;

    private final ChestplatesGui chestplatesGui;

    private final FoodGui foodGui;

    private final ToolsGui toolsGui;

    private final BowsGui bowsGui;

    private final ChestsGui chestsGui;

    private final PotionsGui potionsGui;

    private final ConvenienceGui convenienceGui;

    private final FreezeBomb freezeBomb;

    private final KamikazePig kamikazePig;

    private final SnowBall snowBall;

    private final JetPack jetPack;

    private final LuckyBlock luckyBlock;

    public ShopGuiManager(CaptureTheFlag plugin) {
        this.plugin = plugin;
        this.mainMenu = new MainMenu();
        this.blocksGui = new BlocksGui();
        this.weaponsGui = new WeaponsGui();
        this.chestplatesGui = new ChestplatesGui();
        this.foodGui = new FoodGui();
        this.toolsGui = new ToolsGui();
        this.bowsGui = new BowsGui();
        this.chestsGui = new ChestsGui();
        this.potionsGui = new PotionsGui();
        this.convenienceGui = new ConvenienceGui();

        this.freezeBomb = new FreezeBomb(plugin);
        this.kamikazePig = new KamikazePig(plugin);
        this.snowBall = new SnowBall(plugin);
        this.jetPack = new JetPack(plugin);
        this.luckyBlock = new LuckyBlock(plugin);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }

        inventory = Bukkit.createInventory(player, 9 * 3, ChatColor.GRAY + "Obchod");

        mainMenu.showGui(inventory);

        blocksGui.showGui(inventory, player);

        player.openInventory(inventory);

        return true;
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GRAY + "Obchod")) {

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.WHITE_WOOL) {

                blocksGui.showGui(inventory, player);

            } else if (e.getCurrentItem().getType() == Material.IRON_SWORD) {

                weaponsGui.showGui(inventory);

            } else if (e.getCurrentItem().getType() == Material.CHAINMAIL_CHESTPLATE) {

                chestplatesGui.showGui(inventory, player);

            } else if (e.getCurrentItem().getType() == Material.APPLE) {

                foodGui.showGui(inventory);

            } else if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE) {

                toolsGui.showGui(inventory);

            } else if (e.getCurrentItem().getType() == Material.BOW) {

                bowsGui.showGui(inventory);

            } else if (e.getCurrentItem().getType() == Material.CHEST) {

                chestsGui.showGui(inventory);

            } else if (e.getCurrentItem().getType() == Material.POTION) {

                potionsGui.showGui(inventory);

            } else if (e.getCurrentItem().getType() == Material.TNT) {

                convenienceGui.showGui(inventory);

            }

            e.setCancelled(true);

            if (e.getSlot() > 8) {

                Inventory inventory1 = player.getInventory();

                ItemMeta itemMeta = e.getCurrentItem().getItemMeta();

                if (itemMeta == null) {
                    return;
                }

                if (itemMeta.getLore() == null) {
                    return;
                }

                String color = itemMeta.getLore().get(1).substring(1, 2);

                int price = Integer.parseInt(itemMeta.getLore().get(1).substring(4, 5));

                Material material;


                if (color.equalsIgnoreCase("6")) {
                    material = Material.COPPER_INGOT;
                } else if (color.equalsIgnoreCase("f")) {
                    material = Material.IRON_INGOT;
                } else if (color.equalsIgnoreCase("b")){
                    material = Material.DIAMOND;
                } else {
                    material = null;
                }

                if (material == null) {
                    return;
                }

                if (inventory1.firstEmpty() == -1) {
                    player.sendMessage(ChatColor.RED + "Vyprázdni si nejdříve inventář!");
                    return;
                } else if (inventory1.contains(material, price)) {

                    ItemStack givenItem = new ItemStack(e.getCurrentItem().getType(), e.getCurrentItem().getAmount());

                    if (givenItem.getItemMeta() == null) {
                        return;
                    }

                    if (e.getClick().isShiftClick()) {

                        int countOfBuyItems = 0;

                        for (ItemStack item : inventory1.getContents()) {

                            if (item == null) {
                                continue;
                            }

                            if (item.getType() == material) {

                                int amount = item.getAmount();

                                for (int i = 0; i <= amount; i++) {


                                    int newAmount = item.getAmount();

                                    if (newAmount < price) {
                                        break;
                                    }

                                    item.setAmount(newAmount - price);
                                    itemMeta.setLore(new ArrayList<>());
                                    givenItem.setItemMeta(itemMeta);
                                    inventory1.addItem(givenItem);
                                    countOfBuyItems += givenItem.getAmount();
                                }
                            }
                        }

                        player.sendMessage(ChatColor.GREEN + "Zakoupil jsi úspěšně " + ChatColor.WHITE +  countOfBuyItems + " " + ChatColor.GREEN + givenItem.getItemMeta().getDisplayName());
                        return;
                    }

                    for (ItemStack item : inventory1.getContents()) {

                        if (item == null) {
                            continue;
                        }

                        if (item.getType() == material) {

                           int amount = item.getAmount();

                           item.setAmount(amount - price);
                           break;

                        }
                    }

                    itemMeta.setLore(new ArrayList<>());
                    givenItem.setItemMeta(itemMeta);
                    inventory1.addItem(givenItem);

                    player.sendMessage(ChatColor.GREEN + "Zakoupil jsi úspěšně " + ChatColor.WHITE +  givenItem.getAmount() + " " + ChatColor.GREEN + givenItem.getItemMeta().getDisplayName());
                    return;
                }

                player.sendMessage(ChatColor.RED + "Pro zakoupení nemáš dostatek materiálu.");

            }

        }
    }
}
