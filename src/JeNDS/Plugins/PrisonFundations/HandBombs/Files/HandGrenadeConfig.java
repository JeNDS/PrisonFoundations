package JeNDS.Plugins.PrisonFundations.HandBombs.Files;


import JeNDS.Plugins.JeNDSAPI.Files.YMLFile;
import JeNDS.Plugins.JeNDSAPI.JDAPI;
import JeNDS.Plugins.JeNDSAPI.Other.JDItem;
import JeNDS.Plugins.PrisonFundations.HandBombs.HandBomb;
import JeNDS.Plugins.PrisonFundations.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HandGrenadeConfig {

    private static YMLFile file;


    public static void LoadHandGrenades() {
        file = JDAPI.getFileManipulation.copyFile("HandGrenade.yml", Main.getInstance());
    }


    public static HandBomb GetHandBombFromConfig(Player player, ItemStack itemStack) {
        ConfigurationSection section = file.getFileConfiguration().getConfigurationSection("");
        for (String bomb : section.getKeys(false)) {
            int size = file.getFileConfiguration().getInt(bomb + ".Size");
            int speed = file.getFileConfiguration().getInt(bomb + ".Speed");
            ItemStack bombItem = GetBombItemFromFile(bomb);
            if (bombItem != null)
                if (bombItem.isSimilar(itemStack)) {
                    return new HandBomb(size, speed, itemStack, player);
                }
        }
        return null;
    }

    public static ArrayList<String> GetHandBombNames() {
        ConfigurationSection section = file.getFileConfiguration().getConfigurationSection("");
        return new ArrayList<>(section.getKeys(false));
    }

    public static ItemStack GetBombItemFromFile(String bombName) {
        ConfigurationSection section = file.getFileConfiguration().getConfigurationSection("");
        for (String bomb : section.getKeys(false)) {
            if (bomb.equalsIgnoreCase(bombName)) {
                String name = file.getFileConfiguration().getString(bomb + ".Name");
                List<String> lore = file.getFileConfiguration().getStringList(bomb + ".Lore");
                String materialName = file.getFileConfiguration().getString(bomb + ".Material");
                assert materialName != null;
                Material material = Material.getMaterial(materialName);
                if (material != null) {
                    return JDItem.CustomItemStack(material, name, lore);
                }
            }
        }
        return null;
    }
}
