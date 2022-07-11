package Me.JeNDS.Files;

import JeNDS.Plugins.PluginAPI.Files.YMLFile;
import JeNDS.Plugins.PluginAPI.JDAPI;
import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.Shop;
import Me.JeNDS.Static.Catch;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class ShopsFile {
    public static YMLFile shopFile = JDAPI.getFileManipulation.copyFile("Shops.yml", PF.getPlugin(PF.class));


    public static void LoadShopFile() {
        ConfigurationSection section = shopFile.getFileConfiguration().getConfigurationSection("");
        if (!section.getKeys(false).isEmpty()) {
            for (String shop : section.getKeys(false)) {
                String inheritance = null;
                if (shopFile.getFileConfiguration().get(shop + ".Inherit") != null) inheritance = shopFile.getFileConfiguration().getString(shop + ".Inherit");
                Shop shop1 = new Shop(shop,inheritance,combineShopItems(shop));
                if(!Catch.Shops.contains(shop1)){
                    Catch.Shops.add(shop1);
                }
            }

        }

    }

    private static HashMap<Material, Double> combineShopItems(String shop) {
        HashMap<Material, Double> combinedItems = new HashMap<>(getShopItems(shop));
        boolean Inheritance = true;
        String nextShop = shop;
        while (Inheritance) {
            if (shopFile.getFileConfiguration().get(nextShop + ".Inherit") != null) {
                if (shopFile.getFileConfiguration().get(shopFile.getFileConfiguration().getString(nextShop + ".Inherit")+".Items") != null) {
                    HashMap<Material, Double> tempItems = getShopItems(shopFile.getFileConfiguration().getString(nextShop + ".Inherit"));
                    for(Material material : tempItems.keySet()){
                        if(!combinedItems.containsKey(material)){
                            combinedItems.put(material, tempItems.get(material));
                        }
                    }
                    nextShop = shopFile.getFileConfiguration().getString(nextShop + ".Inherit");
                }
            }
            else {
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
