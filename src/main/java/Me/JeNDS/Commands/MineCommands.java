package Me.JeNDS.Commands;

import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.PlayerGUI.MinesGUI.MGUI_1;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static Me.JeNDS.Reagions.RegionCreator.PlayerMinePositionOne;
import static Me.JeNDS.Reagions.RegionCreator.PlayerMinePositionTwo;

public class MineCommands extends CommandManager {

    public static boolean LoadMines() {
        if (args.length >= 1 && args[0].equalsIgnoreCase("Mines")) {
            if (args.length > 1) {
                if (CreateMine()) {
                    return true;
                }
                if (RegionTool()) {
                    return true;
                }
                if (SetMineSpawn()) {
                    return true;
                }
                if (TeleportMine()) {
                    return true;
                }
                if (MineMenu()) {
                    return true;
                }
                if (MinesHelp()) {
                    return true;
                }
            }
            sender.sendMessage(defaultColor + "/PF Mines Help");
            return true;

        }
        return false;
    }

    private static boolean CreateMine() {
        if (args[1].equalsIgnoreCase("CreateMine")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 3 && args[2] != null) {
                    if (PlayerMinePositionOne.containsKey(player) && PlayerMinePositionTwo.containsKey(player)) {
                        if (!Catch.RunningMines.isEmpty()) {
                            for (Mine mine : Catch.RunningMines) {
                                if (mine.getName().equals(args[2])) {
                                    sender.sendMessage(standOutColor + args[2] + defaultColor + " is already Running!");
                                    return true;
                                }
                            }
                        }
                        Mine mine = new Mine(PlayerMinePositionOne.get(player), PlayerMinePositionTwo.get(player), args[2]);
                        mine.createMine();
                        sender.sendMessage(defaultColor + "you have created Mine " + standOutColor + args[2]);
                        return true;
                    } else {
                        sender.sendMessage(defaultColor + "You must select two points first do" + standOutColor + " /region tool" + defaultColor + " for a region selector");
                        return true;
                    }
                } else {
                    sender.sendMessage(defaultColor + "/PF Mines CreateMine " + standOutColor + "<MineName>");
                    return true;
                }
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
                return true;
            }
        } else {
            return false;
        }
    }

    private static boolean RegionTool() {
        if (args[1].equalsIgnoreCase("regionTool")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ItemStack prisonregiontool = new ItemStack(Material.GOLDEN_SHOVEL, 1);
                ItemMeta meta = prisonregiontool.getItemMeta();
                meta.setDisplayName(defaultColor + "Region Selector");
                prisonregiontool.setItemMeta(meta);
                player.getInventory().addItem(prisonregiontool);
                player.sendMessage(defaultColor + "you received a Region Selector ");
                return true;
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
                return true;
            }
        } else {
            return false;
        }

    }

    private static boolean SetMineSpawn() {
        if (args[1].equalsIgnoreCase("setSpawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                for (Mine mine : Catch.RunningMines) {
                    if (Mine.PlayerCloseToMine(player, mine)) {
                        mine.setSpawn(player.getLocation());
                        player.sendMessage(Presets.DefaultColor + "You have set " + Presets.StandOutColor + mine.getName() + Presets.DefaultColor + " spawn!");
                        return true;
                    }
                }
                sender.sendMessage(Presets.DefaultColor + "No Mine found close to you!");
                return true;

            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
                return true;
            }
        } else {
            return false;
        }
    }

    private static boolean TeleportMine() {
        if (args.length >= 2 && args[1].equalsIgnoreCase("tp")) {
            if (sender instanceof Player) {
                Player player = ((Player) sender).getPlayer();
                if (args.length == 4) {
                    if (Bukkit.getPlayer(args[2]) != null) {
                        for (Mine mine : Catch.RunningMines) {
                            if (mine.getName().equals(args[3])) {
                                if (mine.getSpawn() != null) {
                                    Bukkit.getPlayer(args[2]).teleport(mine.getSpawn());
                                    Bukkit.getPlayer(args[2]).sendMessage(defaultColor + "You been teleported  to mine " + standOutColor + args[2]);
                                    sender.sendMessage(defaultColor + "You have teleported " + standOutColor + args[2] + defaultColor + " to mine " + standOutColor + args[3]);
                                    return true;
                                } else {
                                    sender.sendMessage(standOutColor + args[3] + defaultColor + " does not have a spawn!");
                                    return true;
                                }
                            }
                        }
                        sender.sendMessage(standOutColor + args[3] + defaultColor + " is not a valid Mine!");
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.GREEN + args[2] + defaultColor + " is not a valid player");
                        return true;
                    }
                } else {
                    if (args.length == 3) {
                        for (Mine mine : Catch.RunningMines) {
                            if (mine.getName().equals(args[2])) {
                                if (mine.getSpawn() != null) {
                                    player.teleport(mine.getSpawn());
                                    sender.sendMessage(defaultColor + "You been teleported  to mine " + standOutColor + args[2]);
                                    return true;
                                } else {
                                    sender.sendMessage(standOutColor + args[2] + defaultColor + " does not have a spawn!");
                                    return true;
                                }
                            }
                        }
                        sender.sendMessage(standOutColor + args[2] + defaultColor + " is not a valid Mine!");
                        return true;
                    } else {
                        sender.sendMessage(defaultColor + "/PF Mines TP " + standOutColor + "<MineName>");
                        sender.sendMessage(defaultColor + "/PF Mines TP " + standOutColor + "<Player> <MineName>");
                        return true;
                    }


                }

            } else {
                if (args.length == 4) {
                    if (Bukkit.getPlayer(args[2]) != null) {
                        for (Mine mine : Catch.RunningMines) {
                            if (mine.getName().equals(args[3])) {
                                if (mine.getSpawn() != null) {
                                    Bukkit.getPlayer(args[2]).teleport(mine.getSpawn());
                                    sender.sendMessage(defaultColor + "You have teleported " + standOutColor + args[2] + defaultColor + " to mine " + standOutColor + args[3]);
                                    Bukkit.getPlayer(args[2]).sendMessage(defaultColor + "You been teleported  to mine " + standOutColor + args[2]);
                                    return true;
                                } else {
                                    sender.sendMessage(standOutColor + args[3] + defaultColor + " does not have a spawn!");
                                    return true;
                                }
                            }
                        }
                        sender.sendMessage(standOutColor + args[3] + defaultColor + " is not a valid Mine!");
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.GREEN + args[2] + defaultColor + " is not a valid player");
                        return true;
                    }
                } else {
                    sender.sendMessage(defaultColor + "/PF Mines TP " + standOutColor + "<MineName>");
                    sender.sendMessage(defaultColor + "/PF Mines TP " + standOutColor + "<Player> <MineName>");
                    return true;
                }
            }
        } else return false;
    }

    private static boolean MineMenu() {
        if (args[1].equalsIgnoreCase("Menu")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                MGUI_1 mgui_1 = new MGUI_1();
                player.openInventory(mgui_1.getMenu());
               /*

                for (Inventory inventory : MinesMenu.GetMainMenuPages()) {
                    player.openInventory(inventory);
                    break;
                }

                */


                return true;
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
                return true;
            }
        }
        return false;

    }

    private static boolean MinesHelp() {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("help")) {
                sender.sendMessage(defaultColor + "/PF mines createmine <minename>");
                sender.sendMessage(defaultColor + "/PF mines menu");
                sender.sendMessage(defaultColor + "/PF mines regiontool");
                sender.sendMessage(defaultColor + "/PF mines tp");
                return true;
            }
        }
        return false;
    }
}