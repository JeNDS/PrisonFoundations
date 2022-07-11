package Me.JeNDS.Main;

import Me.JeNDS.Commands.CommandManager;
import Me.JeNDS.CustomEnchants.EnchantManager;
import Me.JeNDS.Events.EventManager;
import Me.JeNDS.Files.MineFile;
import Me.JeNDS.Files.RankFile;
import Me.JeNDS.Files.ShopsFile;
import Me.JeNDS.Implementations.BStatsImport;
import Me.JeNDS.Implementations.EconomyImport;
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
    }

    @Override
    public void onDisable() {

    }


}
