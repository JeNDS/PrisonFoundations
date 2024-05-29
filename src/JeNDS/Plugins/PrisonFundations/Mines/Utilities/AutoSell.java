package JeNDS.Plugins.PrisonFundations.Mines.Utilities;

import JeNDS.Plugins.PrisonFundations.Shops.Shop;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.entity.Player;

public class AutoSell extends Utility {

    public AutoSell(Player player) {
        Integer wait = 5;
        if (!Utility.SpaceInInventory(player)) {
            if (!Shop.SellPlayerItems(player, true)) {

                if (Catch.inventoryFullTimeWait.containsKey(player)) {
                    wait = Catch.inventoryFullTimeWait.get(player);
                }
                if (wait == 5) {
                    player.sendMessage(Presets.MainColor + "Your Inventory is Full, time to sell your Items!");
                }
                wait--;
                if (wait == 0) {
                    Catch.inventoryFullTimeWait.put(player, 5);
                } else {
                    Catch.inventoryFullTimeWait.put(player, wait);
                }
            }
        }

    }
}
