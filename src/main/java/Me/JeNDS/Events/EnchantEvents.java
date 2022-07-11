package Me.JeNDS.Events;

import JeNDS.Plugins.PluginAPI.Enchants.CustomEnchant;
import Me.JeNDS.CustomEnchants.EnchantManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantEvents extends EventManager {


    @EventHandler
    public void addEnchantment(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            //Player needs to be in survival for this to work!!!!
            if (event.getWhoClicked().getGameMode().equals(GameMode.SURVIVAL)) {
                if (event.isRightClick() || event.isLeftClick()) {
                    if (event.getCursor() != null && event.getCurrentItem() != null) {
                        Enchantment enchantment = CustomEnchant.GetItemEnchantment(event.getCursor());
                        if (enchantment != null) {
                            if (event.getCurrentItem().getType().name().contains("PICKAXE")) {
                                if (CustomEnchant.ItemCanEnchant(event.getCurrentItem(), enchantment)) {
                                    event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
                                    event.setCurrentItem(EnchantManager.AddCustomEnchantment(enchantment, event.getCurrentItem()));
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
