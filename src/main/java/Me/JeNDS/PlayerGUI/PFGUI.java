package Me.JeNDS.PlayerGUI;

import JeNDS.Plugins.PluginAPI.GUI.GUInterface;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class PFGUI extends GUInterface {

    protected boolean hasSameName(ItemStack itemStack, String name){
        if(itemStack!=null)
            if(itemStack.hasItemMeta())
                if(itemStack.getItemMeta().hasDisplayName()){
                    return itemStack.getItemMeta().getDisplayName().contains(name);
                }
        return false;
    }
    protected ItemStack fillItem(){
        ItemStack itemStack = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(" ");
        meta.addEnchant(Enchantment.CHANNELING,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    protected void addItems(Material material, String name, List<String> lore, Integer slot, PFGUI pfgui) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta backMeta = itemStack.getItemMeta();
        backMeta.setDisplayName(name);
        backMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        backMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        backMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        if (lore != null) {
            backMeta.setLore(lore);
        }
        itemStack.setItemMeta(backMeta);
        pfgui.addItem(itemStack, slot);
    }
}
