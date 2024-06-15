package JeNDS.Plugins.PrisonFundations.Other.Events;

import JeNDS.Plugins.PrisonFundations.Other.Files.Config;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

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

    @EventHandler
    public void loadTextures(PlayerJoinEvent event) {
        //event.getPlayer().setTexturePack("https://www.dropbox.com/s/kl94hxrtcpxrzk2/Custom%20Models.zip?dl=1");
        //event.getPlayer().setTexturePack(null);
    }

    @EventHandler
    public void checkTexture(PlayerResourcePackStatusEvent event) {
    }

}
