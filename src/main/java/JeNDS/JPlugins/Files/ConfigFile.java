package JeNDS.JPlugins.Files;

import JeNDS.Plugins.PluginAPI.Files.YMLFile;
import JeNDS.Plugins.PluginAPI.JDAPI;
import JeNDS.JPlugins.Main.PF;

public class ConfigFile {
    private static YMLFile ConfigFile = JDAPI.getFileManipulation.copyFile("Config.yml", PF.getInstance());
}
