package JeNDS.Plugins.PrisonFundations.Commands.OtherCommands;


import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Ranks.Rank;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static JeNDS.Plugins.PrisonFundations.Other.Implementations.EconomyImport.Economy;


public class RankCommands extends CommandManager {
    public static void LoadCommand() {
        rankList();
        rankInfo();
        rankUP();
    }

    private static void rankUP() {
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
                                player.sendMessage(color1 + "You still need " + color2 + temp + color1 + " to rankup!");
                            }
                        } else {
                            player.sendMessage(color1 + "You are on the last Rank!");
                        }
                    }
                } else {
                    player.sendMessage(color1 + "You don't have " + color2 + "PF.Rankup" + color1 + " permission");
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

    private static void rankInfo() {
        if (command.getName().equalsIgnoreCase("Rank")) {
            IsCommand = true;
            if (commandSender instanceof Player player) {
                if (hasPermission(commandSender, "pf.rank")) {
                    if (commandArgs.length == 0) {
                        Rank rank = Rank.GetPlayerRank(player);
                        player.sendMessage(color1 + " ");
                        player.sendMessage(color1 + "Rank Info");
                        player.sendMessage(color2 + ChatColor.STRIKETHROUGH + "               ");
                        player.sendMessage(color3 + "Rank Name: " + rank.getPrefix());
                        if (!rank.isLastRank()) {
                            player.sendMessage(color3 + "Next Rank: " + Rank.GetPlayerNextRank(player).getPrefix());
                            player.sendMessage(color3 + "Next Rank Cost: $" + color2 + rank.getRankUpCost());
                        }
                        if (rank.isLastRank()) {
                            player.sendMessage(color3 + "You are on the last Rank");
                        }
                    }
                }
            } else IsCommand = false;
        }
    }

    private static void rankList() {
        if (command.getName().equalsIgnoreCase("Ranks")) {
            IsCommand = true;
            if (commandSender instanceof Player player) {
                if (hasPermission(commandSender, "pf.ranks")) {
                    if (commandArgs.length == 0) {
                        Rank rank = Rank.GetPlayerRank(player);
                        player.sendMessage(color1 + " ");
                        player.sendMessage(color1 + "Rank List");
                        player.sendMessage(color2 + ChatColor.STRIKETHROUGH + "               ");
                        boolean claim = true;
                        for (Rank rank1 : Catch.Ranks) {
                            if (claim) player.sendMessage(rank1.getPrefix() + color2 + " Claim");
                            else player.sendMessage(rank1.getPrefix() + color3 + " Un-Claimed");
                            if (rank == rank1) claim = false;
                        }
                    }
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
