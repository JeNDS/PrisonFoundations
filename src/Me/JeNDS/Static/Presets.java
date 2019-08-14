package Me.JeNDS.Static;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Presets {

    public static ChatColor DefaultColor;
    public static ChatColor StandOutColor;
    public static String ClickTo;

    public static ItemStack EmtpySpace;
    public static ItemStack ResetMineButton;
    public static ItemStack MineChangeNameButton;
    public static ItemStack BlockTypesButton;
    public static ItemStack AddButton;
    public static ItemStack RemoveButton;
    public static ItemStack DeleteButton;
    public static ItemStack ConfimButton;
    public static ItemStack ResetManageButton;
    public static ItemStack TimeResetButton;
    public static ItemStack PercentResetButton;
    public static ItemStack BackButton;
    public static ItemStack NextPageButton;


    public static ItemStack itemMaker(Material material, String Name, String Lore) {
        ItemStack itemStack = new ItemStack(Material.AIR, 1);

        if (material != null) {
            itemStack.setType(material);
        }


        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            if (Name != null) {
                meta.setDisplayName(DefaultColor + Name);
            }

            if (Lore != null) {
                List<String> lore = new ArrayList<>();
                String lores[] = Lore.split("/n");
                for (String lo : lores) {
                    lore.add(StandOutColor + lo);
                }
                meta.setLore(lore);
            }
            if(meta.getItemFlags()!= null){
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

    public static String ColorReplacer(String string) {
        return string.replace("&", "§");
    }

    public static void LoadPresets() {
        DefaultColor = ChatColor.AQUA;
        StandOutColor = ChatColor.GREEN;
        ClickTo = StandOutColor + "Click to " + "/n ";
        EmtpySpace = itemMaker(Material.AIR, null, null);

        ResetMineButton = itemMaker(Material.BARRIER, "Reset Mine", ClickTo + "Reset Mine");

        MineChangeNameButton = itemMaker(Material.PAPER, "Rename", ClickTo + "Change Name");
        BlockTypesButton = itemMaker(Material.DIAMOND_ORE, "Block Types", ClickTo + "Change Types");
        AddButton = itemMaker(Material.EMERALD_BLOCK, "Add", ClickTo + "Add");
        RemoveButton = itemMaker(Material.REDSTONE_BLOCK, "Remove", ClickTo + "Remove");
        DeleteButton = itemMaker(Material.REDSTONE_BLOCK, "Delete", ClickTo + "Delete");
        ConfimButton = itemMaker(Material.EMERALD_BLOCK, "Confirm", ClickTo + "Confirm");
        ResetManageButton = itemMaker(Material.CLOCK, "Manage Reset", ClickTo + "Manage Reset");
        TimeResetButton = itemMaker(Material.CLOCK, "Time Reset", ClickTo + "Change Time");
        PercentResetButton = itemMaker(Material.DIAMOND_PICKAXE, "Percentage Reset", ClickTo + "Change Percentage");
        BackButton = itemMaker(Material.REDSTONE_BLOCK, "Back", ClickTo + "Go Back");
        NextPageButton = itemMaker(Material.EMERALD_BLOCK, "Next Page", ClickTo + "Go to Next Page");


    }
}
