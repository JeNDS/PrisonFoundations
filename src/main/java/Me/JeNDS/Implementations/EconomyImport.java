package Me.JeNDS.Implementations;

import Me.JeNDS.Main.PF;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyImport {
    public static Economy Economy = null;

    public static void LoadEconomy(){
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Prison Foundations is Disable - Vault Not Found!");
            Bukkit.getServer().getPluginManager().disablePlugin(PF.getInstance());
            return;
        }
        if (!setupEconomy()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Prison Foundations is Disable - Economy Plugin Not found!");
            Bukkit.getServer().getPluginManager().disablePlugin(PF.getInstance());
            return;
        }
    }
    private static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            Economy = rsp.getProvider();
        }

        return Economy != null;
    }
}
