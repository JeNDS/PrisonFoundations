package JeNDS.Plugins.PrisonFundations.Mines.MinesGUI.MineOptions.MineManageReset;

import JeNDS.Plugins.PrisonFundations.Main;
import JeNDS.Plugins.PrisonFundations.Managers.PFGUI;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MineManageResetMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineManageResetMenu(PFGUI lastMenu, Mine mine) {
        this.lastMenu = lastMenu;
        this.mine = mine;
        addItems(Material.DIAMOND_PICKAXE, Presets.MainColor + "Percentage Reset", null, 22, this);
        addItems(Material.CLOCK, Presets.MainColor + "Time Reset", null, 31, this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor + "Back", null, 45, this);

        setMenuAndInterface(Presets.SecondaryColor + mine.getConfigName() + " Manage Reset Menu", 54, InventoryType.CHEST, true, fillItem(), Main.getInstance());
    }


    @Override
    protected boolean rightClickEvents(ItemStack itemStack, Player player) {
        return false;
    }

    @Override
    protected boolean leftClickEvents(ItemStack itemStack, Player player) {
        if (itemAndSlot.get(45).isSimilar(itemStack)) {
            player.openInventory(lastMenu.getMenu());
            return true;
        }
        if (itemAndSlot.get(31).isSimilar(itemStack)) {
            MineManageResetTimeMenu mineManageResetTimeMenu = new MineManageResetTimeMenu(this, mine);
            player.openInventory(mineManageResetTimeMenu.getMenu());
            return true;
        }
        if (itemAndSlot.get(22).isSimilar(itemStack)) {
            MineManageResetPercentageMenu mineManageResetPercentageMenu = new MineManageResetPercentageMenu(this, mine);
            player.openInventory(mineManageResetPercentageMenu.getMenu());
            return true;
        }
        return false;
    }
}
