package Me.JeNDS.Main;

import Me.JeNDS.Commands.CommandManager;
import Me.JeNDS.Events.EventManager;
import Me.JeNDS.Files.MineFile;
import Me.JeNDS.Files.RankFile;
import Me.JeNDS.Files.ShopsFile;
import Me.JeNDS.Static.Presets;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PF extends JavaPlugin {

    public static Economy economy = null;
    private static PF PF;

    public static PF getInstance() {
        return PF;
    }

    @Override
    public void onLoad() {
        Presets.LoadPresets();
    }


    @Override
    public void onEnable() {
        PF = this;
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
        EventManager.LoadEvents();
        RankFile.LoadRanks();
        getCommand("pf").setExecutor(new CommandManager());
        getCommand("rankup").setExecutor(new CommandManager());
        getCommand("sell").setExecutor(new CommandManager());
        getCommand("autosell").setExecutor(new CommandManager());
        getCommand("autoblock").setExecutor(new CommandManager());
        getCommand("autosmelt").setExecutor(new CommandManager());
        MineFile.LoadMines();
        ShopsFile.LoadShopFile();
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
