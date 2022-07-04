package Me.JeNDS.PlayerGUI.MinesGUI.MineOptions;

import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.PlayerGUI.PFGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MineSignsMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineSignsMenu(PFGUI lastMenu, Mine mine){
        this.lastMenu = lastMenu;
        this.mine = mine;
        addItems(Material.OAK_SIGN,name+"Mine Percentage",null,11,this);
        addItems(Material.OAK_SIGN,name+"Time Until Reset",null,15,this);
        addItems(Material.REDSTONE_BLOCK,name+"Back",null,45,this);
        setMenuAndInterface(title1 + mine.getName() + " Signs Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
            giveSign(player, name+mine.getName() + " Mine Percentage");
        }
        if(itemAndSlot.get(15).isSimilar(itemStack)){
            giveSign(player,name+mine.getName() + " Time Until Reset");
        }
        return false;
    }

    private void giveSign(Player player, String signName){
        ItemStack itemStack = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name+ signName);
        itemStack.setItemMeta(meta);
        if(!player.getInventory().contains(itemStack)) {
            player.getInventory().addItem(itemStack);
        }
    }
}
