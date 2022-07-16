package JeNDS.JPlugins.Files;

import JeNDS.Plugins.PluginAPI.Files.YMLFile;
import JeNDS.Plugins.PluginAPI.JDAPI;
import JeNDS.JPlugins.Main.PF;

public class PlayerDataFile {
    private static final YMLFile PlayerDataFile = JDAPI.getFileManipulation.copyFile("PlayerData.yml", PF.getInstance());

}
