package JeNDS.Plugins.PrisonFundations.Commands.PFCommands;


import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SignCommands extends CommandManager {
    protected static void LoadCommand() {
        if (hasPermission(commandSender, "pf.signs"))
            if (commandArgs[0].equalsIgnoreCase("signs")) {
                if (commandArgs.length > 1) {
                    MinesSigns();
                } else {
                    commandSender.sendMessage(defaultColor + "/PF Signs <MineName> <Percentage | Time>");
                }
            }


    }

    protected static void LoadResults() {
        if (tabArgs.length >= 2) {
            if (hasPermission(tabSender, "pf.signs"))
                if (tabArgs[0].equalsIgnoreCase("signs")) {
                    tabResult.clear();
                    if (tabArgs.length == 2) {
                        for (Mine mine : Catch.RunningMines) {
                            if (mine.getName().toLowerCase().startsWith(tabArgs[1].toLowerCase())) {
                                tabResult.add(mine.getName());
                            }
                        }
                    }
                    if (tabArgs.length == 3) {
                        if ("Percentage".toLowerCase().startsWith(tabArgs[2].toLowerCase()))
                            tabResult.add("Percentage");
                        if ("Time".toLowerCase().startsWith(tabArgs[2].toLowerCase())) tabResult.add("Time");
                    }
                }
        }
    }

    private static void MinesSigns() {
        if (commandArgs.length == 3) {
            if (!Catch.RunningMines.isEmpty()) {
                for (Mine mine : Catch.RunningMines) {
                    if (commandArgs[1].equalsIgnoreCase(mine.getName())) {
                        Player player = (Player) commandSender;
                        if (commandArgs[2].equalsIgnoreCase("percentage")) {
                            if (!player.getInventory().contains(giveSign(mine.getName(), "Percentage"))) {
                                player.getInventory().addItem(giveSign(mine.getName(), "Percentage"));
                            }
                            return;
                        }
                        if (commandArgs[2].equalsIgnoreCase("time")) {
                            if (!player.getInventory().contains(giveSign(mine.getName(), "time"))) {
                                player.getInventory().addItem(giveSign(mine.getName(), "time"));
                            }
                            return;
                        }
                    }
                }
            }
        }

    }


    private static ItemStack giveSign(String mineName, String todo) {
        ItemStack itemStack = new ItemStack(Material.AIR);

        if (Bukkit.getVersion().contains("1.4.4")) {
            itemStack.setType(Material.OAK_SIGN);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Presets.MainColor + mineName + " " + todo);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}
