package JeNDS.Plugins.PrisonFundations.Ranks;

import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.Objects;

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

    //todo fireworks amount
    //todo prefix nextshop
    //todo prefix nextshopName

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
            ArrayList<String> rewardList = new ArrayList<>(rank.getRankRewards());
            for (String reward : rewardList) {
                for(RankRewardType rankRewardType : RankRewardType.values()){
                    String newString = reward.replace(rankRewardType.getName(), "");
                    if(reward.contains(rankRewardType.getName())){
                            if(rankRewardType.equals(RankRewardType.BROADCAST)) Bukkit.broadcastMessage(replacer(newString, player, rank));
                            if(rankRewardType.equals(RankRewardType.MESSAGE)) player.sendMessage(replacer(newString, player, rank));
                            if(rankRewardType.equals(RankRewardType.COMMAND)) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), replacer(newString, player, rank));
                            if(rankRewardType.equals(RankRewardType.FIREWORKS)){
                                Firework firework  = (Firework) Objects.requireNonNull(player.getLocation().getWorld()).spawnEntity(player.getLocation(), EntityType.FIREWORK );
                                FireworkMeta fireworkMeta = firework.getFireworkMeta();
                                FireworkEffect.Builder builder = FireworkEffect.builder();
                                builder.flicker(true);
                                builder.withColor(Color.GREEN,Color.BLUE);
                                builder.withFade(Color.LIME,Color.AQUA);
                                FireworkEffect fireworkEffect = builder.build();
                                fireworkMeta.addEffects(fireworkEffect);
                                firework.setFireworkMeta(fireworkMeta);
                            }
                    }
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

