package JeNDS.Plugins.PrisonFundations.CustomEnchants.Enchantments;


import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.JeNDSAPI.Enchants.EnchantmentWrapper;
import JeNDS.Plugins.PrisonFundations.PF;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class AutoSell extends CustomEnchant {
    private final Enchantment autoSell = new EnchantmentWrapper("auto_sell", "Auto Sell", 1);


    public AutoSell() {
        register(autoSell, this, PF.getInstance());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void blockBrakeEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (PlayerHasEnchantedItem(autoSell, player)) {
            new JeNDS.Plugins.PrisonFundations.Mines.Utilities.AutoSell(player);
        }
    }

    @Override
    protected Enchantment getEnchantment() {
        return autoSell;
    }
}
