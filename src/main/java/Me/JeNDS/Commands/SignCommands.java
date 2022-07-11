package Me.JeNDS.Commands;

import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

class SignCommands extends CommandManager {
    public static boolean SignsCommands() {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("signs")) {
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
        if (args.length >= 2) {
            if (args[1].equalsIgnoreCase("mines")) {
                if (args.length >= 3) {
                    if (args[2].equalsIgnoreCase("percentage")) {
                        if (args.length >= 4) {
                            if (!Catch.RunningMines.isEmpty()) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (args[3].equals(mine.getName())) {
                                        Player player = (Player) sender;
                                        if (!player.getInventory().contains(giveSign(mine.getName(), "Percentage"))) {
                                            player.getInventory().addItem(giveSign(mine.getName(), "Percentage"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (args[2].equalsIgnoreCase("time")) {
                        if (args.length >= 4) {
                            if (!Catch.RunningMines.isEmpty()) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (args[3].equals(mine.getName())) {
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
        itemMeta.setDisplayName(Presets.DefaultColor + mineName + " " + todo);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}
