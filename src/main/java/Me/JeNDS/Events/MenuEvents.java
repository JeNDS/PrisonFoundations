package Me.JeNDS.Events;

import Me.JeNDS.Objects.MineObjects.BlockType;
import Me.JeNDS.PlayerGUI.MinesGUI.MineOptions.MineBlockTypes.MineResetTypeMenu;
import Me.JeNDS.Static.Catch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;

public class MenuEvents extends EventManager {

    @EventHandler
    public void closeMenu(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Catch.selectingBlockType.remove((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void addMenuItemType(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (event.getWhoClicked() instanceof Player player) {
                if (Catch.selectingBlockType.containsKey(player)) {
                    MineResetTypeMenu mineResetTypeMenu = (MineResetTypeMenu) Catch.selectingBlockType.get(player);
                    if (!mineResetTypeMenu.getMine().containsBlockType(event.getCurrentItem().getType())) {
                        BlockType newType = new BlockType(event.getCurrentItem().getType(), 20);
                        ArrayList<BlockType> newBlockTypes = new ArrayList<>(mineResetTypeMenu.getMine().getBlockTypes());
                        newBlockTypes.add(newType);
                        mineResetTypeMenu.getMine().updateBlockTypes(newBlockTypes);
                        MineResetTypeMenu mineResetTypeMenu1 = new MineResetTypeMenu(mineResetTypeMenu.getLastMenu(), mineResetTypeMenu.getMine());
                        player.openInventory(mineResetTypeMenu1.getMenu());
                        Catch.selectingBlockType.remove(player);
                    }
                }
            }
        }

    }
}
