package JeNDS.Plugins.PrisonFundations.Commands.PFCommand;


import JeNDS.Plugins.JeNDSAPI.Other.JDItem;
import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UtilityCommands extends CommandManager {

    private static void AutoSmelter() {
        if (cmArgs[1].equalsIgnoreCase("autoSmelter")) {
            if (sender instanceof Player player) {
                player.getInventory().addItem(JDItem.CustomItemStack(Material.FURNACE, Presets.MainColor + "Auto Smelter", null));
                player.sendMessage(defaultColor + "you received an auto smelter");
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
            }
        }

    }

    private static void AutoBlocker() {
        if (cmArgs[1].equalsIgnoreCase("autoBlocker")) {
            if (sender instanceof Player player) {
                player.getInventory().addItem(JDItem.CustomItemStack(Material.CRAFTING_TABLE, Presets.MainColor + "Auto Blocker", null));
                player.sendMessage(defaultColor + "you received an auto blocker");
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
            }
        }

    }

    private static void UtilityHelp() {
        if (cmArgs.length == 2) {
            if (cmArgs[1].equalsIgnoreCase("help")) {
                sender.sendMessage(defaultColor + "/PF utility autoSmelter");
                sender.sendMessage(defaultColor + "/PF utility autoBlocker");
            }
        }
    }


    public static void loadCommand() {
        if (cmArgs.length >= 1 && cmArgs[0].equalsIgnoreCase("utility")) {
            if (cmArgs.length > 1) {
                AutoSmelter();
                AutoBlocker();
                UtilityHelp();
            }
            sender.sendMessage(defaultColor + "/PF Utility Help");

        }
    }

    public static void loadResults() {
        if (tbArgs.length == 2) {
            if (tbArgs[0].equalsIgnoreCase("utility")) {
                tabResult.clear();
                if ("autoblocker".startsWith(tbArgs[1].toLowerCase())) {
                    tabResult.add("autoblocker");
                }
                if ("autosmelter".startsWith(tbArgs[1].toLowerCase())) {
                    tabResult.add("autosmelter");
                }
            }
        }
    }
}
