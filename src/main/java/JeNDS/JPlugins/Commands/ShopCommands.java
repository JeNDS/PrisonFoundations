package JeNDS.JPlugins.Commands;

import JeNDS.JPlugins.Shops.Shop;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShopCommands extends CommandManager {
    public static boolean Sell() {
        if (cmd.getName().equalsIgnoreCase("Sell")) {
            if (sender instanceof Player player) {
                if (cmArgs.length == 0) {
                    if (player.hasPermission("PF.Sell") || sender.hasPermission("PF.Admin") || sender.hasPermission("PF.SellOthers")) {
                        if (Shop.GetPlayerShop(player) != null) {
                            Shop.SellPlayerItems(player, true);
                        } else {
                            sender.sendMessage(Presets.MainColor + "Could Not Find A Shop To Sell Your Items");
                            return true;
                        }
                    }
                }
            } else {
                if (cmArgs.length == 1) {
                    if (Bukkit.getPlayer(cmArgs[0]) != null) {
                        Player player = Bukkit.getPlayer(cmArgs[0]);
                    if (sender.hasPermission("PF.Sell.Others") || sender.hasPermission("PF.Admin")) {
                        if (Shop.GetPlayerShop(player) != null) {
                            if (Shop.SellPlayerItems(player, false)) {
                                sender.sendMessage(defaultColor + "You sold all " + standOutColor + cmArgs[0] + defaultColor + " items");
                                return true;
                            }
                        } else {
                            sender.sendMessage(Presets.MainColor + "Could Not Find A Shop To Sell the Items");
                            return true;
                        }
                    }
                }
                    else {
                        sender.sendMessage(standOutColor + cmArgs[0] + defaultColor + " is not a valid Player!");
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
