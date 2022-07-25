package JeNDS.JPlugins.CustomEnchants.Enchantments;

import JeNDS.JPlugins.HandBombs.HandBomb;
import JeNDS.JPlugins.PF;
import JeNDS.Plugins.PluginAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.PluginAPI.Enchants.EnchantmentWrapper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class Explosion extends CustomEnchant {
    public static Enchantment explosion = new EnchantmentWrapper("explosion", "Explosion", 1);


    public Explosion() {
        register(explosion, this, PF.getInstance());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void blockBrakeEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        double luck = 10;
        if (Math.random() * 100 <= luck) {
            if (PlayerHasEnchantedItem(explosion, player)) {
                HandBomb handBomb = new HandBomb(2, 2, null, player);
                handBomb.createExplosion(event.getBlock().getLocation());
            }
        }
    }


    @Override
    protected Enchantment getEnchantment() {
        return explosion;
    }
}
