package JeNDS.Plugins.PrisonFundations.Other.Files;

import JeNDS.Plugins.JeNDSAPI.Files.YMLFile;
import JeNDS.Plugins.JeNDSAPI.JDAPI;
import JeNDS.Plugins.PrisonFundations.Main;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Config {
    public static boolean UnbreakableItems = true;
    public static boolean DisableExperience = true;
    public static ArrayList<World> WorldBlackList = new ArrayList<>();
    private static YMLFile ConfigFile = JDAPI.getFileManipulation.copyFile("Config.yml", Main.getInstance());

    public static void LoadConfig() {
        ConfigFile = JDAPI.getFileManipulation.copyFile("Config.yml", Main.getInstance());
        loadPresets();
    }


    private static void loadPresets() {
        FileConfiguration config = ConfigFile.getFileConfiguration();
        if (config.get("Unbreakable Items") != null) {
            UnbreakableItems = config.getBoolean("Unbreakable Items");
        }
        if (config.get("Disable Experience") != null) {
            DisableExperience = config.getBoolean("Disable Experience");
        }
        if (config.get("Main Color") != null) {
            Presets.MainColor = Presets.ColorReplacer(Objects.requireNonNull(config.getString("Main Color")));
        }
        if (config.get("Secondary Color") != null) {
            Presets.SecondaryColor = Presets.ColorReplacer(Objects.requireNonNull(config.getString("Secondary Color")));
        }
        if (config.get("Third Color") != null) {
            Presets.ThirdColor = Presets.ColorReplacer(Objects.requireNonNull(config.getString("Third Color")));
        }
        if (config.get("World Black List") != null) {
            List<String> WorldList = config.getStringList("World Black List");
            if (!WorldList.isEmpty()) {
                for (String worldName : WorldList) {
                    if (Bukkit.getWorld(worldName) != null) WorldBlackList.add(Bukkit.getWorld(worldName));
                }
            }
            Presets.ThirdColor = Presets.ColorReplacer(Objects.requireNonNull(config.getString("Third Color")));
        }

    }
}
