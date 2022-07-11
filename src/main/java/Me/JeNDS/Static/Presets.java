package Me.JeNDS.Static;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Presets {

    public static ChatColor DefaultColor = ChatColor.AQUA;
    public static ChatColor StandOutColor = ChatColor.GREEN;
    public static ChatColor StandOutColor2 = ChatColor.WHITE;

    public static String ColorReplacer(String string) {
        return string.replace("&", "§");
    }

}
