package Me.JeNDS.Objects;

import Me.JeNDS.Main.PF;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Shop {
    private String shopName;
    private String inherits;
    private HashMap<Material, Double> shopItems = new HashMap<>();

    public Shop(String shopName, String inherits, HashMap<Material, Double> shopItems) {
        this.shopName = shopName;
        this.inherits = inherits;
        this.shopItems = shopItems;
    }

    public static Shop GetPlayerShop(Player player) {
        Shop shop = null;
        for (Shop shop1 : Catch.Shops) {
            if (player.hasPermission("PF.Shops." + shop1.shopName)) shop = shop1;
        }
        return shop;
    }

    public static boolean SellPlayerItems(Player player, boolean self) {
        boolean foundToSell = false;
        if (Shop.GetPlayerShop(player) != null) {
            Shop shop = Shop.GetPlayerShop(player);
            if (!shop.getShopItems().isEmpty()) {
                double sold = 0.0;
                Integer itemsSold = 0;
                for (Material material : shop.getShopItems().keySet()) {
                    for (ItemStack itemStack : player.getInventory().getContents()) {
                        if (itemStack != null) {
                            itemStack.getType();
                            if (itemStack.getType().equals(material)) {
                                double money = itemStack.getAmount() * shop.getShopItems().get(material);
                                foundToSell = true;
                                sold = sold + money;
                                itemsSold = itemsSold + itemStack.getAmount();
                                PF.economy.depositPlayer(player, money);
                                player.getInventory().remove(itemStack);
                            }
                        }
                    }
                }
                if (foundToSell) {
                    if (self) {
                        player.sendMessage(Presets.DefaultColor + "You Sold " + Presets.StandOutColor + itemsSold + Presets.DefaultColor + " items to shop " + Presets.StandOutColor + shop.getShopName() + Presets.DefaultColor + " for " + Presets.StandOutColor + sold);
                    } else {
                        player.sendMessage(Presets.DefaultColor + "Someone Sold " + Presets.StandOutColor + itemsSold + Presets.DefaultColor + " items to shop on your behalf " + Presets.StandOutColor + shop.getShopName() + Presets.DefaultColor + " for " + Presets.StandOutColor + sold);

                    }
                }
                return true;
            }
        }
        return false;
    }

    public String getShopName() {
        return shopName;
    }

    public String getInherits() {
        return inherits;
    }

    public HashMap<Material, Double> getShopItems() {
        return shopItems;
    }
}
