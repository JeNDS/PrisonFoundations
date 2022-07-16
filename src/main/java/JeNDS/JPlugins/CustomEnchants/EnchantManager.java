package JeNDS.JPlugins.CustomEnchants;

import JeNDS.Plugins.PluginAPI.Enchants.EnchantmentWrapper;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class EnchantManager {

    public static void LoadEnchantments() {
        new AutoBlock();
        new AutoSmelt();
        new AutoSell();
        LoadPotionEnchants();
    }

    private static void LoadPotionEnchants(){
        new EffectEnchantment(new EnchantmentWrapper("night_vision", "Night Vision", 1), PotionEffectType.NIGHT_VISION,1);
        new EffectEnchantment(new EnchantmentWrapper("haste", "Haste", 1), PotionEffectType.FAST_DIGGING,3);
        new EffectEnchantment(new EnchantmentWrapper("speed", "Speed", 1), PotionEffectType.SPEED,3);
        new EffectEnchantment(new EnchantmentWrapper("super_jump", "Super Jump", 1), PotionEffectType.JUMP,3);
        new EffectEnchantment(new EnchantmentWrapper("flight", "Flight", 1), 0);
    }

    // TODO: 7/10/2022 Flight,Explossions,supper jump,
    public static ItemStack AddCustomEnchantment(Enchantment enchantment, ItemStack itemStack) {
        if (!itemStack.getEnchantments().containsKey(enchantment)) {
            itemStack.addUnsafeEnchantment(enchantment, 1);
            ItemMeta meta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            if (1 == 1) {
                lore.add(ChatColor.GRAY + "" + enchantment.getName());
            } else {
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
