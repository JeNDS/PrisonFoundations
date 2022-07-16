package JeNDS.JPlugins.PlayerGUI.MinesGUI.MineOptions.MineManageReset;

import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Objects.MineObjects.Mine;
import JeNDS.JPlugins.PlayerGUI.PFGUI;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MineManageResetMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineManageResetMenu(PFGUI lastMenu, Mine mine){
        this.lastMenu = lastMenu;
        this.mine = mine;
        addItems(Material.DIAMOND_PICKAXE, Presets.DefaultColor +"Percentage Reset",null,22,this);
        addItems(Material.CLOCK, Presets.DefaultColor +"Time Reset",null,31,this);
        addItems(Material.REDSTONE_BLOCK, Presets.DefaultColor +"Back",null,45,this);

        setMenuAndInterface(Presets.StandOutColor + mine.getName() + " Manage Reset Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
    }


    @Override
    protected boolean rightClickEvents(ItemStack itemStack, Player player) {
        return false;
    }

    @Override
    protected boolean leftClickEvents(ItemStack itemStack, Player player) {
        if(itemAndSlot.get(45).isSimilar(itemStack)){
            player.openInventory(lastMenu.getMenu());
            return true;
        }
        if(itemAndSlot.get(31).isSimilar(itemStack)){
            MineManageResetTimeMenu mineManageResetTimeMenu = new MineManageResetTimeMenu(this,mine);
            player.openInventory(mineManageResetTimeMenu.getMenu());
            return true;
        }
        if(itemAndSlot.get(22).isSimilar(itemStack)){
            MineManageResetPercentageMenu mineManageResetPercentageMenu = new MineManageResetPercentageMenu(this,mine);
            player.openInventory(mineManageResetPercentageMenu.getMenu());
            return true;
        }
        return false;
    }
}
