package Me.JeNDS.Objects;

import Me.JeNDS.Files.RankFile;
import Me.JeNDS.Static.Catch;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Rank {
    private String rankName;
    private Integer priority;
    private String prefix;
    private double rankUpCost;
    private ArrayList<String> rankRewards = new ArrayList<>();
    private boolean isLastRank;
    public static Rank GetPlayerRank(Player player){
        Rank highestRank = null;
        for(Rank rank1 : Catch.Ranks){
            if(player.hasPermission("PF.Rank."+rank1.getRankName())){
                if(rank1.isLastRank()) return rank1;
                else {
                    if(highestRank!=null){
                        if(highestRank.getPriority()> rank1.getPriority()){
                            highestRank = rank1;
                        }
                    }
                    else {
                        highestRank = rank1;
                    }
                }
            }
        }
        return highestRank;
    }
    public static Rank GetPlayerNextRank(Player player){
        Rank highestRank = GetPlayerRank(player);
        Rank tempRank = null;
        for(Rank rank1 : Catch.Ranks){
            if(!player.hasPermission("PF.Rank."+rank1.getRankName())){
                if(highestRank.getPriority()> rank1.getPriority()) {
                    if (tempRank == null) {
                        tempRank = rank1;
                    }
                    else {
                        if(tempRank.getPriority() < rank1.getPriority()){
                            tempRank = rank1;
                        }
                    }
                }
            }
        }
        if(tempRank!=null){
            return tempRank;
        }
        else {
            return highestRank;
        }
    }

    public static void RankUpPlayer(Player player){
        RankFile.RunPlayerCommands(player);
    }

    public Rank(String rankName,Integer priority, String prefix, boolean isLastRank, double rankUpCost,ArrayList<String> rankRewards) {
        this.rankRewards = rankRewards;
        this.rankName = rankName;
        this.priority = priority;
        this.prefix = prefix;
        this.isLastRank = isLastRank;
        this.rankUpCost = rankUpCost;
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

