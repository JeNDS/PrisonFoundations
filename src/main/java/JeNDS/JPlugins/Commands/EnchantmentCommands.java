package JeNDS.JPlugins.Commands;

import JeNDS.JPlugins.CustomEnchants.EnchantManager;
import JeNDS.JPlugins.Static.Presets;
import JeNDS.Plugins.PluginAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.PluginAPI.Other.JDItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnchantmentCommands extends CommandManager {
    public static boolean PFEnchant() {
        if (cmd.getName().equalsIgnoreCase("PFEnchant")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    if (sender.hasPermission("PF.Enchants") || sender.hasPermission("PF.Admin")) {
                        for(Enchantment enchantment : CustomEnchant.EnchantmentList){
                            String name = enchantment.getName().replaceAll(" ", "");
                            name = name.toLowerCase();
                            if (args[0].equalsIgnoreCase(name)) {
                                enchantedBook(enchantment,player);
                                return true;
                            }
                        }
                    } else {
                        sender.sendMessage(Presets.MainColor + "You don't have Permissions " + Presets.SecondaryColor + "PFEnchants");
                    }
                }
            }
        }

        return false;
    }


    private static void enchantedBook(Enchantment enchantment,Player player){
        ItemStack book = JDItem.CustomItemStack(Material.ENCHANTED_BOOK,null, Arrays.asList(Presets.ThirdColor +"Drag to your Pickaxe to use!"));
        EnchantManager.AddCustomEnchantment(enchantment, book);
        player.getInventory().addItem(book);
    }


}
