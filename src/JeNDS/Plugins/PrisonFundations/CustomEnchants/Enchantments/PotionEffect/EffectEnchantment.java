package JeNDS.Plugins.PrisonFundations.CustomEnchants.Enchantments.PotionEffect;


import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.PrisonFundations.PF;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
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

    public EffectEnchantment(Enchantment enchantment, PotionEffectType potionType, int level) {
        this.customEnchant = enchantment;
        this.potionType = potionType;
        this.level = level;
        register(enchantment, this, PF.getInstance());
    }

    public EffectEnchantment(Enchantment enchantment, int level) {
        this.customEnchant = enchantment;
        this.potionType = null;
        this.level = level;
        register(enchantment, this, PF.getInstance());
    }
    @EventHandler
    protected void itemHeldCheck(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if (itemStack != null) {
            if (ItemHasEnchantment(customEnchant, itemStack)) {
                if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }
                if (potionType != null) {
                    PotionEffect potionEffect = new PotionEffect(potionType, Integer.MAX_VALUE, level - 1, false, false, false);
                    player.addPotionEffect(potionEffect);
                }
            }
        }
        if (player.getInventory().getItem(event.getPreviousSlot()) != null) {
            if (ItemHasEnchantment(customEnchant, Objects.requireNonNull(player.getInventory().getItem(event.getPreviousSlot())))) {
                if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                }
                if (potionType != null) {
                    player.removePotionEffect(potionType);
                }
            }

        }

    }

    @EventHandler
    protected void dropChecker(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemDrop().getItemStack();
        if (ItemHasEnchantment(customEnchant, itemStack)) {
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.setAllowFlight(false);
                player.setFlying(false);
            }
            if (potionType != null) {
                player.removePotionEffect(potionType);
            }
        }
    }

    @EventHandler
    protected void pickUpCheck(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(PF.getInstance(), new Runnable() {
                @Override
                public void run() {
                    ItemStack itemStack = event.getItem().getItemStack();
                    if (ItemHasEnchantment(customEnchant, itemStack)) {
                        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                            player.setAllowFlight(true);
                            player.setFlying(true);
                        }
                        if (potionType != null) {
                            PotionEffect potionEffect = new PotionEffect(potionType, Integer.MAX_VALUE, level - 1, false, false, false);
                            player.addPotionEffect(potionEffect);
                        }
                    }
                }
            }, 1L);

        }
    }

    @EventHandler
    protected void swapCheck(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getMainHandItem();
        assert itemStack != null;
        if (ItemHasEnchantment(customEnchant, itemStack)) {
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.setAllowFlight(true);
                player.setFlying(true);
            }
            if (potionType != null) {
                PotionEffect potionEffect = new PotionEffect(potionType, Integer.MAX_VALUE, level - 1, false, false, false);
                player.addPotionEffect(potionEffect);
            }
        }
    }

    @EventHandler
    protected void inventoryCheck(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            int slot = player.getInventory().getHeldItemSlot();
            ItemStack itemStack = event.getCurrentItem();
            ItemStack itemStack2 = event.getCursor();
            assert itemStack != null;
            if (ItemHasEnchantment(customEnchant, itemStack)) {
                if (ItemHasEnchantment(customEnchant,player.getInventory().getItemInMainHand())) {
                    if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                    }
                    if (potionType != null) {
                        player.removePotionEffect(potionType);
                    }
                }
                return;

            }
            if(slot == event.getSlot()){
                assert itemStack2 != null;
                if(ItemHasEnchantment(customEnchant,itemStack2)){
                    if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                    }
                    if (potionType != null) {
                        PotionEffect potionEffect = new PotionEffect(potionType, Integer.MAX_VALUE, level - 1, false, false, false);
                        player.addPotionEffect(potionEffect);
                    }
                }
            }
        }
    }
    @EventHandler
    protected void inventoryCheck2(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            ItemStack itemStack = event.getCursor();
            assert itemStack != null;
            if(ItemHasEnchantment(customEnchant,itemStack)) event.setCancelled(true);
        }
    }


    @Override
    protected Enchantment getEnchantment() {
        return customEnchant;
    }
}
