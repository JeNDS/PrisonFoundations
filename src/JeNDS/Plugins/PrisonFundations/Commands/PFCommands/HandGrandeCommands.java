package JeNDS.Plugins.PrisonFundations.Commands.PFCommands;

import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.HandBombs.Files.HandGrenadeConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandGrandeCommands extends CommandManager {

    protected static void LoadCommand() {

        if (commandArgs.length >= 1 && commandArgs[0].equalsIgnoreCase("handGrenades")) {
            if (hasPermission(commandSender, "pf.handgrandes")) {
                if (commandArgs.length >= 2 && commandArgs[1].equalsIgnoreCase("give"))
                    if (commandArgs.length >= 3)
                        if (Bukkit.getPlayer(commandArgs[2]) != null) {
                            Player player = Bukkit.getPlayer(commandArgs[2]);
                            if (commandArgs.length >= 4) {
                                for (String bombName : HandGrenadeConfig.GetHandBombNames()) {
                                    if (commandArgs[3].equalsIgnoreCase(bombName)) {
                                        ItemStack bomb = HandGrenadeConfig.GetBombItemFromFile(bombName);
                                        if (bomb != null) {
                                            if (commandArgs.length == 5) {
                                                Integer integer = Integer.parseInt(commandArgs[4]);
                                                if (integer != null) {
                                                    bomb.setAmount(integer);
                                                    player.getInventory().addItem(bomb);
                                                    commandSender.sendMessage(defaultColor + "You have given " + player.getName() + " " + integer + " " + bombName + " grenades!");
                                                    return;
                                                }
                                            }
                                            player.getInventory().addItem(bomb);
                                            commandSender.sendMessage(defaultColor + "You have given " + player.getName() + " a " + bombName + " grenade!");
                                            return;
                                        }
                                        commandSender.sendMessage(defaultColor + "Error occurred while getting bomb");
                                        return;
                                    }
                                }
                            }

                        } else {
                            commandSender.sendMessage(standOutColor + commandArgs[2] + defaultColor + " is not a valid Player!");
                            return;
                        }
            }
            commandSender.sendMessage(defaultColor + "/PF handGrenades give <player> <grandeName> [amount]");
        }
    }

    protected static void LoadResults() {
        if (tabArgs.length >= 2) {
            if (tabArgs[0].equalsIgnoreCase("handGrenades")) {
                if (hasPermission(tabSender, "pf.handgrandes")) {
                    tabResult.clear();
                    if (tabArgs.length == 2 && "give".startsWith(tabArgs[1])) {
                        tabResult.add("give");
                    }
                    if (tabArgs[1].equalsIgnoreCase("give")) {
                        if (tabArgs.length == 3) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (player.getName().toLowerCase().startsWith(tabArgs[2])) {
                                    tabResult.add(player.getName());
                                }
                            }
                        }
                        if (tabArgs.length == 4) {
                            for (String bombName : HandGrenadeConfig.GetHandBombNames()) {
                                String lowerCaseName = bombName.toLowerCase();
                                if (lowerCaseName.startsWith(tabArgs[3].toLowerCase())) {
                                    tabResult.add(bombName);
                                }
                            }
                        }

                    }
                }
            }
        }
    }

}
