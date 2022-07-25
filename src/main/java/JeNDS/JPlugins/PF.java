package JeNDS.JPlugins;

import JeNDS.JPlugins.HandBombs.Files.HandGrenadeConfig;
import JeNDS.JPlugins.Managers.EnchantManager;
import JeNDS.JPlugins.Managers.EventManager;
import JeNDS.JPlugins.Commands.CommandManager;
import JeNDS.JPlugins.Mines.Files.MineFile;
import JeNDS.JPlugins.Mines.Utilities.Files.UtilitiesFile;
import JeNDS.JPlugins.Other.Files.Config;
import JeNDS.JPlugins.Other.Implementations.BStatsImport;
import JeNDS.JPlugins.Other.Implementations.EconomyImport;
import JeNDS.JPlugins.Other.Implementations.PlaceHolders;
import JeNDS.JPlugins.PlayerData.Files.PlayerDataFile;
import JeNDS.JPlugins.Ranks.File.RankFile;
import JeNDS.JPlugins.Shops.File.ShopsFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PF extends JavaPlugin {


    private static PF PF;

    public static PF getInstance() {
        return PF;
    }

    @Override
    public void onEnable() {
        PF = this;
        Config.LoadConfig();
        EconomyImport.LoadEconomy();
        BStatsImport.LoadBStats();
        new CommandManager().loadCommands();
        EnchantManager.LoadEnchantments();
        EventManager.LoadEvents();
        RankFile.LoadRanks();
        MineFile.LoadMines();
        ShopsFile.LoadShopFile();
        UtilitiesFile.LoadUtilities();
        PlaceHolders.LoadPlaceHolders();
        PlayerDataFile.LoadDataFile();
        HandGrenadeConfig.LoadHandGrenades();

    }

    @Override
    public void onDisable() {
        PlayerDataFile.UnLoadDataFile();
    }

    //Ideas that need to be implemented to the plugin later on
    //todo Language File
    //todo lucky crate
    //todo hand grandes
    //todo makes commands cleaner


}
