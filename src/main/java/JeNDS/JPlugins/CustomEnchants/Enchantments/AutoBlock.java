package JeNDS.JPlugins.CustomEnchants.Enchantments;

import JeNDS.JPlugins.PF;
import JeNDS.Plugins.PluginAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.PluginAPI.Enchants.EnchantmentWrapper;
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
            new JeNDS.JPlugins.Mines.Utilities.AutoBlock(player);
        }
    }

    @Override
    protected Enchantment getEnchantment() {
        return autoBlock;
    }
}
