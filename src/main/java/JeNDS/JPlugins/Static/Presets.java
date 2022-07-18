package JeNDS.JPlugins.Static;

import JeNDS.JPlugins.Main.PF;
import JeNDS.Plugins.PluginAPI.Other.JTools;
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
