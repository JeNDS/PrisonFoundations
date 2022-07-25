package JeNDS.JPlugins.HandBombs.Events;

import JeNDS.JPlugins.HandBombs.Files.HandGrenadeConfig;
import JeNDS.JPlugins.HandBombs.HandBomb;
import JeNDS.JPlugins.Managers.EventManager;
import JeNDS.Plugins.PluginAPI.Other.JDItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HandGrandesEvents extends EventManager {


    @EventHandler
    public void addEnchantment(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
            if(mainItem.getType()!= Material.AIR){
                HandBomb handBomb = HandGrenadeConfig.GetHandBombFromConfig(event.getPlayer(), mainItem);
                if(handBomb!=null){
                    handBomb.launchGrande();
                }
            }
        }
    }
}
