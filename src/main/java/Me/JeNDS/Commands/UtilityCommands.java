package Me.JeNDS.Commands;

import JeNDS.Plugins.PluginAPI.Other.JDItem;
import Me.JeNDS.Static.Presets;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UtilityCommands extends CommandManager {

    public static boolean LoadUtility() {
        if (args.length >= 1 && args[0].equalsIgnoreCase("utility")) {
            if (args.length > 1) {
                if (AutoSmelter()) return true;
                if (AutoBlocker()) return true;
                if (UtilityHelp()) return true;
            }
            sender.sendMessage(defaultColor + "/PF Utility Help");
            return true;

        }
        return false;
    }


    private static boolean AutoSmelter() {
        if (args[1].equalsIgnoreCase("autoSmelter")) {
            if (sender instanceof Player player) {
                player.getInventory().addItem(JDItem.CustomItemStack(Material.FURNACE, Presets.DefaultColor + "Auto Smelter", null));
                player.sendMessage(defaultColor + "you received an auto smelter");
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
            }
            return true;
        } else {
            return false;
        }

    }

    private static boolean AutoBlocker() {
        if (args[1].equalsIgnoreCase("autoBlocker")) {
            if (sender instanceof Player player) {
                player.getInventory().addItem(JDItem.CustomItemStack(Material.CRAFTING_TABLE, Presets.DefaultColor + "Auto Blocker", null));
                player.sendMessage(defaultColor + "you received an auto blocker");
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
            }
            return true;
        } else {
            return false;
        }

    }

    private static boolean UtilityHelp() {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("help")) {
                sender.sendMessage(defaultColor + "/PF utility autoSmelter");
                sender.sendMessage(defaultColor + "/PF utility autoBlocker");
                return true;
            }
        }
        return false;
    }
}
