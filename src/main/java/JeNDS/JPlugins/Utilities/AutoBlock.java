package JeNDS.JPlugins.Utilities;

import JeNDS.JPlugins.Main.PF;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static JeNDS.JPlugins.Static.Presets.ValidVersion;

public class AutoBlock extends Utility {


    public AutoBlock(Player player) {
        blockMaking(player, Material.GOLD_INGOT, Material.GOLD_BLOCK);
        blockMaking(player, Material.LAPIS_LAZULI, Material.LAPIS_BLOCK);
        blockMaking(player, Material.IRON_INGOT, Material.IRON_BLOCK);
        blockMaking(player, Material.COAL, Material.COAL_BLOCK);
        blockMaking(player, Material.REDSTONE, Material.REDSTONE_BLOCK);
        blockMaking(player, Material.DIAMOND, Material.DIAMOND_BLOCK);
        blockMaking(player, Material.EMERALD, Material.EMERALD_BLOCK);
        blockMaking(player, Material.QUARTZ, Material.QUARTZ_BLOCK);
        blockMaking(player, Material.GOLD_NUGGET, Material.GOLD_INGOT);
        if(ValidVersion(1.13)){
            blockMaking(player, Material.IRON_NUGGET, Material.IRON_INGOT);
        }
        if(ValidVersion(1.15)){
            blockMaking(player, Material.NETHER_BRICK, Material.NETHER_BRICKS);
        }
    }


    private void blockMaking(Player player, Material initialState, Material finalState) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                itemStack.getType();
                if (itemStack.getType().equals(initialState)) {
                    if (itemStack.getAmount() >= 9) {
                        int availableBlocks = itemStack.getAmount() / 9;
                        int leftOvers = itemStack.getAmount() % 9;
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
                        itemStack.setAmount(leftOvers);
                        combinePlayerInventory(player);
                    }
                }
            }
        }
    }

}
