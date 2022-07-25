package JeNDS.JPlugins.HandBombs.Files;

import JeNDS.JPlugins.HandBombs.HandBomb;
import JeNDS.JPlugins.PF;
import JeNDS.Plugins.PluginAPI.Files.YMLFile;
import JeNDS.Plugins.PluginAPI.JDAPI;
import JeNDS.Plugins.PluginAPI.Other.JDItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandGrenadeConfig {

    private static YMLFile file;


    public static void LoadHandGrenades() {
        file = JDAPI.getFileManipulation.copyFile("HandGrenade.yml", PF.getInstance());
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
