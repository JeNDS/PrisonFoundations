package JeNDS.Plugins.PrisonFundations.Mines.MinesGUI.MineOptions.MineManageReset;

import JeNDS.Plugins.PrisonFundations.Managers.PFGUI;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.PF;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MineManageResetTimeMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineManageResetTimeMenu(PFGUI lastMenu, Mine mine) {
        this.lastMenu = lastMenu;
        this.mine = mine;

        addItems(Material.EMERALD_BLOCK, Presets.MainColor + "Add 5 Minutes", null, 12, this);
        addItems(Material.EMERALD_BLOCK, Presets.MainColor + "Add 10 Minutes", null, 21, this);
        addItems(Material.EMERALD_BLOCK, Presets.MainColor + "Add 25 Minutes", null, 30, this);
        addItems(Material.EMERALD_BLOCK, Presets.MainColor + "Add 50 Minutes", null, 39, this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor + "Remove 5 Minutes", null, 14, this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor + "Remove 10 Minutes", null, 23, this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor + "Remove 25 Minutes", null, 32, this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor + "Remove 50 Minutes", null, 41, this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor + "Back", null, 45, this);

        setMenuAndInterface(Presets.SecondaryColor + mine.getName() + " Manage Time Reset Menu " + mine.getMineResetTime()+"M", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
        if (itemAndSlot.get(12).isSimilar(itemStack)) {
            modifyMenuTime(player,+5);
            return true;
        }
        if (itemAndSlot.get(21).isSimilar(itemStack)) {
            modifyMenuTime(player,+10);
            return true;
        }
        if (itemAndSlot.get(30).isSimilar(itemStack)) {
            modifyMenuTime(player,+25);
            return true;
        }
        if (itemAndSlot.get(39).isSimilar(itemStack)) {
            modifyMenuTime(player,+50);
            return true;
        }
        if (itemAndSlot.get(14).isSimilar(itemStack)) {
            modifyMenuTime(player,-5);
            return true;
        }
        if (itemAndSlot.get(23).isSimilar(itemStack)) {
            modifyMenuTime(player,-10);
            return true;
        }
        if (itemAndSlot.get(32).isSimilar(itemStack)) {
            modifyMenuTime(player,-25);
            return true;
        }
        if (itemAndSlot.get(41).isSimilar(itemStack)) {
            modifyMenuTime(player,-50);
            return true;
        }
        return false;
    }

    private void modifyMenuTime(Player player, int time) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine m : Catch.RunningMines) {
                if (m.getName().contains(mine.getName())) {
                    if(m.getMineResetTime()+ time >= 5){
                        m.setMineResetTime(m.getMineResetTime()+time);
                        MineManageResetTimeMenu mineManageResetTimeMenu = new MineManageResetTimeMenu(lastMenu,mine);
                        player.openInventory(mineManageResetTimeMenu.getMenu());
                    }
                    else {
                        m.setMineResetTime(5);
                        MineManageResetTimeMenu mineManageResetTimeMenu = new MineManageResetTimeMenu(lastMenu,mine);
                        player.openInventory(mineManageResetTimeMenu.getMenu());
                    }
                }
            }
        }
    }
}
