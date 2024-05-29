package JeNDS.Plugins.PrisonFundations.Mines.Utilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utility {
    public static boolean nullCheck(ItemStack itemStack) {
        if (itemStack != null) {
            itemStack.getType();
            return false;
        }
        return true;
    }

    public static boolean SpaceInInventory(Player player) {
        for (ItemStack itemStack : player.getInventory().getStorageContents()) {
            if (itemStack == null) {
                return true;
            }
        }
        return false;
    }

    public void combinePlayerInventory(Player player) {
        for (int i = 0; i < player.getInventory().getStorageContents().length; i++) {
            for (int i2 = 0; i2 < player.getInventory().getStorageContents().length; i2++) {
                if (isValidItem(player.getInventory().getItem(i))) {
                    if (isValidItem(player.getInventory().getItem(i2))) {
                        ItemStack itemStack1 = player.getInventory().getItem(i);
                        ItemStack itemStack2 = player.getInventory().getItem(i2);
                        if (itemStack1.isSimilar(itemStack2) && i!=i2) {
                            int amount = itemStack1.getAmount() + itemStack2.getAmount();
                            if (amount <= 64) {
                                player.getInventory().getItem(i).setAmount(amount);
                                player.getInventory().getItem(i2).setAmount(0);
                            } else {
                                itemStack1.setAmount(64);
                                player.getInventory().getItem(i).setAmount(64);
                                player.getInventory().getItem(i2).setAmount(amount - 64);
                            }
                            if (amount >= 64) {
                                break;
                            }

                        }
                    }
                } else {
                    break;
                }
            }
        }
    }

    private boolean isValidItem(ItemStack itemStack) {
        if (itemStack != null) {
            if (itemStack.getType() != Material.AIR) {
                return itemStack.getAmount() < 64;
            }
        }
        return false;
    }
}
