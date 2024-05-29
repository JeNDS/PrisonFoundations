package JeNDS.Plugins.PrisonFundations.CustomEnchants.Enchantments;

import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant;

import JeNDS.Plugins.JeNDSAPI.Enchants.EnchantmentWrapper;
import JeNDS.Plugins.PrisonFundations.PF;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class AutoBlock extends CustomEnchant {
    public static Enchantment autoBlock = new EnchantmentWrapper("auto_block", "Auto Block", 1);


    public AutoBlock() {
        register(autoBlock, this, PF.getInstance());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void blockBrakeEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (PlayerHasEnchantedItem(autoBlock, player)) {
            new JeNDS.Plugins.PrisonFundations.Mines.Utilities.AutoBlock(player);
        }
    }

    @Override
    protected Enchantment getEnchantment() {
        return autoBlock;
    }
}
