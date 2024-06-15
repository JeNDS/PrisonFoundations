package JeNDS.Plugins.PrisonFundations.CustomEnchants;


import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.PrisonFundations.Managers.EventManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant.AddCustomEnchantment;

public class EnchantEvents extends EventManager {


    @EventHandler
    public void addEnchantment(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (event.getWhoClicked().getGameMode().equals(GameMode.SURVIVAL)) {
                if (event.isRightClick() || event.isLeftClick()) {
                    if (event.getCursor() != null && event.getCurrentItem() != null) {
                        Enchantment enchantment = CustomEnchant.GetItemEnchantment(event.getCursor());
                        if (enchantment != null) {
                            if (event.getCursor().getType().equals(Material.ENCHANTED_BOOK))
                                if (enchantment.canEnchantItem(event.getCurrentItem())) {
                                    int level = 0;
                                    if(event.getCurrentItem().getItemMeta().hasEnchant(enchantment)) level = event.getCurrentItem().getItemMeta().getEnchantLevel(enchantment);
                                    if(level<enchantment.getMaxLevel()) {
                                        event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
                                        event.setCurrentItem(AddCustomEnchantment(enchantment, event.getCurrentItem()));
                                        event.setCancelled(true);
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}
