package Me.JeNDS.Utilities;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class AutoPickUp extends Utility {


    public AutoPickUp(Player player, BlockBreakEvent event) {
        if (SpaceInInventory(player)) {
            addItems(player,event.getBlock().getDrops());
            new AutoEXP(player,event);
            new AutoBlock(player);
            new AutoSmelt(player);

        } else {
            new AutoSell(player);
        }
    }

    private void addItems(Player player, Collection<ItemStack> items){
        for(ItemStack itemStack : items){
            player.getInventory().addItem(itemStack);
        }
    }


}
