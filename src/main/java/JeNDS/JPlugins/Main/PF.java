package JeNDS.JPlugins.Main;

import JeNDS.JPlugins.Commands.CommandManager;
import JeNDS.JPlugins.CustomEnchants.EnchantManager;
import JeNDS.JPlugins.Events.EventManager;
import JeNDS.JPlugins.Files.*;
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
        Config.LoadConfig();
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
