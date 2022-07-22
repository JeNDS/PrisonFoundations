package JeNDS.JPlugins.Commands;

import JeNDS.JPlugins.Managers.CommandManager;
import JeNDS.JPlugins.Ranks.Rank;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static JeNDS.JPlugins.Other.Implementations.EconomyImport.Economy;

public class RankCommands extends CommandManager {
    public static boolean Rankup() {
        if (cmd.getName().equalsIgnoreCase("Rankup")) {
            if (sender instanceof Player player) {
                if (args.length == 0) {
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
                    return true;
                }
            }
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player player = Bukkit.getPlayer(args[0]);
                    Rank rank = Rank.GetPlayerRank(player);
                    if (sender.hasPermission("PF.Rankup.Others") || sender.hasPermission("PF.Admin")) {
                        if (!rank.isLastRank()) {
                            Rank.RankUpPlayer(player);
                        } else {
                            sender.sendMessage(Presets.SecondaryColor + args[0] + Presets.MainColor + " is on his final Rank!");
                        }
                    } else {
                        sender.sendMessage(Presets.MainColor + "You don't have Permissions " + Presets.SecondaryColor + "Rankup.Others");
                    }
                } else {
                    sender.sendMessage(Presets.SecondaryColor + args[0] + Presets.MainColor + " is not a valid player!");
                }
                return true;
            }
            return true;
        }

        return false;
    }
}
