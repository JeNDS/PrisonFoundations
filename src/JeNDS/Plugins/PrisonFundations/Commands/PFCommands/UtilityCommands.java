package JeNDS.Plugins.PrisonFundations.Commands.PFCommands;


import JeNDS.Plugins.JeNDSAPI.Other.JDItem;
import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UtilityCommands extends CommandManager {


    public static void LoadCommand() {
        if (hasPermission(commandSender, "pf.utilities"))
            if (commandArgs.length >= 1 && commandArgs[0].equalsIgnoreCase("utility")) {
                if (commandArgs.length > 1) {
                    AutoSmelter();
                    AutoBlocker();
                }
            }
    }

    public static void LoadResults() {
        if (tabArgs.length == 2) {
            if (hasPermission(tabSender, "pf.utilities"))
                if (tabArgs[0].equalsIgnoreCase("utility")) {
                    tabResult.clear();
                    if ("AutoBlocker".toLowerCase().startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("AutoBlocker");
                    }
                    if ("AutoSmelter".toLowerCase().startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("AutoSmelter");
                    }
                }
        }
    }

    private static void AutoSmelter() {
        if (commandArgs[1].equalsIgnoreCase("autoSmelter")) {
            if (commandSender instanceof Player player) {
                player.getInventory().addItem(JDItem.CustomItemStack(Material.FURNACE, Presets.MainColor + "Auto Smelter", null));
                player.sendMessage(defaultColor + "you received an auto smelter");
            } else {
                commandSender.sendMessage(defaultColor + "You must be a player to do this!");
            }
        }

    }

    private static void AutoBlocker() {
        if (commandArgs[1].equalsIgnoreCase("autoBlocker")) {
            if (commandSender instanceof Player player) {
                player.getInventory().addItem(JDItem.CustomItemStack(Material.CRAFTING_TABLE, Presets.MainColor + "Auto Blocker", null));
                player.sendMessage(defaultColor + "you received an auto blocker");
            } else {
                commandSender.sendMessage(defaultColor + "You must be a player to do this!");
            }
        }

    }
}
