package JeNDS.Plugins.PrisonFundations.Shops;

import JeNDS.Plugins.PrisonFundations.Other.Implementations.EconomyImport;
import JeNDS.Plugins.PrisonFundations.PlayerData.PFPlayer;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Shop {
    private final String shopName;
    private final String dispalyName;
    private final String inherits;
    private HashMap<Material, Double> shopItems = new HashMap<>();


    public Shop(String shopName, String dispalyName, String inherits, HashMap<Material, Double> shopItems) {
        this.shopName = shopName;
        this.dispalyName = dispalyName;
        this.inherits = inherits;
        this.shopItems = shopItems;
    }

    public static Shop GetPlayerShop(Player player) {
        Shop shop = null;
        for (Shop shop1 : Catch.Shops) {
            if (player.hasPermission("pf.shop." + shop1.shopName)) shop = shop1;
        }
        return shop;
    }

    public static Shop GetShopFrontString(String shopName) {
        for (Shop shop : Catch.Shops) {
            if (shop.shopName.equalsIgnoreCase(shopName)) {
                return shop;
            }
        }
        return null;
    }

    public static boolean SellPlayerItems(Player player) {
        boolean foundToSell = false;
        if (Shop.GetPlayerShop(player) != null) {
            Shop shop = Shop.GetPlayerShop(player);
            if (!shop.getShopItems().isEmpty()) {
                double sold = 0.0;
                int itemsSold = 0;
                for (Material material : shop.getShopItems().keySet()) {
                    for (ItemStack itemStack : player.getInventory().getContents()) {
                        if (itemStack != null) {
                            itemStack.getType();
                            if (itemStack.getType().equals(material)) {
                                double money = itemStack.getAmount() * shop.getShopItems().get(material);
                                PFPlayer.GetPFPlayer(player.getUniqueId());
                                money = PFPlayer.GetPFPlayer(player.getUniqueId()).getFinalMultiplier() * money;
                                foundToSell = true;
                                sold = sold + money;
                                itemsSold = itemsSold + itemStack.getAmount();
                                EconomyImport.Economy.depositPlayer(player, money);
                                player.getInventory().remove(itemStack);
                            }
                        }
                    }
                }
                if (foundToSell) {
                    player.sendMessage(Presets.SecondaryColor + itemsSold + Presets.MainColor + " items sold to shop " + Presets.SecondaryColor + shop.getShopDisplayName() + Presets.MainColor + " for $" + Presets.SecondaryColor + sold);
                }
                return true;
            }
        }
        return false;
    }

    public static void SellPlayerItems(Player player, Shop shop) {
        boolean foundToSell = false;
        if (!shop.getShopItems().isEmpty()) {
            double sold = 0.0;
            int itemsSold = 0;
            for (Material material : shop.getShopItems().keySet()) {
                for (ItemStack itemStack : player.getInventory().getContents()) {
                    if (itemStack != null) {
                        itemStack.getType();
                        if (itemStack.getType().equals(material)) {
                            double money = itemStack.getAmount() * shop.getShopItems().get(material);
                            PFPlayer.GetPFPlayer(player.getUniqueId());
                            money = PFPlayer.GetPFPlayer(player.getUniqueId()).getFinalMultiplier() * money;
                            foundToSell = true;
                            sold = sold + money;
                            itemsSold = itemsSold + itemStack.getAmount();
                            EconomyImport.Economy.depositPlayer(player, money);
                            player.getInventory().remove(itemStack);
                        }
                    }
                }
            }
            if (foundToSell) {
                player.sendMessage(Presets.SecondaryColor + itemsSold + Presets.MainColor + " Sold " + Presets.MainColor + " to shop " + Presets.SecondaryColor + shop.getShopDisplayName() + Presets.MainColor + " for $" + Presets.SecondaryColor + sold);
            }
        }

    }

    public String getShopName() {
        return shopName;
    }

    public String getShopDisplayName() {
        return dispalyName;
    }

    public String getInherits() {
        return inherits;
    }

    public HashMap<Material, Double> getShopItems() {
        return shopItems;
    }
}
