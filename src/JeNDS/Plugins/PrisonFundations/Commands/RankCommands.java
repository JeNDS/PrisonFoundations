package JeNDS.Plugins.PrisonFundations.Commands;


import JeNDS.Plugins.PrisonFundations.Ranks.Rank;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static JeNDS.Plugins.PrisonFundations.Other.Implementations.EconomyImport.Economy;


public class RankCommands extends CommandManager {
    public static boolean Rankup() {
        if (CommandManager.cmd.getName().equalsIgnoreCase("Rankup")) {
            if (CommandManager.sender instanceof Player player) {
                if (CommandManager.cmArgs.length == 0) {
                    Rank rank = Rank.GetPlayerRank(player);
                    if (!rank.isLastRank()) {
                        if (Economy.getBalance(player) >= rank.getRankUpCost()) {
                            Economy.withdrawPlayer(player, rank.getRankUpCost());
                            Rank.RankUpPlayer(player);
                        } else {
                            double temp = rank.getRankUpCost() - Economy.getBalance(player);
                            player.sendMessage(CommandManager.defaultColor + "You still need " + CommandManager.standOutColor + temp + CommandManager.defaultColor + " to rankup!");
                        }
                    } else {
                        player.sendMessage(CommandManager.defaultColor + "You are on the last Rank!");
                    }
                    return true;
                }
            }
            if (CommandManager.cmArgs.length == 1) {
                if (Bukkit.getPlayer(CommandManager.cmArgs[0]) != null) {
                    Player player = Bukkit.getPlayer(CommandManager.cmArgs[0]);
                    Rank rank = Rank.GetPlayerRank(player);
                    if (CommandManager.sender.hasPermission("PF.Rankup.Others") || CommandManager.sender.hasPermission("PF.Admin")) {
                        if (!rank.isLastRank()) {
                            Rank.RankUpPlayer(player);
                        } else {
                            CommandManager.sender.sendMessage(Presets.SecondaryColor + CommandManager.cmArgs[0] + Presets.MainColor + " is on his final Rank!");
                        }
                    } else {
                        CommandManager.sender.sendMessage(Presets.MainColor + "You don't have Permissions " + Presets.SecondaryColor + "Rankup.Others");
                    }
                } else {
                    CommandManager.sender.sendMessage(Presets.SecondaryColor + CommandManager.cmArgs[0] + Presets.MainColor + " is not a valid player!");
                }
                return true;
            }
            return true;
        }

        return false;
    }
}
