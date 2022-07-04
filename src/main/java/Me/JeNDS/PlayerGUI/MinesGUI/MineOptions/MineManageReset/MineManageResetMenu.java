package Me.JeNDS.PlayerGUI.MinesGUI.MineOptions.MineManageReset;

import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.PlayerGUI.PFGUI;
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
        addItems(Material.DIAMOND_PICKAXE,name+"Percentage Reset",null,22,this);
        addItems(Material.CLOCK,name+"Time Reset",null,31,this);
        addItems(Material.REDSTONE_BLOCK,name+"Back",null,45,this);

        setMenuAndInterface(title1 + mine.getName() + " Manage Reset Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
    }


    @Override
    protected boolean rightClickEvents(ItemStack itemStack, Player player) {
        return false;
    }

    @Override
    protected boolean leftClickEvents(ItemStack itemStack, Player player) {
        if(itemAndSlot.get(45).isSimilar(itemStack)){
            player.openInventory(lastMenu.getMenu());
        }
        if(itemAndSlot.get(31).isSimilar(itemStack)){
            MineManageResetTimeMenu mineManageResetTimeMenu = new MineManageResetTimeMenu(this,mine);
            player.openInventory(mineManageResetTimeMenu.getMenu());
        }
        if(itemAndSlot.get(22).isSimilar(itemStack)){
            MineManageResetPercentageMenu mineManageResetPercentageMenu = new MineManageResetPercentageMenu(this,mine);
            player.openInventory(mineManageResetPercentageMenu.getMenu());
        }
        return false;
    }
}