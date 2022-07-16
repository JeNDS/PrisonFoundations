package JeNDS.JPlugins.CustomEnchants;

import JeNDS.JPlugins.Main.PF;
import JeNDS.Plugins.PluginAPI.Enchants.CustomEnchant;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class EffectEnchantment extends CustomEnchant {
    private final Enchantment customEnchant;
    private final PotionEffectType potionType;
    private final int level;

    protected EffectEnchantment(Enchantment enchantment, PotionEffectType potionType, int level) {
        this.customEnchant = enchantment;
        this.potionType = potionType;
        this.level = level;
        register(enchantment, this, PF.getInstance());
    }

    protected EffectEnchantment(Enchantment enchantment, int level) {
        this.customEnchant = enchantment;
        this.potionType = null;
        this.level = level;
        register(enchantment, this, PF.getInstance());
    }

    protected void potionUpdater(Player player, ItemStack itemStack) {
        if (itemStack != null) {
            if (ItemHasEnchantment(customEnchant, itemStack)) {
                PotionEffect potionEffect = new PotionEffect(potionType, Integer.MAX_VALUE, level - 1, false, false, false);
                player.addPotionEffect(potionEffect);
                return;
            }
        }
        player.removePotionEffect(potionType);
    }

    protected void gameModeUpdate(Player player, ItemStack itemStack) {
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            if (itemStack != null) {
                if (ItemHasEnchantment(customEnchant, itemStack)) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    return;
                }
            }
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

    private void switcher(Player player, ItemStack itemStack) {
        if (potionType != null) {
            potionUpdater(player, itemStack);
        } else {
            gameModeUpdate(player, itemStack);
        }
    }

    @EventHandler
    protected void itemHeldCheck(PlayerItemHeldEvent event) {
        switcher(event.getPlayer(), event.getPlayer().getInventory().getItem(event.getNewSlot()));
    }

    @EventHandler
    protected void dropChecker(PlayerDropItemEvent event) {
        switcher(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand());
    }

    @EventHandler
    protected void pickUpCheck(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            switcher(player, event.getItem().getItemStack());
        }
    }

    @EventHandler
    protected void swapCheck(PlayerSwapHandItemsEvent event) {
        switcher(event.getPlayer(), event.getMainHandItem());
    }

    @EventHandler
    protected void inventoryCheck(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (event.getCursor() != null) {
                if (ItemHasEnchantment(customEnchant, event.getCursor())) {
                    if (player.getInventory().getHeldItemSlot() == event.getSlot()) {
                        switcher(player, event.getCursor());
                    } else {
                        switcher(player, player.getInventory().getItemInMainHand());
                    }
                } else {
                    if (ItemHasEnchantment(customEnchant, player.getInventory().getItemInMainHand())) {
                        if (event.getCursor() == null) {
                            switcher(player, null);
                        } else {
                            if (ItemHasEnchantment(customEnchant, Objects.requireNonNull(event.getCurrentItem()))) {
                                switcher(player, null);
                            }
                        }

                    }
                }
            }

        }
    }


    @Override
    protected Enchantment getEnchantment() {
        return customEnchant;
    }
}
