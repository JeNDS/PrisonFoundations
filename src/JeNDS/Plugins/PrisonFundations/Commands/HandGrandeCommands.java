package JeNDS.Plugins.PrisonFundations.Commands;

import JeNDS.Plugins.PrisonFundations.HandBombs.Files.HandGrenadeConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandGrandeCommands extends CommandManager {

    public static void loadCommand() {
        if (CommandManager.cmArgs.length >= 1 && CommandManager.cmArgs[0].equalsIgnoreCase("handGrenades")) {
            if (CommandManager.cmArgs.length > 1) {
                BombCommand();
                Help();
            }
            else {
                CommandManager.sender.sendMessage(CommandManager.defaultColor + "/PF handGrenades Help");
            }

        }
    }

    public static void loadResults() {
        if (CommandManager.tbArgs.length >= 2) {
            CommandManager.tabResult.clear();
            for (String bombName : HandGrenadeConfig.GetHandBombNames()) {
                if (bombName.startsWith(CommandManager.tbArgs[1].toLowerCase())) {
                    CommandManager.tabResult.add(bombName);
                }
            }
            if ("help".startsWith(CommandManager.tbArgs[1].toLowerCase())) {
                CommandManager.tabResult.add("help");
            }
        }
    }

    private static void BombCommand() {
        if (CommandManager.cmArgs.length >= 2) {
            for (String bombName : HandGrenadeConfig.GetHandBombNames()) {
                if (CommandManager.cmArgs[1].equalsIgnoreCase(bombName)) {
                    ItemStack bomb = HandGrenadeConfig.GetBombItemFromFile(bombName);
                    if (bomb != null) {
                        Player player = (Player) CommandManager.sender;
                        player.getInventory().addItem(bomb);
                        CommandManager.sender.sendMessage(CommandManager.defaultColor + "You have been given a " + bombName + " grenade!");
                        return;
                    }
                    CommandManager.sender.sendMessage(CommandManager.defaultColor + "Error occurred while getting bomb");
                    return;
                }
            }
        }

    }

    private static void Help() {
        if (CommandManager.cmArgs.length == 2) {
            if (CommandManager.cmArgs[1].equalsIgnoreCase("help")) {
                CommandManager.sender.sendMessage(CommandManager.defaultColor + "/PF handGrenades <bombName>");
            }
        }
    }

}
