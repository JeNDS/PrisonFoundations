package JeNDS.JPlugins.PlayerGUI.MinesGUI.MineOptions;

import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Objects.MineObjects.Mine;
import JeNDS.JPlugins.PlayerGUI.PFGUI;
import JeNDS.JPlugins.Static.Presets;
import JeNDS.JPlugins.PlayerGUI.MinesGUI.MineOptions.MineManageReset.MineManageResetMenu;
import JeNDS.JPlugins.PlayerGUI.MinesGUI.MineOptions.MineBlockTypes.MineResetTypeMenu;
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
        addItems(Material.BARRIER, Presets.DefaultColor +"Reset",null,11,this);
        addItems(Material.REDSTONE_BLOCK, Presets.DefaultColor +"Delete",null,15,this);
        addItems(Material.OAK_SIGN, Presets.DefaultColor +"Signs&Holograms",null,22,this);
        addItems(Material.DIAMOND_ORE, Presets.DefaultColor +"Block Types",null,38,this);
        addItems(Material.CLOCK, Presets.DefaultColor +"Manage Reset",null,42,this);
        addItems(Material.REDSTONE_BLOCK, Presets.DefaultColor +"Back",null,45,this);
        setMenuAndInterface(Presets.StandOutColor + mine.getName() + " Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
            player.sendMessage(Presets.DefaultColor + "You have Deleted Mine " + Presets.StandOutColor + mine.getName());
            return true;
        }
        return false;
    }
}