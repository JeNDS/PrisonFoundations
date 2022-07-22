package JeNDS.JPlugins.Other.Events;

import JeNDS.JPlugins.Other.Files.Config;
import JeNDS.JPlugins.Static.Catch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OtherEvents implements Listener {


    @EventHandler
    public void itemBrakeDisable(PlayerItemDamageEvent event) {
        if (Config.UnbreakableItems) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void clearCatchOnLeave(PlayerQuitEvent event) {
        Catch.inventoryFullTimeWait.remove(event.getPlayer());
        Catch.lastPlayerMenu.remove(event.getPlayer());
        Catch.nextPlayerMenu.remove(event.getPlayer());
    }

}
