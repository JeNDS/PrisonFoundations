package JeNDS.JPlugins.Main;

import JeNDS.JPlugins.Commands.CommandManager;
import JeNDS.JPlugins.CustomEnchants.EnchantManager;
import JeNDS.JPlugins.Events.EventManager;
import JeNDS.JPlugins.Files.MineFile;
import JeNDS.JPlugins.Files.RankFile;
import JeNDS.JPlugins.Files.ShopsFile;
import JeNDS.JPlugins.Files.UtilitiesFile;
import JeNDS.JPlugins.Implementations.BStatsImport;
import JeNDS.JPlugins.Implementations.EconomyImport;
import org.bukkit.plugin.java.JavaPlugin;

public class PF extends JavaPlugin {


    private static PF PF;

    public static PF getInstance() {
        return PF;
    }

    @Override
    public void onEnable() {
        PF = this;
        EconomyImport.LoadEconomy();
        BStatsImport.LoadBStats();
        CommandManager.LoadCommands();
        EnchantManager.LoadEnchantments();
        EventManager.LoadEvents();
        RankFile.LoadRanks();
        MineFile.LoadMines();
        ShopsFile.LoadShopFile();
        UtilitiesFile.LoadUtilities();
    }

    @Override
    public void onDisable() {

    }


}
