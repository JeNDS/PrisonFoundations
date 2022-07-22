package JeNDS.JPlugins.Mines.MineObjects;

public enum UpdateType {
    MINE_PERCENTAGE("Mine Percentage"), MINE_TIME_RESET("Mine Time Reset");

    private String name;
    UpdateType(String s) {
        name = s;
    }
    public static UpdateType GetUpdateTypeByName(String s){
        for(UpdateType singType : UpdateType.values()){
            if(singType.name.contains(s)) return singType;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
