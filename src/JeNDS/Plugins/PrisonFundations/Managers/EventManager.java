package JeNDS.Plugins.PrisonFundations.Managers;


import JeNDS.Plugins.PrisonFundations.CustomEnchants.EnchantEvents;
import JeNDS.Plugins.PrisonFundations.HandBombs.Events.HandGrandesEvents;
import JeNDS.Plugins.PrisonFundations.Main;
import JeNDS.Plugins.PrisonFundations.Mines.Events.MenuEvents;
import JeNDS.Plugins.PrisonFundations.Mines.Events.MineEvents;
import JeNDS.Plugins.PrisonFundations.Mines.Events.MineInfoEvents;
import JeNDS.Plugins.PrisonFundations.Mines.Reagions.RegionCreator;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.Events.UtilityEvents;
import JeNDS.Plugins.PrisonFundations.Other.Events.OtherEvents;
import JeNDS.Plugins.PrisonFundations.Ranks.Events.RankPrefix;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class EventManager implements Listener {


    public static void LoadEvents() {
        Bukkit.getPluginManager().registerEvents(new MineEvents(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new MenuEvents(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new MineInfoEvents(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new RankPrefix(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new RegionCreator(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new UtilityEvents(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new EnchantEvents(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new OtherEvents(), Main.getInstance());
        Bukkit.getPluginManager().registerEvents(new HandGrandesEvents(), Main.getInstance());
    }
}
