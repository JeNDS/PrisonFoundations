package JeNDS.Plugins.PrisonFundations.Commands.OtherCommands;


import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Shops.Shop;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShopCommands extends CommandManager {
    public static void LoadCommand() {
        if (command.getName().equalsIgnoreCase("Sell")) {
            IsCommand = true;
            if (commandArgs.length == 0) {
                if (commandSender instanceof Player player) {
                    if (hasPermission(commandSender, "pf.sell")) {
                        if (Shop.GetPlayerShop(player) != null) {
                            Shop.SellPlayerItems(player);
                        } else {
                            commandSender.sendMessage(Presets.MainColor + "Could Not Find A Shop To Sell Your Items");

                        }
                    } else
                        player.sendMessage(color1 + "You don't have " + color2 + "pf.sell" + color1 + " permission");
                }
            }
            if (commandArgs.length >= 1) {
                if (Bukkit.getPlayer(commandArgs[0]) != null) {
                    Player player = Bukkit.getPlayer(commandArgs[0]);
                    if (hasPermission(commandSender, "pf.sell.others")) {
                        if (commandArgs.length == 2) {
                            Shop shop = Shop.GetShopFrontString(commandArgs[1]);
                            if (shop != null) {
                                Shop.SellPlayerItems(player, shop);
                                commandSender.sendMessage(color1 + "You sold all " + color2 + commandArgs[0] + color1 + " items at " + color2 + shop.getShopDisplayName());
                                return;

                            }
                        }
                        if (commandArgs.length == 1) {
                            if (Shop.GetPlayerShop(player) != null) {
                                if (Shop.SellPlayerItems(player)) {
                                    commandSender.sendMessage(color1 + "You sold all " + color2 + commandArgs[0] + color1 + " items at " + color2 + Shop.GetPlayerShop(player).getShopDisplayName());

                                }
                            } else {
                                commandSender.sendMessage(Presets.MainColor + "Could Not Find A Shop To Sell the Items");
                            }
                        }
                    }
                } else {
                    commandSender.sendMessage(color2 + commandArgs[0] + color1 + " is not a valid Player!");

                }
            }

        }
    }

    public static void LoadResults() {
        if (tabCommand.getName().equalsIgnoreCase("sell")) {
            if (hasPermission(tabSender, "pf.sell.others")) {
                if (tabArgs.length == 1) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getName().toLowerCase().startsWith(tabArgs[0].toLowerCase())) {
                            tabResult.add(player.getName());
                        }
                    }
                }
                if (tabArgs.length == 2) {
                    for (Shop shop : Catch.Shops) {
                        if (shop.getShopName().toLowerCase().startsWith(tabArgs[1])) {
                            tabResult.add(shop.getShopName());
                        }
                    }
                }

            }
        }
    }
}
