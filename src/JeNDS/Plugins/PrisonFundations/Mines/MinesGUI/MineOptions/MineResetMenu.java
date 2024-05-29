package JeNDS.Plugins.PrisonFundations.Mines.MinesGUI.MineOptions;

import JeNDS.Plugins.PrisonFundations.Managers.PFGUI;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.PF;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MineResetMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineResetMenu(PFGUI lastMenu, Mine mine){
        this.lastMenu = lastMenu;
        this.mine = mine;
        addItems(Material.BARRIER, Presets.MainColor +"Slow Full Reset",null,11,this);
        addItems(Material.BARRIER, Presets.MainColor +"Slow Partial Reset",null,15,this);
        addItems(Material.BARRIER, Presets.MainColor +"Instant Full Reset",null,38,this);
        addItems(Material.BARRIER, Presets.MainColor +"Instant Partial Reset",null,42,this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor +"Back",null,45,this);
        setMenuAndInterface(Presets.SecondaryColor + mine.getName() + " Reset Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
        if(itemAndSlot.get(11).isSimilar(itemStack)){mine.resetMine(true,true);return true;}
        if(itemAndSlot.get(15).isSimilar(itemStack)){mine.resetMine(false,true);return true;}
        if(itemAndSlot.get(38).isSimilar(itemStack)){mine.resetMine(true,false);return true;}
        if(itemAndSlot.get(42).isSimilar(itemStack)){mine.resetMine(false,false);return true;}
        return false;
    }
}
