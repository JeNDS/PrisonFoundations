package JeNDS.JPlugins.Objects;

import JeNDS.JPlugins.Static.Catch;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Rank {
    private final String rankName;
    private final Integer priority;
    private final String prefix;
    private final double rankUpCost;
    private ArrayList<String> rankRewards = new ArrayList<>();
    private final boolean isLastRank;

    public Rank(String rankName, Integer priority, String prefix, boolean isLastRank, double rankUpCost, ArrayList<String> rankRewards) {
        this.rankRewards = rankRewards;
        this.rankName = rankName;
        this.priority = priority;
        this.prefix = prefix;
        this.isLastRank = isLastRank;
        this.rankUpCost = rankUpCost;
    }

    //todo presets for sound effects and other player rankupeffects

    public static Rank GetPlayerRank(Player player) {
        Rank highestRank = null;
        for (Rank rank1 : Catch.Ranks) {
            if (player.hasPermission("PF.Ranks." + rank1.getRankName())) {
                if (rank1.isLastRank()) return rank1;
                else {
                    if (highestRank != null) {
                        if (highestRank.getPriority() > rank1.getPriority()) {
                            highestRank = rank1;
                        }
                    } else {
                        highestRank = rank1;
                    }
                }
            }
        }
        return highestRank;
    }

    public static Rank GetPlayerNextRank(Player player) {
        Rank highestRank = GetPlayerRank(player);
        Rank tempRank = null;
        for (Rank rank1 : Catch.Ranks) {
            if (!player.hasPermission("PF.Ranks." + rank1.getRankName())) {
                if (highestRank.getPriority() > rank1.getPriority()) {
                    if (tempRank == null) {
                        tempRank = rank1;
                    } else {
                        if (tempRank.getPriority() < rank1.getPriority()) {
                            tempRank = rank1;
                        }
                    }
                }
            }
        }
        if (tempRank != null) {
            return tempRank;
        } else {
            return highestRank;
        }
    }

    private static void runPlayerCommands(Player player) {
        Rank rank = Rank.GetPlayerRank(player);
        if (!rank.getRankRewards().isEmpty()) {
            ArrayList<String> rs = new ArrayList<>(rank.getRankRewards());
            for (String s : rs) {
                boolean command = false;
                boolean message = false;
                boolean broadcast = false;
                String[] splits = s.split("\\s");
                String thaNew = "";
                for (String sp : splits) {
                    boolean found = false;
                    if (sp.contains("[Command]")) {
                        found = true;
                        command = true;
                        sp = sp.replace("[Command]", "");
                    }
                    if (sp.contains("[Message]")) {
                        found = true;
                        message = true;
                        sp = sp.replace("[Message]", "");
                    }
                    if (sp.contains("[Broadcast]")) {
                        found = true;
                        broadcast = true;
                        sp = sp.replace("[Broadcast]", "");
                    }
                    if (thaNew.length() > 1) {
                        if (found) {
                            thaNew = thaNew + "" + sp;
                        } else {
                            thaNew = thaNew + " " + sp;
                        }
                    } else {
                        thaNew = sp;
                    }
                }
                if (broadcast) {
                    Bukkit.broadcastMessage(replacer(thaNew, player, rank));
                }
                if (message) {
                    player.sendMessage(replacer(thaNew, player, rank));
                }
                if (command) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), replacer(thaNew, player, rank));
                }
            }
        }

    }

    public static void RankUpPlayer(Player player) {
        runPlayerCommands(player);
    }

    private static String replacer(String replacer, Player player, Rank rank) {

        String temp = "";
        String[] split = replacer.split("\\s");
        for (String sp : split) {
            if (sp.contains("%player%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%player%", player.getName());
                } else {
                    temp = sp.replaceAll("%player%", player.getName());
                }
            } else if (sp.contains("%nextRank%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%nextRank%", Rank.GetPlayerNextRank(player).getRankName());
                } else {
                    temp = sp.replaceAll("%nextRank%", Rank.GetPlayerNextRank(player).getRankName());
                }
            } else if (sp.contains("%rank%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%rank%", rank.getRankName());

                } else {
                    temp = sp.replaceAll("%rank%", rank.getRankName());
                }
            } else if (sp.contains("%rankPrefix%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%rankPrefix%", Presets.ColorReplacer(rank.getPrefix()));
                } else {
                    temp = sp.replaceAll("%rankPrefix%", Presets.ColorReplacer(rank.getPrefix()));
                }
            } else if (sp.contains("%nextRankPrefix")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%nextRankPrefix%", Presets.ColorReplacer(Rank.GetPlayerNextRank(player).getPrefix()));
                } else {
                    temp = sp.replaceAll("%nextRankPrefix%", Presets.ColorReplacer(Rank.GetPlayerNextRank(player).getPrefix()));
                }
            } else {
                if (temp.length() > 1) {
                    temp = temp + " " + sp;
                } else {
                    temp = sp;
                }
            }
        }
        return Presets.ColorReplacer(temp);
    }

    public String getRankName() {
        return rankName;
    }

    public Integer getPriority() {
        return priority;
    }


    public String getPrefix() {
        return prefix;
    }

    public double getRankUpCost() {
        return rankUpCost;
    }

    public ArrayList<String> getRankRewards() {
        return rankRewards;
    }

    public boolean isLastRank() {
        return isLastRank;
    }
}

