package JeNDS.Plugins.PrisonFundations.Commands.PFCommand.Mines;


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
    public static boolean SignsCommands() {
        if (cmArgs.length >= 1) {
            if (cmArgs[0].equalsIgnoreCase("signs")) {
                if (MinesSigns()) {
                    return true;
                }
                sender.sendMessage(defaultColor + "/PF Signs Help");
                return true;
            }
        }
        return false;
    }

    public static boolean MinesSigns() {
        if (cmArgs.length >= 2) {
            if (cmArgs[1].equalsIgnoreCase("mines")) {
                if (cmArgs.length >= 3) {
                    if (cmArgs[2].equalsIgnoreCase("percentage")) {
                        if (cmArgs.length >= 4) {
                            if (!Catch.RunningMines.isEmpty()) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (cmArgs[3].equals(mine.getName())) {
                                        Player player = (Player) sender;
                                        if (!player.getInventory().contains(giveSign(mine.getName(), "Percentage"))) {
                                            player.getInventory().addItem(giveSign(mine.getName(), "Percentage"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (cmArgs[2].equalsIgnoreCase("time")) {
                        if (cmArgs.length >= 4) {
                            if (!Catch.RunningMines.isEmpty()) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (cmArgs[3].equals(mine.getName())) {
                                        Player player = (Player) sender;
                                        if (!player.getInventory().contains(giveSign(mine.getName(), "Time"))) {
                                            player.getInventory().addItem(giveSign(mine.getName(), "Time"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean ShopsSigns() {
        return false;
    }

    private static ItemStack giveSign(String mineName, String todo) {
        ItemStack itemStack = new ItemStack(Material.AIR);

        if (Bukkit.getVersion().contains("1.4.4")) {
            itemStack.setType(Material.OAK_SIGN);
        } else {
            //1.3.2
            //itemStack.setType(Material.SIGN);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Presets.MainColor + mineName + " " + todo);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}
