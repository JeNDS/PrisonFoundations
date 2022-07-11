package Me.JeNDS.Commands;

import JeNDS.Plugins.PluginAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.PluginAPI.Other.JDItem;
import Me.JeNDS.CustomEnchants.AutoBlock;
import Me.JeNDS.CustomEnchants.AutoSmelt;
import Me.JeNDS.Static.Presets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static Me.JeNDS.CustomEnchants.EnchantManager.AddCustomEnchantment;

public class EnchantmentCommands extends CommandManager {
    public static boolean PFEnchant() {
        if (cmd.getName().equalsIgnoreCase("PFEnchant")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    if (sender.hasPermission("PFEnchants") || sender.hasPermission("Admin")) {
                        for(Enchantment enchantment : CustomEnchant.EnchantmentList){
                            String name = enchantment.getName().replaceAll(" ", "");
                            name = name.toLowerCase();
                            if (args[0].equalsIgnoreCase(name)) {
                                enchantedBook(enchantment,player);
                                return true;
                            }
                        }
                    } else {
                        sender.sendMessage(Presets.DefaultColor + "You don't have Permissions " + Presets.StandOutColor + "PFEnchants");
                    }
                }
            }
        }

        return false;
    }


    private static void enchantedBook(Enchantment enchantment,Player player){
        ItemStack book = JDItem.CustomItemStack(Material.ENCHANTED_BOOK,null, Arrays.asList(Presets.StandOutColor2+"Drag to your Pickaxe to use!"));
        AddCustomEnchantment(enchantment, book);
        player.getInventory().addItem(book);
    }


}
