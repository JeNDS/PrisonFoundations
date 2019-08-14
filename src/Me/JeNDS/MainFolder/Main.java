package Me.JeNDS.MainFolder;

import Me.JeNDS.Chat.RankPrefix;
import Me.JeNDS.Commands.CommandsClass;
import Me.JeNDS.Events.ShopEvents;
import Me.JeNDS.Files.MineFile;
import Me.JeNDS.Files.RankFile;
import Me.JeNDS.Files.ShopsFile;
import Me.JeNDS.Objects.Mine;
import Me.JeNDS.PlayerMenus.Listeners.MinesMenuListener;
import Me.JeNDS.Reagions.RegionCreator;
import Me.JeNDS.Static.Presets;
import Me.JeNDS.Utilities.AutoPickup.AutoPickUpListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    public static Economy economy = null;

    @Override
    public void onLoad() {
        Presets.LoadPresets();
    }

    @Override
    public void onEnable() {
        if(Bukkit.getVersion().contains("1.3.2")){

        }
        else if(Bukkit.getVersion().contains("1.4.4")){

        }
        else {


        }
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Prison Foundations is Disable - Vault Not Found!");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!setupEconomy()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Prison Foundations is Disable - Economy Plugin Not found!");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getPluginManager().registerEvents(new AutoPickUpListener(), this);
        Bukkit.getPluginManager().registerEvents(new RegionCreator(), this);
        Bukkit.getPluginManager().registerEvents(new MinesMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new RankPrefix(), this);
        Bukkit.getPluginManager().registerEvents(new OtherEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ShopEvents(), this);
        getCommand("pf").setExecutor(new CommandsClass());
        getCommand("rankup").setExecutor(new CommandsClass());
        getCommand("sell").setExecutor(new CommandsClass());
        getCommand("autosell").setExecutor(new CommandsClass());
        getCommand("autoblock").setExecutor(new CommandsClass());
        getCommand("autosmelt").setExecutor(new CommandsClass());
        MineFile.LoadMines();
        //dont remove
        RankFile Rankfile= new RankFile();
        ShopsFile shopsFile = new ShopsFile();
    }

    @Override
    public void onDisable() {
    }


    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
        }

        return economy != null;
    }

}
