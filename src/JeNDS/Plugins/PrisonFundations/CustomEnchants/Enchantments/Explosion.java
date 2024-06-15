package JeNDS.Plugins.PrisonFundations.CustomEnchants.Enchantments;


import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.JeNDSAPI.Enchants.EnchantmentWrapper;
import JeNDS.Plugins.JeNDSAPI.Item.ItemType;
import JeNDS.Plugins.PrisonFundations.HandBombs.HandBomb;
import JeNDS.Plugins.PrisonFundations.Main;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class Explosion extends CustomEnchant {
    public static Enchantment explosion = new EnchantmentWrapper("explosion", ItemType.TOOL, "Explosion", 3);


    public Explosion() {
        register(explosion, this, Main.getInstance());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void blockBrakeEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.SURVIVAL))
            if (Mine.LocationInMine(event.getBlock().getLocation()))
                if (PlayerHasEnchantedItem(explosion, player)) {
                    int level = player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(explosion);
                    double luck = 10;
                    if (Math.random() * 100 <= luck) {
                        HandBomb handBomb = new HandBomb(2 * level, 2, null, player);
                        handBomb.createExplosion(event.getBlock().getLocation());
                    }

                }
    }


    @Override
    protected Enchantment getEnchantment() {
        return explosion;
    }
}
