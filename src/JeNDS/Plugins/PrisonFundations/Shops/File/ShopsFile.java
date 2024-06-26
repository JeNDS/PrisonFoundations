package JeNDS.Plugins.PrisonFundations.Shops.File;


import JeNDS.Plugins.JeNDSAPI.Files.YMLFile;
import JeNDS.Plugins.JeNDSAPI.JDAPI;
import JeNDS.Plugins.PrisonFundations.Main;
import JeNDS.Plugins.PrisonFundations.Shops.Shop;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopsFile {
    public static YMLFile shopFile;


    public static void LoadShopFile() {
        shopFile = JDAPI.getFileManipulation.copyFile("Shops.yml", Main.getPlugin(Main.class));
        ConfigurationSection section = shopFile.getFileConfiguration().getConfigurationSection("");
        assert section != null;
        if (!section.getKeys(false).isEmpty()) {
            for (String shop : section.getKeys(false)) {
                String inheritance = null;
                String displayName = shop;
                if (shopFile.getFileConfiguration().get(shop + ".Display Name") != null)
                    displayName = shopFile.getFileConfiguration().getString(shop + ".Display Name");
                if (shopFile.getFileConfiguration().get(shop + ".Inherit") != null)
                    inheritance = shopFile.getFileConfiguration().getString(shop + ".Inherit");
                displayName = ChatColor.translateAlternateColorCodes('&', displayName);
                Shop shop1 = new Shop(shop, displayName, inheritance, combineShopItems(shop));
                if (!Catch.Shops.contains(shop1)) {
                    Catch.Shops.add(shop1);
                }
            }

        }

    }

    public static void ReloadShops() {
        Catch.Shops = new ArrayList<>();
        LoadShopFile();
    }

    private static HashMap<Material, Double> combineShopItems(String shop) {
        HashMap<Material, Double> combinedItems = new HashMap<>(getShopItems(shop));
        boolean Inheritance = true;
        String nextShop = shop;
        while (Inheritance) {
            if (shopFile.getFileConfiguration().get(nextShop + ".Inherit") != null) {
                if (shopFile.getFileConfiguration().get(shopFile.getFileConfiguration().getString(nextShop + ".Inherit") + ".Items") != null) {
                    HashMap<Material, Double> tempItems = getShopItems(shopFile.getFileConfiguration().getString(nextShop + ".Inherit"));
                    for (Material material : tempItems.keySet()) {
                        if (!combinedItems.containsKey(material)) {
                            combinedItems.put(material, tempItems.get(material));
                        }
                    }
                    nextShop = shopFile.getFileConfiguration().getString(nextShop + ".Inherit");
                }
            } else {
                Inheritance = false;
            }
        }
        return combinedItems;
    }

    private static HashMap<Material, Double> getShopItems(String shop) {
        HashMap<Material, Double> combinedItems = new HashMap<>();
        if (shopFile.getFileConfiguration().get(shop + ".Items") != null) {
            ConfigurationSection oldItems = shopFile.getFileConfiguration().getConfigurationSection(shop + ".Items");
            for (String s : oldItems.getKeys(false)) {
                if (Material.getMaterial(s) != null) {
                    if (!combinedItems.containsKey(s)) {
                        combinedItems.put(Material.getMaterial(s), shopFile.getFileConfiguration().getDouble(shop + ".Items." + s));
                    }
                }
            }
        }
        return combinedItems;
    }

}
