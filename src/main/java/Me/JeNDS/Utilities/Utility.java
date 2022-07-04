package Me.JeNDS.Utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utility {
    protected static boolean nullCheck(ItemStack itemStack) {
        if (itemStack != null) {
            if (itemStack.getType() != null) {
                return false;
            }
        }
        return true;
    }
    protected static boolean SpaceInInventory(Player player) {
        for (ItemStack itemStack : player.getInventory().getStorageContents()) {
            if (itemStack == null) {
                return true;
            }
        }
        return false;
    }
}
