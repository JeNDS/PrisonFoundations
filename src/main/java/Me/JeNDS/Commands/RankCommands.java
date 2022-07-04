package Me.JeNDS.Commands;

import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.Rank;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RankCommands extends CommandManager {
    public static boolean Rankup() {
        if (cmd.getName().equalsIgnoreCase("Rankup")) {
            if (sender instanceof Player player) {
                if (args.length == 0) {
                    Rank rank = Rank.GetPlayerRank(player);
                    if (!rank.isLastRank()) {
                        if (PF.economy.getBalance(player) >= rank.getRankUpCost()) {
                            PF.economy.withdrawPlayer(player, rank.getRankUpCost());
                            Rank.RankUpPlayer(player);
                        } else {
                            double temp = rank.getRankUpCost() - PF.economy.getBalance(player);
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
                            sender.sendMessage(Presets.StandOutColor + args[0] + Presets.DefaultColor + " is on his final Rank!");
                        }
                    } else {
                        sender.sendMessage(Presets.DefaultColor + "You don't have Permissions " + Presets.StandOutColor + "PF.Rankup.Others");
                    }
                } else {
                    sender.sendMessage(Presets.StandOutColor + args[0] + Presets.DefaultColor + " is not a valid player!");
                }
                return true;
            }
            return true;
        }

        return false;
    }
}
