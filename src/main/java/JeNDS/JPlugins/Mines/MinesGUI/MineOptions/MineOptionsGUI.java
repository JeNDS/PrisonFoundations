package JeNDS.JPlugins.Mines.MinesGUI.MineOptions;

import JeNDS.JPlugins.PF;
import JeNDS.JPlugins.Mines.MineObjects.Mine;
import JeNDS.JPlugins.Managers.PFGUI;
import JeNDS.JPlugins.Static.Presets;
import JeNDS.JPlugins.Mines.MinesGUI.MineOptions.MineManageReset.MineManageResetMenu;
import JeNDS.JPlugins.Mines.MinesGUI.MineOptions.MineBlockTypes.MineResetTypeMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MineOptionsGUI extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineOptionsGUI(PFGUI lastMenu,Mine mine){
        this.lastMenu = lastMenu;
        this.mine = mine;
        addItems(Material.BARRIER, Presets.MainColor +"Reset",null,11,this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor +"Delete",null,15,this);
        addItems(Material.OAK_SIGN, Presets.MainColor +"Signs&Holograms",null,22,this);
        addItems(Material.DIAMOND_ORE, Presets.MainColor +"Block Types",null,38,this);
        addItems(Material.CLOCK, Presets.MainColor +"Manage Reset",null,42,this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor +"Back",null,45,this);
        setMenuAndInterface(Presets.SecondaryColor + mine.getName() + " Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
        if(itemAndSlot.get(11).isSimilar(itemStack)){
            MineResetMenu mineResetMenu = new MineResetMenu(this,mine);
            player.openInventory(mineResetMenu.getMenu());
            return true;
        }
        if(itemAndSlot.get(22).isSimilar(itemStack)){
            MineSignsAndHologramsMenu mineSignsMenu = new MineSignsAndHologramsMenu(this,mine);
            player.openInventory(mineSignsMenu.getMenu());
            return true;
        }
        if(itemAndSlot.get(38).isSimilar(itemStack)){
            MineResetTypeMenu mineResetTypeMenu = new MineResetTypeMenu(this,mine);
            player.openInventory(mineResetTypeMenu.getMenu());
            return true;
        }
        if(itemAndSlot.get(42).isSimilar(itemStack)){
            MineManageResetMenu mineManageResetMenu = new MineManageResetMenu(this,mine);
            player.openInventory(mineManageResetMenu.getMenu());
            return true;
        }
        if(itemAndSlot.get(15).isSimilar(itemStack)){
            mine.deleteMine();
            player.closeInventory();
            player.sendMessage(Presets.MainColor + "You have Deleted Mine " + Presets.SecondaryColor + mine.getName());
            return true;
        }
        return false;
    }
}
