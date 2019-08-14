package Me.JeNDS.Utilities.AutoBlock;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AutoBlockListener {


    public static void AutoBlock(Player player) {
        BlockMaking(player, Material.GOLD_INGOT, Material.GOLD_BLOCK);
        BlockMaking(player, Material.LAPIS_LAZULI, Material.LAPIS_BLOCK);
        BlockMaking(player, Material.IRON_INGOT, Material.IRON_BLOCK);
        BlockMaking(player, Material.COAL, Material.COAL_BLOCK);
        BlockMaking(player, Material.REDSTONE, Material.REDSTONE_BLOCK);
        BlockMaking(player, Material.DIAMOND, Material.DIAMOND_BLOCK);
        BlockMaking(player, Material.EMERALD, Material.EMERALD_BLOCK);
        BlockMaking(player, Material.QUARTZ, Material.QUARTZ_BLOCK);

    }

    private static boolean nullCheck(ItemStack itemStack) {
        if (itemStack != null) {
            if (itemStack.getType() != null) {
                return false;
            }
        }
        return true;
    }

    private static boolean SpaceInInventory(Player player) {
        for (ItemStack itemStack : player.getInventory().getStorageContents()) {
            if (itemStack == null) {
                return true;
            }
        }
        return false;
    }

    private static void BlockMaking(Player player, Material initialState, Material finalState) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null)
                if (itemStack.getType() != null) {
                    if (itemStack.getType().equals(initialState)) {
                        if (itemStack.getAmount() >= 9) {
                            Integer availableBlocks = itemStack.getAmount() / 9;
                            Integer leftOvers = itemStack.getAmount() % 9;
                            for (ItemStack goldBlock : player.getInventory().getContents()) {
                                if (!nullCheck(goldBlock)) {
                                    if (goldBlock.getType().equals(finalState)) {
                                        if (goldBlock.getAmount() <= 63) {
                                            while (goldBlock.getAmount() < 64 && availableBlocks > 0) {
                                                goldBlock.setAmount(goldBlock.getAmount() + 1);
                                                availableBlocks--;
                                            }
                                        }
                                    }
                                }
                            }
                            if (availableBlocks > 0) {
                                if (SpaceInInventory(player)) {
                                    player.getInventory().addItem(new ItemStack(finalState, availableBlocks));

                                }
                            }
                            if (availableBlocks != itemStack.getAmount() / 9 || availableBlocks == 0) {
                                if (leftOvers > 0) {
                                    itemStack.setAmount(leftOvers);
                                }
                                if (leftOvers == 0) {
                                    itemStack.setAmount(leftOvers);
                                }
                            }
                        }
                    }
                }
        }
    }

}
