package Me.JeNDS.Utilities.AutoSmelt;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AutoSmeltListener {

    public static ItemStack AutoSmelter(ItemStack itemStack){

        if (itemStack != null){
            if (itemStack.getType() != null){
                if (itemStack.getType().equals(Material.COBBLESTONE)){
                    return new ItemStack(Material.STONE,itemStack.getAmount());
                }
                if (itemStack.getType().equals(Material.GOLD_ORE)){
                    return new ItemStack(Material.GOLD_INGOT,itemStack.getAmount());
                }
                if (itemStack.getType().equals(Material.IRON_ORE)){
                    return new ItemStack(Material.IRON_INGOT,itemStack.getAmount());
                }
                if (itemStack.getType().equals(Material.NETHER_QUARTZ_ORE)){
                    return new ItemStack(Material.QUARTZ,itemStack.getAmount());
                }
            }
        }
        return null;

    }

}
