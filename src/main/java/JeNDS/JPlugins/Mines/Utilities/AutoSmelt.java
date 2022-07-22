package JeNDS.JPlugins.Mines.Utilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static JeNDS.JPlugins.Static.Presets.ValidVersion;

public class AutoSmelt {
    private final Player player;

    public AutoSmelt(Player player) {
        this.player = player;
        smelter();


    }

    private void smelter() {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                addItemToPlayer(Material.COBBLESTONE, Material.STONE, itemStack);
                addItemToPlayer(Material.GOLD_ORE, Material.GOLD_INGOT, itemStack);
                addItemToPlayer(Material.IRON_ORE, Material.IRON_INGOT, itemStack);
                addItemToPlayer(Material.NETHER_QUARTZ_ORE, Material.QUARTZ, itemStack);
                if(ValidVersion(1.15)){
                    addItemToPlayer(Material.NETHERRACK, Material.NETHER_BRICK, itemStack);
                }
                if(ValidVersion(1.16)){
                    addItemToPlayer(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP, itemStack);
                }
                if(ValidVersion(1.17)){
                    addItemToPlayer(Material.RAW_COPPER, Material.COPPER_INGOT, itemStack);
                    addItemToPlayer(Material.RAW_IRON_BLOCK, Material.IRON_INGOT, itemStack);
                    addItemToPlayer(Material.RAW_IRON, Material.IRON_INGOT, itemStack);
                    addItemToPlayer(Material.RAW_GOLD, Material.GOLD_INGOT, itemStack);
                }
            }
        }

    }

    private void addItemToPlayer(Material input, Material output, ItemStack itemStack) {
        if (itemStack.getType().equals(input)) {
            player.getInventory().removeItem(itemStack);
            player.getInventory().addItem(new ItemStack(output, itemStack.getAmount()));
        }
    }

}
