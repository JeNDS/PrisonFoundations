package Me.JeNDS.Utilities;

import Me.JeNDS.Objects.Shop;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.entity.Player;

public class AutoSell extends Utility{

    public AutoSell(Player player){
            Integer wait = 5;
            boolean foundToSell = false;
            if (Catch.autoSell.containsKey(player))
                if (Catch.autoSell.get(player)) {
                    Shop.SellPlayerItems(player,true);
                }
            if (!foundToSell) {
                if (Catch.inventoryFullTimeWait.containsKey(player)) {
                    wait = Catch.inventoryFullTimeWait.get(player);
                }
                if (wait == 5) {
                    player.sendMessage(Presets.DefaultColor + "Your Inventory is Full, time to sell your Items!");
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
