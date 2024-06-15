package JeNDS.Plugins.PrisonFundations;


import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.HandBombs.Files.HandGrenadeConfig;
import JeNDS.Plugins.PrisonFundations.CustomEnchants.EnchantManager;
import JeNDS.Plugins.PrisonFundations.Managers.EventManager;
import JeNDS.Plugins.PrisonFundations.Mines.Files.MineFile;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.Files.UtilitiesFile;
import JeNDS.Plugins.PrisonFundations.Other.Files.Config;
import JeNDS.Plugins.PrisonFundations.Other.Implementations.BStatsImport;
import JeNDS.Plugins.PrisonFundations.Other.Implementations.EconomyImport;
import JeNDS.Plugins.PrisonFundations.Other.Implementations.PlaceHolders;
import JeNDS.Plugins.PrisonFundations.PlayerData.Files.PlayerDataFile;
import JeNDS.Plugins.PrisonFundations.Ranks.File.RankFile;
import JeNDS.Plugins.PrisonFundations.Shops.File.ShopsFile;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {


    private static Main Plugin;

    public static Main getInstance() {
        return Plugin;
    }

    @Override
    public void onEnable() {
        Plugin = this;
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
    //todo Language File
    //todo lucky crate
    //todo enchantments for mines and gear
    //todo commands and permission
    //todo npc integration

}
