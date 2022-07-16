package JeNDS.JPlugins.Static;

import org.bukkit.ChatColor;

public class Presets {

    public static ChatColor DefaultColor = ChatColor.AQUA;
    public static ChatColor StandOutColor = ChatColor.GREEN;
    public static ChatColor StandOutColor2 = ChatColor.WHITE;

    public static String ColorReplacer(String string) {
        return string.replace("&", "§");
    }

}
