package JeNDS.JPlugins.Static;

import JeNDS.JPlugins.Main.PF;
import org.bukkit.ChatColor;

public class Presets {

    public static ChatColor DefaultColor = ChatColor.AQUA;
    public static ChatColor StandOutColor = ChatColor.GREEN;
    public static ChatColor StandOutColor2 = ChatColor.WHITE;

    public static String ColorReplacer(String string) {
        return string.replace("&", "§");
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
