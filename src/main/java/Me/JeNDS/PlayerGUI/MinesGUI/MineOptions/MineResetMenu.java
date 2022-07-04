package Me.JeNDS.PlayerGUI.MinesGUI.MineOptions;

import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.PlayerGUI.PFGUI;
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
        addItems(Material.BARRIER,name+"Slow Full Reset",null,11,this);
        addItems(Material.BARRIER,name+"Slow Partial Reset",null,15,this);
        addItems(Material.BARRIER,name+"Instant Full Reset",null,38,this);
        addItems(Material.BARRIER,name+"Instant Partial Reset",null,42,this);
        addItems(Material.REDSTONE_BLOCK,name+"Back",null,45,this);
        setMenuAndInterface(title1 + mine.getName() + " Reset Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
        if(itemAndSlot.get(11).isSimilar(itemStack)){mine.resetMine(true,true);}
        if(itemAndSlot.get(15).isSimilar(itemStack)){mine.resetMine(false,true);}
        if(itemAndSlot.get(38).isSimilar(itemStack)){mine.resetMine(true,false);}
        if(itemAndSlot.get(42).isSimilar(itemStack)){mine.resetMine(false,false);}
        return false;
    }
}
