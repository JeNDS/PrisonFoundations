package JeNDS.Plugins.PrisonFundations.Commands;


import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandsTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("pf") || command.getName().equalsIgnoreCase("PrisonFoundations")) {
            if (!(sender instanceof Player) || sender.hasPermission("PF.Admin")) {
                if (args.length == 1) {
                    if ("mines".startsWith(args[0].toLowerCase())) {
                        result.add("mines");
                    }
                    if ("reload".startsWith(args[0].toLowerCase())) {
                        result.add("reload");
                    }
                    if ("help".startsWith(args[0].toLowerCase())) {
                        result.add("help");
                    }
                    if ("utility".startsWith(args[0].toLowerCase())) {
                        result.add("utility");
                    }
                    if ("multiplier".startsWith(args[0].toLowerCase())) {
                        result.add("multiplier");
                    }
                }

                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("mines")) {
                        if ("regiontool".startsWith(args[1].toLowerCase())) {
                            result.add("regiontool");
                        }
                        if ("hologramremover".startsWith(args[1].toLowerCase())) {
                            result.add("hologramremover");
                        }
                        if ("menu".startsWith(args[1].toLowerCase())) {
                            result.add("menu");
                        }
                        if ("createmine".startsWith(args[1].toLowerCase())) {
                            result.add("createmine");
                        }
                        if ("help".startsWith(args[1].toLowerCase())) {
                            result.add("help");
                        }
                    }
                }
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("multiplier")) {
                        if ("give".startsWith(args[1].toLowerCase())) {
                            result.add("give");
                        }
                        if ("clear".startsWith(args[1].toLowerCase())) {
                            result.add("clear");
                        }
                        if (args[1].equalsIgnoreCase("give")) {
                            if (args.length == 3) {
                                result.clear();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    if (player.getName().startsWith(args[2].toLowerCase())) {
                                        result.add(player.getName());
                                    }
                                }
                            }
                            if (args.length == 4) {
                                result.clear();
                                for (double i = 1; i != 100; i++) {
                                    if (Double.toString(i).startsWith(args[3].toLowerCase())) {
                                        result.add(Double.toString(i));
                                    }
                                }
                            }
                            if (args.length == 5) {
                                result.clear();
                                if ("permanent".startsWith(args[4].toLowerCase())) {
                                    result.add("permanent");
                                }
                                if ("0:0:0:0".startsWith(args[4].toLowerCase())) {
                                    result.add("0:0:0:0");
                                }
                            }
                        }
                        if (args[1].equalsIgnoreCase("clear")) {
                            if (args.length == 3) {
                                result.clear();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    if (player.getName().startsWith(args[2].toLowerCase())) {
                                        result.add(player.getName());
                                    }
                                }
                            }
                        }

                    }
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("utility")) {
                        if ("autoblocker".startsWith(args[1].toLowerCase())) {
                            result.add("autoblocker");
                        }
                        if ("autosmelter".startsWith(args[1].toLowerCase())) {
                            result.add("autosmelter");
                        }
                    }
                }
                //command tp mines
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("mines")) {
                        if (args.length == 2) {
                            if ("tp".startsWith(args[1].toLowerCase())) {
                                result.add("tp");
                            }
                        }
                        if (args[1].equalsIgnoreCase("tp")) {
                            if (args.length >= 3) {
                                if (args.length == 3) {
                                    if (!Catch.RunningMines.isEmpty()) {
                                        for (Mine mine : Catch.RunningMines) {
                                            if (mine.getName().startsWith(args[2].toLowerCase())) {
                                                result.add(mine.getName());
                                            }
                                        }
                                    }
                                    result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase())).toList());
                                }
                                if (args.length == 4) {
                                    if (Bukkit.getPlayer(args[2]) != null) {
                                        if (!Catch.RunningMines.isEmpty()) {
                                            for (Mine mine : Catch.RunningMines) {
                                                if (mine.getName().startsWith(args[3].toLowerCase())) {
                                                    result.add(mine.getName());
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
                //reload
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        if ("mines".startsWith(args[1].toLowerCase())) {
                            result.add("mines");
                        }
                        if ("ranks".startsWith(args[1].toLowerCase())) {
                            result.add("ranks");
                        }
                        if ("shops".startsWith(args[1].toLowerCase())) {
                            result.add("shops");
                        }
                        if ("utility".startsWith(args[1].toLowerCase())) {
                            result.add("utility");
                        }
                        if ("config".startsWith(args[1].toLowerCase())) {
                            result.add("config");
                        }
                    }
                }
                //set spawn
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("mines")) {
                        if (args.length == 2) {
                            if ("setspawn".startsWith(args[1].toLowerCase())) {
                                result.add("setspawn");
                            }
                        }
                        if (args.length == 3) {
                            if (args[1].equalsIgnoreCase("setspawn")) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (mine.getName().startsWith(args[2].toLowerCase())) {
                                        result.add(mine.getName());
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        //Sell
        if (command.getName().equalsIgnoreCase("sell")) {
            if ((sender.hasPermission("PF.Admin") || sender.hasPermission("PF.Sell.Others")) || !(sender instanceof Player)) {
                //AutoSell
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList());
                }
            }
        }
        if (command.getName().equalsIgnoreCase("rankup")) {
            if ((sender.hasPermission("PF.Admin") || sender.hasPermission("PF.Rankup.Others")) || !(sender instanceof Player)) {
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList());
                }
            }
        }
        if (command.getName().equalsIgnoreCase("pfenchant")) {
            if ((sender.hasPermission("PF.Admin") || sender.hasPermission("PF.Rankup.Others")) || !(sender instanceof Player)) {
                if (args.length == 1) {
                    for (Enchantment enchantment : CustomEnchant.EnchantmentList) {
                        String name = enchantment.getName().replaceAll(" ", "");
                        if (name.startsWith(args[0].toLowerCase())) {
                            result.add(name);
                        }
                        result.add(name);
                    }
                }
            }
        }
        return result;
    }
}
