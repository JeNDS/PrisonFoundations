package Me.JeNDS.Commands;

import Me.JeNDS.Objects.Shop;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
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
                            sender.sendMessage(Presets.DefaultColor + "Could Not Find A Shop To Sell Your Items");
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
                            sender.sendMessage(Presets.DefaultColor + "Could Not Find A Shop To Sell the Items");
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

    public static boolean AutoBlock() {
        if (cmd.getName().equalsIgnoreCase("AutoBlock")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("PF.AutoBlock") || sender.hasPermission("PF.Admin") || sender.hasPermission("PF.AutoBlock")) {
                    if (args.length == 0) {
                        if (!Catch.autoBlock.containsKey((((Player) sender).getPlayer()))) {
                            Catch.autoBlock.put(((Player) sender).getPlayer(), true);
                            sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "On");
                            return true;
                        } else {
                            if (Catch.autoBlock.get(((Player) sender).getPlayer())) {
                                Catch.autoBlock.put(((Player) sender).getPlayer(), false);
                                sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "Off");
                                return true;
                            } else {
                                Catch.autoBlock.put(((Player) sender).getPlayer(), true);
                                sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "On");
                                return true;
                            }
                        }
                    }
                }
                if (!sender.hasPermission("PF.AutoBlock")) {
                    sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoBlock");
                    return true;
                }

                if (args.length >= 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (sender.hasPermission("PF.AutoBlock") && sender.hasPermission("PF.AutoBlock.Others")) {
                            if (args.length >= 2) {
                                if (args[1].equalsIgnoreCase("on")) {
                                    Catch.autoBlock.put(Bukkit.getPlayer(args[0]), true);
                                    sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "On" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoBlock has been turn " + standOutColor + "On" + defaultColor + " for You!");
                                    return true;
                                }
                                if (args[1].equalsIgnoreCase("off")) {
                                    Catch.autoBlock.put(Bukkit.getPlayer(args[0]), false);
                                    sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "Off" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoBlock has been turn " + standOutColor + "Off" + defaultColor + " for You!");
                                    return true;
                                }
                            }
                        } else {
                            sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoBlock.Others");
                            return true;
                        }
                    } else {
                        sender.sendMessage(standOutColor + args[0] + defaultColor + " is not a valid Player!");
                        return true;
                    }
                }
                sender.sendMessage(defaultColor + "/AutoBlock <Player> <On>:<Off>");
                return true;
            }

        }
        return false;
    }

    public static boolean AutoSmelt() {
        if (cmd.getName().equalsIgnoreCase("AutoSmelt")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("PF.AutoSmelt") || sender.hasPermission("PF.Admin") || sender.hasPermission("PF.AutoSmelt.Others")) {
                    if (args.length == 0) {
                        if (!Catch.autoSmelt.containsKey((((Player) sender).getPlayer()))) {
                            Catch.autoSmelt.put(((Player) sender).getPlayer(), true);
                            sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "On");
                            return true;
                        } else {
                            if (Catch.autoSmelt.get(((Player) sender).getPlayer())) {
                                Catch.autoSmelt.put(((Player) sender).getPlayer(), false);
                                sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "Off");
                                return true;
                            } else {
                                Catch.autoSmelt.put(((Player) sender).getPlayer(), true);
                                sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "On");
                                return true;
                            }
                        }
                    }
                }
                if (!sender.hasPermission("PF.AutoSmelt")) {
                    sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoSmelt");
                    return true;
                }

                if (args.length >= 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (sender.hasPermission("PF.AutoSmelt.Others") || sender.hasPermission("PF.Admin")) {
                            if (args.length >= 2) {
                                if (args[1].equalsIgnoreCase("on")) {
                                    Catch.autoSmelt.put(Bukkit.getPlayer(args[0]), true);
                                    sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "On" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoSmelt has been turn " + standOutColor + "On" + defaultColor + " for You!");
                                    return true;
                                }
                                if (args[1].equalsIgnoreCase("off")) {
                                    Catch.autoSmelt.put(Bukkit.getPlayer(args[0]), false);
                                    sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "Off" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoSmelt has been turn " + standOutColor + "Off" + defaultColor + " for You!");
                                    return true;
                                }
                            }
                        } else {
                            sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoSmelt.Others");
                            return true;
                        }
                    } else {
                        sender.sendMessage(standOutColor + args[0] + defaultColor + " is not a valid Player!");
                        return true;
                    }
                }
                sender.sendMessage(defaultColor + "/AutoSmelt <Player> <On>:<Off>");
                return true;
            }

        }
        return false;
    }
}
