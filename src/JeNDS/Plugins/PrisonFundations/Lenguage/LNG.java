package JeNDS.Plugins.PrisonFundations.Lenguage;


import JeNDS.Plugins.PrisonFundations.Static.Presets;

import java.util.ArrayList;
import java.util.List;

public class LNG {
    //Commands
    private static final String c1 = Presets.MainColor;
    private static final String c2 = Presets.SecondaryColor;
    private static final String c3 = Presets.ThirdColor;
    public static String NoPermissionCommandMessage = c1 + "You don't have Permission";



    //Items
    public static ArrayList<String> CustomEnchantedItemLore = new ArrayList<>(List.of(Presets.ThirdColor + "Drag to your Pickaxe to use!"));
}
