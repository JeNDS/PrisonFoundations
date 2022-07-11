package Me.JeNDS.PlayerGUI.MinesGUI.MineOptions;

import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.PlayerGUI.PFGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static Me.JeNDS.Static.Presets.DefaultColor;
import static Me.JeNDS.Static.Presets.StandOutColor;

public class MineSignsMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineSignsMenu(PFGUI lastMenu, Mine mine){
        this.lastMenu = lastMenu;
        this.mine = mine;
        addItems(Material.OAK_SIGN, DefaultColor +"Mine Percentage",null,11,this);
        addItems(Material.OAK_SIGN, DefaultColor +"Time Until Reset",null,15,this);
        addItems(Material.REDSTONE_BLOCK, DefaultColor +"Back",null,45,this);
        setMenuAndInterface(StandOutColor + mine.getName() + " Signs Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
            giveSign(player, DefaultColor +mine.getName() + " Mine Percentage");
            return true;
        }
        if(itemAndSlot.get(15).isSimilar(itemStack)){
            giveSign(player, DefaultColor +mine.getName() + " Time Until Reset");
            return true;
        }
        return false;
    }

    private void giveSign(Player player, String signName){
        ItemStack itemStack = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(DefaultColor + signName);
        itemStack.setItemMeta(meta);
        if(!player.getInventory().contains(itemStack)) {
            player.getInventory().addItem(itemStack);
        }
    }
}
