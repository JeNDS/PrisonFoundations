package JeNDS.JPlugins.Managers;

import JeNDS.JPlugins.CustomEnchants.Events.EnchantEvents;
import JeNDS.JPlugins.HandBombs.Events.HandGrandesEvents;
import JeNDS.JPlugins.Other.Events.OtherEvents;
import JeNDS.JPlugins.PF;
import JeNDS.JPlugins.Mines.Events.MenuEvents;
import JeNDS.JPlugins.Mines.Events.MineEvents;
import JeNDS.JPlugins.Mines.Events.MineInfoEvents;
import JeNDS.JPlugins.Mines.Reagions.RegionCreator;
import JeNDS.JPlugins.Mines.Utilities.Events.UtilityEvents;
import JeNDS.JPlugins.Ranks.Events.RankPrefix;
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
