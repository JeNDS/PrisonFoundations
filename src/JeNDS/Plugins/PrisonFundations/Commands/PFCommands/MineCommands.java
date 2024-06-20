package JeNDS.Plugins.PrisonFundations.Commands.PFCommands;

import JeNDS.Plugins.JeNDSAPI.Other.JDItem;
import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.Mines.MinesGUI.MGUI_1;
import JeNDS.Plugins.PrisonFundations.Mines.MinesGUI.MineOptions.MineOptionsGUI;
import JeNDS.Plugins.PrisonFundations.Mines.Reagions.RegionCreator;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MineCommands extends CommandManager {

    protected static void LoadCommand() {
        if (commandArgs.length >= 1 && commandArgs[0].equalsIgnoreCase("Mines")) {
            if (hasPermission(commandSender, "pf.mines")) if (commandArgs.length > 1) {
                CreateMine();
                RegionTool();
                SetMineSpawn();
                TeleportMine();
                MineMenu();
                MinesHelp();
                HologramTool();
            }
            //sender.sendMessage(defaultColor + "/PF Mines Help");
            IsCommand = true;
        }
    }

    protected static void LoadResults() {
        if (tabArgs.length >= 2) {
            if (hasPermission(tabSender, "pf.mines")) if (tabArgs[0].equalsIgnoreCase("mines")) {
                tabResult.clear();
                if (tabArgs.length == 2) {
                    if ("regiontool".startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("regiontool");
                    }
                    if ("hologramremover".startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("hologramremover");
                    }
                    if ("help".startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("help");
                    }
                    if ("menu".startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("menu");
                    }
                    if ("createmine".startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("createmine");
                    }

                    if ("tp".startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("tp");
                    }
                    if ("setspawn".startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("setspawn");
                    }
                }
                if (tabArgs[1].equalsIgnoreCase("tp")) {
                    if (tabArgs.length >= 3) {
                        if (tabArgs.length == 3) {
                            if (!Catch.RunningMines.isEmpty()) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (mine.getConfigName().startsWith(tabArgs[2].toLowerCase())) {
                                        tabResult.add(mine.getConfigName());
                                    }
                                }
                            }
                        }
                        if (tabArgs.length == 4) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (player.getName().toLowerCase().startsWith(tabArgs[3].toLowerCase())) {
                                    tabResult.add(player.getName());
                                }
                            }

                        }

                    }
                }
                if (tabArgs.length == 3) {
                    if (tabArgs[1].equalsIgnoreCase("setspawn")) {
                        for (Mine mine : Catch.RunningMines) {
                            if (mine.getConfigName().startsWith(tabArgs[2].toLowerCase())) {
                                tabResult.add(mine.getConfigName());
                            }
                        }
                    }
                }
                if (tabArgs.length == 3) {
                    if (tabArgs[1].equalsIgnoreCase("menu")) {
                        for (Mine mine : Catch.RunningMines) {
                            if (mine.getConfigName().startsWith(tabArgs[2].toLowerCase())) {
                                tabResult.add(mine.getConfigName());
                            }
                        }
                    }
                }


            }
        }
    }

    private static void CreateMine() {
        if (commandArgs[1].equalsIgnoreCase("CreateMine")) {
            if (commandSender instanceof Player player) {
                if (commandArgs.length == 3 && commandArgs[2] != null) {
                    if (RegionCreator.PlayerMinePositionOne.containsKey(player) && RegionCreator.PlayerMinePositionTwo.containsKey(player)) {
                        if (!Catch.RunningMines.isEmpty()) {
                            for (Mine mine : Catch.RunningMines) {
                                if (mine.getConfigName().equals(commandArgs[2])) {
                                    commandSender.sendMessage(color2 + commandArgs[2] + color1 + " is already Running!");

                                }
                            }
                        }
                        Mine mine = new Mine(RegionCreator.PlayerMinePositionOne.get(player), RegionCreator.PlayerMinePositionTwo.get(player), commandArgs[2]);
                        mine.createMine();
                        commandSender.sendMessage(color1 + "you have created Mine " + color2 + commandArgs[2]);

                    } else {
                        commandSender.sendMessage(color1 + "You must select two points first do" + color2 + " /region tool" + color1 + " for a region selector");

                    }
                } else {
                    commandSender.sendMessage(color1 + "/PF Mines CreateMine " + color2 + "<MineName>");

                }
            } else {
                commandSender.sendMessage(color1 + "You must be a player to do this!");

            }
        } else {

        }
    }

    private static void RegionTool() {
        if (commandArgs[1].equalsIgnoreCase("regionTool")) {
            if (commandSender instanceof Player player) {
                ItemStack prisonregiontool = new ItemStack(Material.GOLDEN_SHOVEL, 1);
                ItemMeta meta = prisonregiontool.getItemMeta();
                meta.setDisplayName(color1 + "Region Selector");
                prisonregiontool.setItemMeta(meta);
                player.getInventory().addItem(prisonregiontool);
                player.sendMessage(color1 + "you received a Region Selector ");

            } else {
                commandSender.sendMessage(color1 + "You must be a player to do this!");

            }
        } else {

        }

    }

    private static void MineMenu() {
        if (commandArgs[1].equalsIgnoreCase("Menu")) {
            if (commandSender instanceof Player player) {

                if (commandArgs.length == 3) {
                    Mine mine = Mine.GetMineFromName(commandArgs[2]);
                    if (mine != null) {
                        player.openInventory(new MineOptionsGUI(new MGUI_1(), mine).getMenu());
                    }
                } else {
                    MGUI_1 mgui_1 = new MGUI_1();
                    player.openInventory(mgui_1.getMenu());
                }


            } else {
                commandSender.sendMessage(color1 + "You must be a player to do this!");

            }
        }
    }


    private static void MinesHelp() {
        if (commandArgs.length == 2) {
            if (commandArgs[1].equalsIgnoreCase("help")) {
                commandSender.sendMessage(color1 + "/PF mines createmine <minename>");
                commandSender.sendMessage(color1 + "/PF mines setspawn <minename>");
                commandSender.sendMessage(color1 + "/PF mines menu");
                commandSender.sendMessage(color1 + "/PF mines regiontool");
                commandSender.sendMessage(color1 + "/PF mines tp");
                commandSender.sendMessage(color1 + "/PF mines hologramRemover");

            }
        }

    }

    private static void HologramTool() {
        if (commandArgs[1].equalsIgnoreCase("hologramRemover")) {
            if (commandSender instanceof Player player) {
                ItemStack itemStack = JDItem.CustomItemStack(Material.BLAZE_ROD, color1 + "Hologram Remover", null);
                player.getInventory().addItem(itemStack);
                player.sendMessage(color1 + "you received a Hologram Remover ");
            } else {
                commandSender.sendMessage(color1 + "You must be a player to do this!");
            }

        } else {

        }

    }

    private static void SetMineSpawn() {
        if (commandArgs[1].equalsIgnoreCase("setSpawn")) {
            if (commandSender instanceof Player player) {
                if (commandArgs.length >= 3) {
                    if (Mine.GetMineFromName(commandArgs[2]) != null) {
                        Mine mine = Mine.GetMineFromName(commandArgs[2]);
                        assert mine != null;
                        mine.setSpawn(player.getLocation());
                        player.sendMessage(Presets.MainColor + "You have set " + Presets.SecondaryColor + mine.getConfigName() + Presets.MainColor + " spawn!");
                    } else {
                        commandSender.sendMessage(Presets.MainColor + commandArgs[2] + " is not a valid mine");
                    }
                }

            } else {
                commandSender.sendMessage(color1 + "You must be a player to do this!");
            }

        } else {

        }
    }

    private static void TeleportMine() {
        if (commandArgs.length >= 2 && commandArgs[1].equalsIgnoreCase("tp")) {
            if (commandSender instanceof Player) {
                Player player = ((Player) commandSender).getPlayer();
                if (commandArgs.length == 3) {
                    for (Mine mine : Catch.RunningMines) {
                        if (mine.getConfigName().equals(commandArgs[2])) {
                            if (mine.getSpawn() != null) {
                                player.teleport(mine.getSpawn());
                                player.sendMessage(color1 + "You been teleported  to mine " + color2 + commandArgs[2]);

                            } else {
                                commandSender.sendMessage(color2 + commandArgs[2] + color1 + " does not have a spawn!");
                            }
                            return;
                        }
                    }
                }
            }

            if (commandArgs.length == 4) {
                if (Bukkit.getPlayer(commandArgs[3]) != null) {
                    for (Mine mine : Catch.RunningMines) {
                        if (mine.getConfigName().equals(commandArgs[2])) {
                            if (mine.getSpawn() != null) {
                                Bukkit.getPlayer(commandArgs[3]).teleport(mine.getSpawn());
                                commandSender.sendMessage(color1 + "You have teleported " + color2 + commandArgs[3] + color1 + " to mine " + color2 + commandArgs[2]);
                                Bukkit.getPlayer(commandArgs[3]).sendMessage(color1 + "You been teleported  to mine " + color2 + commandArgs[2]);
                                return;
                            } else {
                                commandSender.sendMessage(color2 + commandArgs[2] + color1 + " does not have a spawn!");
                                return;

                            }
                        }
                    }
                    //sender.sendMessage(standOutColor + cmArgs[2] + defaultColor + " is not a valid Mine!");

                } else {
                    commandSender.sendMessage(ChatColor.GREEN + commandArgs[3] + color1 + " is not a valid player");
                    return;

                }
            }

            commandSender.sendMessage(color1 + "/PF Mines TP " + color2 + "<MineName>");
            commandSender.sendMessage(color1 + "/PF Mines TP " + color2 + "<MineName> <Player>");


        }
    }

}
