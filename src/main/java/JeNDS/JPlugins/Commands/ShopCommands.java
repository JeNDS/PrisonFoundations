package JeNDS.JPlugins.Commands;

import JeNDS.JPlugins.Objects.Shop;
import JeNDS.JPlugins.Static.Catch;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShopCommands extends CommandManager {
    public static boolean Sell() {
        if (cmd.getName().equalsIgnoreCase("Sell")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
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
                if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player player = Bukkit.getPlayer(args[0]);
                    if (sender.hasPermission("PF.Sell.Others") || sender.hasPermission("PF.Admin")) {
                        if (Shop.GetPlayerShop(player) != null) {
                            if (Shop.SellPlayerItems(player, false)) {
                                sender.sendMessage(defaultColor + "You sold all " + standOutColor + args[0] + defaultColor + " items");
                                return true;
                            }
                        } else {
                            sender.sendMessage(Presets.MainColor + "Could Not Find A Shop To Sell the Items");
                            return true;
                        }
                    }
                }
                    else {
                        sender.sendMessage(standOutColor + args[0] + defaultColor + " is not a valid Player!");
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean AutoSell() {
        if (cmd.getName().equalsIgnoreCase("AutoSell")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("PF.AutoSell") || sender.hasPermission("PF.Admin") || sender.hasPermission("PF.AutoSell.Others")) {
                    if (args.length == 0) {
                        if (!Catch.autoSell.containsKey((((Player) sender).getPlayer()))) {
                            Catch.autoSell.put(((Player) sender).getPlayer(), true);
                            sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "On");
                            return true;
                        } else {
                            if (Catch.autoSell.get(((Player) sender).getPlayer())) {
                                Catch.autoSell.put(((Player) sender).getPlayer(), false);
                                sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "Off");
                                return true;
                            } else {
                                Catch.autoSell.put(((Player) sender).getPlayer(), true);
                                sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "On");
                                return true;
                            }
                        }
                    }
                }
                if (!sender.hasPermission("PF.AutoSell")) {
                    sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoSell");
                    return true;
                }

                if (args.length >= 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (sender.hasPermission("PF.AutoSell.Others") || sender.hasPermission("PF.Admin")) {
                            if (args.length >= 2) {
                                if (args[1].equalsIgnoreCase("on")) {
                                    Catch.autoSell.put(Bukkit.getPlayer(args[0]), true);
                                    sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "On" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoSell has been turn " + standOutColor + "On" + defaultColor + " for You!");
                                    return true;
                                }
                                if (args[1].equalsIgnoreCase("off")) {
                                    Catch.autoSell.put(Bukkit.getPlayer(args[0]), false);
                                    sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "Off" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoSell has been turn " + standOutColor + "Off" + defaultColor + " for You!");
                                    return true;
                                }
                            }
                        } else {
                            sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoSell.Others");
                            return true;
                        }
                    } else {
                        sender.sendMessage(standOutColor + args[0] + defaultColor + " is not a valid Player!");
                        return true;
                    }
                }
                sender.sendMessage(defaultColor + "/AutoSell <Player> <On>:<Off>");
                return true;
            }

        }
        return false;
    }

}
