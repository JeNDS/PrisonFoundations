package JeNDS.Plugins.PrisonFundations.Commands.OtherCommands;


import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Shops.PricesGUI;
import JeNDS.Plugins.PrisonFundations.Shops.Shop;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PricesCommand extends CommandManager {
    public static void LoadCommand() {
        if (command.getName().equalsIgnoreCase("prices")) {
            IsCommand = true;
            if (commandSender instanceof Player player) {
                if (hasPermission(commandSender, "pf.prices")) {
                    if (commandArgs.length == 0) {
                        if (Shop.GetPlayerShop(player) != null) {
                            PricesGUI pricesGUI = new PricesGUI(Shop.GetPlayerShop(player));
                            player.openInventory(pricesGUI.getMenu());
                        } else {
                            commandSender.sendMessage(Presets.MainColor + "Could Not Find A Shop");
                        }
                    }
                    if (commandArgs.length == 1) {
                        if (Shop.GetShopFrontString(commandArgs[0]) != null) {
                            if (player.hasPermission("pf.shop." + commandArgs[0].toLowerCase())) {
                                PricesGUI pricesGUI = new PricesGUI(Shop.GetShopFrontString(commandArgs[0]));
                                player.openInventory(pricesGUI.getMenu());
                            } else {
                                player.sendMessage(color1 + "You don't have " + color2 + commandArgs[0] + color1 + " unlock!");
                            }
                        }
                    } else {
                        commandSender.sendMessage(color2 + commandArgs[0] + Presets.MainColor + " Is not a valid Shop!");
                    }

                } else
                    player.sendMessage(color1 + "You don't have " + color2 + "pf.prices" + color1 + " permission");
            } else {
                if (commandArgs.length == 2) {
                    if (Bukkit.getPlayer(commandArgs[1]) != null) {
                        if (Shop.GetShopFrontString(commandArgs[0]) != null) {
                            PricesGUI pricesGUI = new PricesGUI(Shop.GetShopFrontString(commandArgs[0]));
                            Bukkit.getPlayer(commandArgs[1]).openInventory(pricesGUI.getMenu());
                        }
                    }
                }
            }

        }
    }

    public static void LoadResults() {
        if (tabCommand.getName().equalsIgnoreCase("prices")) {
            if (hasPermission(tabSender, "pf.prices")) {
                if (tabArgs.length == 1) {
                    for (Shop shop : Catch.Shops) {
                        if (shop.getShopName().toLowerCase().startsWith(tabArgs[0].toLowerCase())) {
                            tabResult.add(shop.getShopName());
                        }
                    }
                }

            }
        }
    }
}
