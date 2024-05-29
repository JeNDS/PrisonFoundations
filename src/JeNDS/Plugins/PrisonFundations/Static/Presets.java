package JeNDS.Plugins.PrisonFundations.Static;

import JeNDS.Plugins.PrisonFundations.PF;
import JeNDS.Plugins.JeNDSAPI.Other.JTools;
import org.bukkit.ChatColor;

public class Presets {

    public static String MainColor = ChatColor.AQUA + "";
    public static String SecondaryColor = ChatColor.GREEN+ "";
    public static String ThirdColor = ChatColor.WHITE+ "";

    public static String ColorReplacer(String string) {
        return JTools.FormatString(string);
    }



    public static boolean ValidVersion(double version){
        String[] splitter = PF.getInstance().getServer().getBukkitVersion().split("-");
        try {
            double result = Double.parseDouble(splitter[0]);
            return   version<= result;
        }
        catch (NumberFormatException ignore ){}
        return false;
    }

}
