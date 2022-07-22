package JeNDS.JPlugins.Mines.Utilities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class AutoPickUp extends Utility {

    public AutoPickUp(Player player, BlockDropItemEvent event) {
        if (SpaceInInventory(player)) {
            addItems(player, event.getItems());
        }
    }

    public AutoPickUp(Player player, ItemStack itemStack) {
        if (SpaceInInventory(player)) {
            addItems(player, itemStack);
        }
    }

    private void addItems(Player player, List<Item> items) {
        if (items.size() == 1 && items.get(0).getItemStack().getAmount() == 1) {
            if (playerHasFortune(player)) {
                fortune(player, items.get(0).getItemStack());
                return;
            }
        }
        for (Item itemStack : items) {
            player.getInventory().addItem(itemStack.getItemStack());
        }

    }

    private void addItems(Player player, ItemStack itemStack) {
        if (itemStack.getAmount() == 1) {
            if (playerHasFortune(player)) {
                fortune(player, itemStack);
                return;
            }
        }
        player.getInventory().addItem(itemStack);

    }

    private boolean playerHasFortune(Player player) {
        if (player.getInventory().getItemInMainHand().getType() != Material.AIR)
            if (player.getInventory().getItemInMainHand().hasItemMeta())
                return player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
        return false;
    }


    private void fortune(Player player, ItemStack itemStack) {
        if (playerHasFortune(player)) {
            Random r = new Random();
            int fortuneLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            ;
            int low = fortuneLevel / 2;
            int dropAmount = r.nextInt(fortuneLevel - low) + low;
            itemStack.setAmount(dropAmount);
            player.getInventory().addItem(itemStack);
        }
    }
}
