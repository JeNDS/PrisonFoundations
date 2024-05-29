package JeNDS.Plugins.PrisonFundations.CustomEnchants.Enchantments;


import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.JeNDSAPI.Enchants.EnchantmentWrapper;
import JeNDS.Plugins.PrisonFundations.PF;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class AutoSmelt extends CustomEnchant {
    public static Enchantment autoSmelt = new EnchantmentWrapper("auto_smelt", "Auto Smelt", 1);


    public AutoSmelt() {
        register(autoSmelt, this, PF.getInstance());
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void blockBrakeEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (PlayerHasEnchantedItem(autoSmelt, player)) {
            new JeNDS.Plugins.PrisonFundations.Mines.Utilities.AutoSmelt(player);

        }
    }
    @Override
    protected Enchantment getEnchantment() {
        return autoSmelt;
    }
}
