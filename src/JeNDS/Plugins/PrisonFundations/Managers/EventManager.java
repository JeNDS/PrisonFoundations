package JeNDS.Plugins.PrisonFundations.Managers;


import JeNDS.Plugins.PrisonFundations.CustomEnchants.Events.EnchantEvents;
import JeNDS.Plugins.PrisonFundations.HandBombs.Events.HandGrandesEvents;
import JeNDS.Plugins.PrisonFundations.Mines.Events.MenuEvents;
import JeNDS.Plugins.PrisonFundations.Mines.Events.MineEvents;
import JeNDS.Plugins.PrisonFundations.Mines.Events.MineInfoEvents;
import JeNDS.Plugins.PrisonFundations.Mines.Reagions.RegionCreator;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.Events.UtilityEvents;
import JeNDS.Plugins.PrisonFundations.Other.Events.OtherEvents;
import JeNDS.Plugins.PrisonFundations.PF;
import JeNDS.Plugins.PrisonFundations.Ranks.Events.RankPrefix;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class EventManager implements Listener {


    public static void LoadEvents(){
        Bukkit.getPluginManager().registerEvents(new MineEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new MenuEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new MineInfoEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new RankPrefix(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new RegionCreator(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new UtilityEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new EnchantEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new OtherEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new HandGrandesEvents(), PF.getInstance());
    }
}
