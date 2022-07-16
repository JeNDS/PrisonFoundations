package JeNDS.JPlugins.PlayerGUI.MinesGUI.MineOptions;

import JeNDS.JPlugins.PlayerGUI.PFGUI;
import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Objects.MineObjects.Mine;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static JeNDS.JPlugins.Static.Presets.DefaultColor;
import static JeNDS.JPlugins.Static.Presets.StandOutColor;

public class MineSignsAndHologramsMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineSignsAndHologramsMenu(PFGUI lastMenu, Mine mine){
        this.lastMenu = lastMenu;
        this.mine = mine;
        addItems(Material.OAK_SIGN, DefaultColor +"Mine Percentage",null,11,this);
        addItems(Material.OAK_SIGN, DefaultColor +"Time Until Reset",null,15,this);
        addItems(Material.ARMOR_STAND, DefaultColor +"Mine Percentage",null,29,this);
        addItems(Material.ARMOR_STAND, DefaultColor +"Time Until Reset",null,33,this);
        addItems(Material.REDSTONE_BLOCK, DefaultColor +"Back",null,45,this);
        setMenuAndInterface(StandOutColor + mine.getName() + " Signs&Holograms Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
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
            giveItem(player, DefaultColor +mine.getName() + " Mine Percentage",Material.OAK_SIGN);
            return true;
        }
        if(itemAndSlot.get(15).isSimilar(itemStack)){
            giveItem(player, DefaultColor +mine.getName() + " Time Until Reset",Material.OAK_SIGN);
            return true;
        }
        if(itemAndSlot.get(29).isSimilar(itemStack)){
            giveItem(player, DefaultColor +mine.getName() + " Mine Percentage",Material.ARMOR_STAND);
            return true;
        }
        if(itemAndSlot.get(33).isSimilar(itemStack)){
            giveItem(player, DefaultColor +mine.getName() + " Time Until Reset",Material.ARMOR_STAND);
            return true;
        }
        return false;
    }

    private void giveItem(Player player, String name,Material material){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(DefaultColor + name);
        itemStack.setItemMeta(meta);
        if(!player.getInventory().contains(itemStack)) {
            player.getInventory().addItem(itemStack);
        }
    }
}
