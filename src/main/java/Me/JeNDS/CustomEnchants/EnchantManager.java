package Me.JeNDS.CustomEnchants;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EnchantManager {

    public static void LoadEnchantments(){
        new AutoBlock();
        new AutoSmelt();
    }
    public static ItemStack AddCustomEnchantment(Enchantment enchantment, ItemStack itemStack) {
        if(!itemStack.getEnchantments().containsKey(enchantment)) {
            itemStack.addUnsafeEnchantment(enchantment, 1);
            ItemMeta meta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            if(1 == 1) {
                lore.add(ChatColor.GRAY + "" + enchantment.getName());
            }
            else {
                lore.add(ChatColor.GRAY + "" + enchantment.getName() + " " + 1);
            }
            assert meta != null;
            if (meta.hasLore()) {
                lore.addAll(meta.getLore());
            }
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }
}
