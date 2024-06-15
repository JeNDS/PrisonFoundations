package JeNDS.Plugins.PrisonFundations.Commands.OtherCommands;


import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Ranks.Rank;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static JeNDS.Plugins.PrisonFundations.Other.Implementations.EconomyImport.Economy;


public class RankCommands extends CommandManager {
    public static void LoadCommand() {
        if (command.getName().equalsIgnoreCase("Rankup")) {
            IsCommand = true;
            if (commandSender instanceof Player player) {
                if (hasPermission(commandSender, "pf.rankup")) {
                    if (commandArgs.length == 0) {
                        Rank rank = Rank.GetPlayerRank(player);
                        if (!rank.isLastRank()) {
                            if (Economy.getBalance(player) >= rank.getRankUpCost()) {
                                Economy.withdrawPlayer(player, rank.getRankUpCost());
                                Rank.RankUpPlayer(player);
                            } else {
                                double temp = rank.getRankUpCost() - Economy.getBalance(player);
                                player.sendMessage(defaultColor + "You still need " + standOutColor + temp + defaultColor + " to rankup!");
                            }
                        } else {
                            player.sendMessage(defaultColor + "You are on the last Rank!");
                        }
                    }
                } else {
                    player.sendMessage(defaultColor + "You don't have " + standOutColor + "PF.Rankup" + defaultColor + " permission");
                }
            }
            if (commandArgs.length == 1) {
                if (Bukkit.getPlayer(commandArgs[0]) != null) {
                    Player player = Bukkit.getPlayer(commandArgs[0]);
                    Rank rank = Rank.GetPlayerRank(player);
                    if (hasPermission(commandSender, "pf.rankup.others")) {
                        if (!rank.isLastRank()) {
                            Rank.RankUpPlayer(player);
                        } else {
                            commandSender.sendMessage(Presets.SecondaryColor + commandArgs[0] + Presets.MainColor + " is on his final Rank!");
                        }
                    }
                } else {
                    commandSender.sendMessage(Presets.SecondaryColor + commandArgs[0] + Presets.MainColor + " is not a valid player!");
                }

            }
        } else IsCommand = false;

    }

    public static void LoadResults() {
        if (tabCommand.getName().equalsIgnoreCase("Rankup")) {
            if (hasPermission(tabSender, "pf.rankup.others")) {
                if (tabArgs.length == 1) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getName().toLowerCase().startsWith(tabArgs[0])) {
                            tabResult.add(player.getName());
                        }
                    }
                }

            }
        }
    }
}
