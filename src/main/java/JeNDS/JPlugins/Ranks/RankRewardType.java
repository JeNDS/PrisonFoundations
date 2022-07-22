package JeNDS.JPlugins.Ranks;

public enum RankRewardType {
    BROADCAST("[Broadcast]"),COMMAND("[Command]"),MESSAGE("[Message]"),FIREWORKS("[FireWorks]"),
    ;

    private final String name;
    RankRewardType(String s) {
        this.name = s;
    }

    public static RankRewardType GetFromString(String s){
        for(RankRewardType rankRewardsType : RankRewardType.values()){
            if(rankRewardsType.name.contains(s)){
                return rankRewardsType;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
