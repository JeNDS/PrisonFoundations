package JeNDS.JPlugins.Events;

import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Reagions.RegionCreator;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class EventManager implements Listener {



    public static void LoadEvents(){
        Bukkit.getPluginManager().registerEvents(new MineEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new MenuEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new SignEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new RankPrefix(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new RegionCreator(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new UtilityEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new EnchantEvents(), PF.getInstance());
        Bukkit.getPluginManager().registerEvents(new OtherEvents(), PF.getInstance());
    }
}
