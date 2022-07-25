package JeNDS.JPlugins.Commands;

import JeNDS.JPlugins.HandBombs.Files.HandGrenadeConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandGrandeCommands extends CommandManager {

    public static void loadCommand() {
        if (cmArgs.length >= 1 && cmArgs[0].equalsIgnoreCase("handGrenades")) {
            if (cmArgs.length > 1) {
                BombCommand();
                Help();
            }
            else {
                sender.sendMessage(defaultColor + "/PF handGrenades Help");
            }

        }
    }

    public static void loadResults() {
        if (tbArgs.length >= 2) {
            tabResult.clear();
            for (String bombName : HandGrenadeConfig.GetHandBombNames()) {
                if (bombName.startsWith(tbArgs[1].toLowerCase())) {
                    tabResult.add(bombName);
                }
            }
            if ("help".startsWith(tbArgs[1].toLowerCase())) {
                tabResult.add("help");
            }
        }
    }

    private static void BombCommand() {
        if (cmArgs.length >= 2) {
            for (String bombName : HandGrenadeConfig.GetHandBombNames()) {
                if (cmArgs[1].equalsIgnoreCase(bombName)) {
                    ItemStack bomb = HandGrenadeConfig.GetBombItemFromFile(bombName);
                    if (bomb != null) {
                        Player player = (Player) sender;
                        player.getInventory().addItem(bomb);
                        sender.sendMessage(defaultColor + "You have been given a " + bombName + " grenade!");
                        return;
                    }
                    sender.sendMessage(defaultColor + "Error occurred while getting bomb");
                    return;
                }
            }
        }

    }

    private static void Help() {
        if (cmArgs.length == 2) {
            if (cmArgs[1].equalsIgnoreCase("help")) {
                sender.sendMessage(defaultColor + "/PF handGrenades <bombName>");
            }
        }
    }

}
