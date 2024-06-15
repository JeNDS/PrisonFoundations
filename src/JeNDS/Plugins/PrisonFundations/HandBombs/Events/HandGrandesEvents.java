package JeNDS.Plugins.PrisonFundations.HandBombs.Events;


import JeNDS.Plugins.PrisonFundations.HandBombs.Files.HandGrenadeConfig;
import JeNDS.Plugins.PrisonFundations.HandBombs.HandBomb;
import JeNDS.Plugins.PrisonFundations.Managers.EventManager;
import JeNDS.Plugins.PrisonFundations.Other.Files.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HandGrandesEvents extends EventManager {


    @EventHandler
    public void addEnchantment(PlayerInteractEvent event) {
        if (Config.WorldBlackList.contains(event.getPlayer().getWorld())) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
            if (mainItem.getType() != Material.AIR) {
                HandBomb handBomb = HandGrenadeConfig.GetHandBombFromConfig(event.getPlayer(), mainItem);
                if (handBomb != null) {
                    handBomb.launchGrande();
                }
            }
        }
    }
}
