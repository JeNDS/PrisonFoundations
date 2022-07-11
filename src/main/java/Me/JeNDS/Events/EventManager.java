package Me.JeNDS.Events;

import Me.JeNDS.Main.PF;
import Me.JeNDS.Reagions.RegionCreator;
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
    }
}
