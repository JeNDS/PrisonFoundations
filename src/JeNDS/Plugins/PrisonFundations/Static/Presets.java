package JeNDS.Plugins.PrisonFundations.Static;

import JeNDS.Plugins.JeNDSAPI.Other.JTools;
import org.bukkit.ChatColor;

public class Presets {

    public static String MainColor = ChatColor.AQUA + "";
    public static String SecondaryColor = ChatColor.GREEN + "";
    public static String ThirdColor = ChatColor.WHITE + "";

    public static String ColorReplacer(String string) {
        return JTools.FormatString(string);
    }


}
