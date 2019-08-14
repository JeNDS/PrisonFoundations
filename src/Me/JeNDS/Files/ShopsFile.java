package Me.JeNDS.Files;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopsFile {
    private static HashMap<String, HashMap<Material, Double>> shopItems = new HashMap<>();
    private FileMaker shopFile;

    public ShopsFile() {
        FileMaker folder = new FileMaker("Shop Data", true);
        {
            folder.create();
        }
        shopFile = new FileMaker("Shops File", true, "Shop Data");
        {

            if (shopFile.getRawFile().length() == 0) {
                shopFile.create();
                loadDefault();
                loadShopFile();
            } else {
                loadShopFile();
            }
        }
    }

    public static HashMap<Material, Double> GetShopItems(String Shop) {
        if (shopItems.containsKey(Shop)) {
            return shopItems.get(Shop);
        }
        return new HashMap<>();
    }

    public static void SetShopItems(HashMap<Material, Double> items, String Shop) {
        shopItems.put(Shop, items);
    }

    public static String FindPlayerShop(Player player) {
        String foundShop = null;
        if (!shopItems.isEmpty()) {
            for (String shop : shopItems.keySet()) {
                if (player.hasPermission("PF.Shops." + shop) || player.hasPermission("PF.Ranks." + shop)) {
                    foundShop = shop;
                }
            }
        }
        return foundShop;
    }

    private void loadDefault() {
        ArrayList shopA = new ArrayList();
        ArrayList shopB = new ArrayList();
        ArrayList shopC = new ArrayList();
        shopA.add(Material.STONE.toString() + ",10");
        shopA.add(Material.COBBLESTONE.toString() + ",15");
        shopB.add(Material.COAL.toString() + ",5");
        shopB.add(Material.COAL_BLOCK.toString() + ",50");
        shopB.add(Material.COAL_ORE.toString() + ",20");
        shopC.add(Material.STONE.toString() + ",20");
        shopC.add(Material.COBBLESTONE.toString() + ",25");
        shopC.add(Material.IRON_INGOT.toString() + ",30");
        shopC.add(Material.IRON_BLOCK.toString() + ",200");
        shopC.add(Material.IRON_ORE.toString() + ",40");
        shopFile.getFile().set("Shops.A.Items", shopA);
        shopFile.getFile().set("Shops.B.Items", shopB);
        shopFile.getFile().set("Shops.B.Inherit", "A");
        shopFile.getFile().set("Shops.C.Items", shopC);
        shopFile.getFile().set("Shops.C.Inherit", "B");
        shopFile.save();
    }

    private void loadShopFile() {
        shopItems = new HashMap<>();
        if (shopFile.getFile().get("Shops") != null) {
            ConfigurationSection section = shopFile.getFile().getConfigurationSection("Shops");
            if (!section.getKeys(false).isEmpty()) {
                for (String shop : section.getKeys(false)) {
                    HashMap<Material, Double> tempHash = new HashMap<>();
                    if (shopFile.getFile().get("Shops." + shop + ".Inherit") != null) {
                        if (shopItems.containsKey(shopFile.getFile().getString("Shops." + shop + ".Inherit"))) {
                            tempHash = shopItems.get(shopFile.getFile().getString("Shops." + shop + ".Inherit"));
                        }
                    }
                    for (String raw : shopFile.getFile().getStringList("Shops." + shop + ".Items")) {
                        Material material = null;
                        Double price = null;
                        String[] rawSplit = raw.split(",");
                        for (String s : rawSplit) {
                            if (Material.getMaterial(s) != null) {
                                material = Material.getMaterial(s);
                            }
                            try {
                                price = Double.parseDouble(s);
                            } catch (NumberFormatException e) {

                            }
                        }
                        if (material != null && price != null) {
                            if (tempHash.containsKey(material)) {
                                if (tempHash.get(material) != price) {
                                    HashMap<Material, Double> temp = new HashMap<>();
                                    for (Material m : tempHash.keySet()) {
                                        if (m.equals(material)) {
                                            temp.put(m, price);
                                        } else {
                                            temp.put(m, tempHash.get(m));
                                        }
                                    }
                                    tempHash = temp;
                                }
                            } else {
                                tempHash.put(material, price);
                            }
                        }
                    }
                    shopItems.put(shop, tempHash);
                }

            }
        }
    }

    public static ArrayList<String> getShopList() {
        ArrayList<String> temp = new ArrayList<>();
        if(!shopItems.isEmpty()){
            for(String s: shopItems.keySet()){
                temp.add(s);
            }
        }
        return temp;
    }
}
