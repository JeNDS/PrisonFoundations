package JeNDS.JPlugins.Files;

import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Static.Presets;
import JeNDS.Plugins.PluginAPI.Files.YMLFile;
import JeNDS.Plugins.PluginAPI.JDAPI;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class Config {
    public static boolean UnbreakableItems = true;
    private static YMLFile ConfigFile = JDAPI.getFileManipulation.copyFile("Config.yml", PF.getInstance());

    public static void LoadConfig() {
        ConfigFile = JDAPI.getFileManipulation.copyFile("Config.yml", PF.getInstance());
        loadPresets();
    }


    private static void loadPresets() {
        FileConfiguration config = ConfigFile.getFileConfiguration();
        ;
        if (config.get("Unbreakable Items") != null) {
            UnbreakableItems = config.getBoolean("Unbreakable Items");
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
    }
}
