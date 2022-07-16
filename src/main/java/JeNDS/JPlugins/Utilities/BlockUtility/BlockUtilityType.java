package JeNDS.JPlugins.Utilities.BlockUtility;

public enum BlockUtilityType {
    AUTOBLOCK("Auto Blocker"),AUTOSMELT("Auto Smelter");

    final String name;

    BlockUtilityType(String s) {
        name = s;
    }
    String getName(){
        return name;
    }
    public static  BlockUtilityType GetBlockUtilityTypeFromString(String name){
        for(BlockUtilityType type : BlockUtilityType.values()){
            if(type.name.contains(name)){
                return type;
            }
        }
        return null;
    }
}
