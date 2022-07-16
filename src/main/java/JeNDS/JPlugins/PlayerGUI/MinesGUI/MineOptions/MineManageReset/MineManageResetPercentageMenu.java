package JeNDS.JPlugins.PlayerGUI.MinesGUI.MineOptions.MineManageReset;

import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Objects.MineObjects.Mine;
import JeNDS.JPlugins.PlayerGUI.PFGUI;
import JeNDS.JPlugins.Static.Catch;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MineManageResetPercentageMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineManageResetPercentageMenu(PFGUI lastMenu, Mine mine) {
        this.lastMenu = lastMenu;
        this.mine = mine;

        addItems(Material.EMERALD_BLOCK, Presets.DefaultColor + "Add 5%", null, 12, this);
        addItems(Material.EMERALD_BLOCK, Presets.DefaultColor + "Add 10%", null, 21, this);
        addItems(Material.EMERALD_BLOCK, Presets.DefaultColor + "Add 25%", null, 30, this);
        addItems(Material.EMERALD_BLOCK, Presets.DefaultColor + "Add 50%", null, 39, this);
        addItems(Material.REDSTONE_BLOCK, Presets.DefaultColor + "Remove 5%", null, 14, this);
        addItems(Material.REDSTONE_BLOCK, Presets.DefaultColor + "Remove 10%", null, 23, this);
        addItems(Material.REDSTONE_BLOCK, Presets.DefaultColor + "Remove 25%", null, 32, this);
        addItems(Material.REDSTONE_BLOCK, Presets.DefaultColor + "Remove 50%", null, 41, this);
        addItems(Material.REDSTONE_BLOCK, Presets.DefaultColor + "Back", null, 45, this);

        setMenuAndInterface(Presets.StandOutColor + mine.getName() + " Manage Percentage Reset Menu " + mine.getMinePercentageReset() + "%", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
            modifyMenuPercentage(player, +5);
            return true;
        }
        if (itemAndSlot.get(21).isSimilar(itemStack)) {
            modifyMenuPercentage(player, +10);
            return true;
        }
        if (itemAndSlot.get(30).isSimilar(itemStack)) {
            modifyMenuPercentage(player, +25);
            return true;
        }
        if (itemAndSlot.get(39).isSimilar(itemStack)) {
            modifyMenuPercentage(player, +50);
            return true;
        }
        if (itemAndSlot.get(14).isSimilar(itemStack)) {
            modifyMenuPercentage(player, -5);
            return true;
        }
        if (itemAndSlot.get(23).isSimilar(itemStack)) {
            modifyMenuPercentage(player, -10);
            return true;
        }
        if (itemAndSlot.get(32).isSimilar(itemStack)) {
            modifyMenuPercentage(player, -25);
            return true;
        }
        if (itemAndSlot.get(41).isSimilar(itemStack)) {
            modifyMenuPercentage(player, -50);
            return true;
        }
        return false;
    }

    private void modifyMenuPercentage(Player player, int percentage) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine m : Catch.RunningMines) {
                if (m.getName().contains(m.getName())) {
                    if (m.getMinePercentageReset() + percentage <= 95 && m.getMinePercentageReset() + percentage >= 5) {
                        resetPercentage(m.getMinePercentageReset() + percentage, player);
                    } else {
                        if (m.getMinePercentageReset() + percentage > 95) {
                            resetPercentage(95, player);
                        }
                        if (m.getMinePercentageReset() + percentage < 5) {
                            resetPercentage(5, player);
                        }
                    }
                    return;
                }
            }
        }
    }

    private void resetPercentage(int percentage, Player player) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine m : Catch.RunningMines) {
                if (m.getName().contains(m.getName())) {
                    m.setMinePercentageReset(percentage);
                    mine.setMinePercentageReset(percentage);
                    MineManageResetPercentageMenu mineManageResetPercentageMenu = new MineManageResetPercentageMenu(lastMenu, mine);
                    player.openInventory(mineManageResetPercentageMenu.getMenu());
                    return;
                }
            }
        }

    }
}
