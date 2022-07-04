package Me.JeNDS.PlayerGUI.MinesGUI.MineOptions;

import Me.JeNDS.Files.MineFile;
import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.PlayerGUI.MinesGUI.MineOptions.MineManageReset.MineManageResetMenu;
import Me.JeNDS.PlayerGUI.MinesGUI.MineOptions.MineBlockTypes.MineResetTypeMenu;
import Me.JeNDS.PlayerGUI.PFGUI;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
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
        addItems(Material.BARRIER,name+"Reset",null,11,this);
        addItems(Material.REDSTONE_BLOCK,name+"Delete",null,15,this);
        addItems(Material.OAK_SIGN,name+"Signs",null,22,this);
        addItems(Material.DIAMOND_ORE,name+"Block Types",null,38,this);
        addItems(Material.CLOCK,name+"Manage Reset",null,42,this);
        addItems(Material.REDSTONE_BLOCK,name+"Back",null,45,this);
        setMenuAndInterface(title1 + mine.getName() + " Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
        if(itemAndSlot.get(11).isSimilar(itemStack)){
            MineResetMenu mineResetMenu = new MineResetMenu(this,mine);
            player.openInventory(mineResetMenu.getMenu());
        }
        if(itemAndSlot.get(22).isSimilar(itemStack)){
            MineSignsMenu mineSignsMenu = new MineSignsMenu(this,mine);
            player.openInventory(mineSignsMenu.getMenu());
        }
        if(itemAndSlot.get(38).isSimilar(itemStack)){
            MineResetTypeMenu mineResetTypeMenu = new MineResetTypeMenu(this,mine);
            player.openInventory(mineResetTypeMenu.getMenu());
        }
        if(itemAndSlot.get(42).isSimilar(itemStack)){
            MineManageResetMenu mineManageResetMenu = new MineManageResetMenu(this,mine);
            player.openInventory(mineManageResetMenu.getMenu());
        }
        if(itemAndSlot.get(15).isSimilar(itemStack)){
            if (Catch.RunningMines.contains(mine)) {
                Bukkit.getScheduler().cancelTask(mine.getTimeResetTaskID());
                Bukkit.getScheduler().cancelTask(mine.getPercentageResetTaskID());
                Catch.RunningMines.remove(mine);
            }
            MineFile mineFile = new MineFile(mine.getName());
            mineFile.deletConfig();
            player.closeInventory();
            player.sendMessage(Presets.DefaultColor + "You have Deleted Mine " + Presets.StandOutColor + mine.getName());
        }
        return false;
    }
}
