package JeNDS.Plugins.PrisonFundations.Shops;

import JeNDS.Plugins.PrisonFundations.Main;
import JeNDS.Plugins.PrisonFundations.Managers.PFGUI;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PricesGUI extends PFGUI {

    public PricesGUI(Shop shop) {
        createInventory(shop);
    }

    protected Map<Material, Double> sortByValue(Map<Material, Double> unsortMap) {
        LinkedHashMap<Material, Double> sortedMap = new LinkedHashMap<>();
        ArrayList<Double> list = new ArrayList<>();

        for (Map.Entry<Material, Double> entry : unsortMap.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);
        for (Double num : list) {
            for (Map.Entry<Material, Double> entry : unsortMap.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }
        return sortedMap;
    }

    protected void createInventory(Shop shop) {
        int itemSlot = 0;
        if (!shop.getShopItems().isEmpty()) {
            for (Material material : sortByValue(shop.getShopItems()).keySet()) {
                addItems(material, fancyMaterialName(material.name()), List.of(Presets.MainColor + "Price", Presets.SecondaryColor + "$" + Presets.ThirdColor + shop.getShopItems().get(material)), itemSlot, this);
                itemSlot++;
            }
        }
        setMenuAndInterface(shop.getShopName() + " Prices", 45, InventoryType.CHEST, true, Main.getPlugin(Main.class));
    }

    protected String fancyMaterialName(String materialName) {
        materialName = materialName.replaceAll(" ", "");
        String[] nameList = materialName.split("_");
        String multiWord = Presets.ThirdColor;
        if (nameList.length != 0) {
            for (String rawList : nameList) {
                rawList = rawList.toLowerCase();
                rawList = StringUtils.capitalize(rawList);
                if (nameList.length == 1) {
                    return Presets.ThirdColor + rawList;
                } else {
                    if (nameList[nameList.length - 1].contains(rawList)) {
                        multiWord = multiWord + rawList;
                    } else {
                        multiWord = multiWord + rawList + " ";
                    }

                }
            }
            return multiWord;

        }
        return Presets.MainColor + materialName;
    }

    @Override
    protected boolean rightClickEvents(ItemStack itemStack, Player player) {
        return false;
    }

    @Override
    protected boolean leftClickEvents(ItemStack itemStack, Player player) {
        return false;
    }
}
