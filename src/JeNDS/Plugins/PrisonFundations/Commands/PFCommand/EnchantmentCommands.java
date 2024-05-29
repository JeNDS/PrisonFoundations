package JeNDS.Plugins.PrisonFundations.Commands.PFCommand;


import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.JeNDSAPI.Other.JDItem;
import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Lenguage.LNG;
import JeNDS.Plugins.PrisonFundations.Managers.EnchantManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantmentCommands extends CommandManager {
    public static boolean PFEnchant() {
        if (cmd.getName().equalsIgnoreCase("PFEnchant")) {
            if (sender instanceof Player player) {
                if (cmArgs.length == 1) {
                    if (sender.hasPermission("PF.Enchants") || sender.hasPermission("PF.Admin")) {
                        for(Enchantment enchantment : CustomEnchant.EnchantmentList){
                            String name = enchantment.getName().replaceAll(" ", "");
                            name = name.toLowerCase();
                            if (cmArgs[0].equalsIgnoreCase(name)) {
                                enchantedBook(enchantment,player);
                                return true;
                            }
                        }
                    } else {
                        sender.sendMessage(LNG.NoPermissionCommandMessage);
                    }
                }
            }
        }

        return false;
    }


    private static void enchantedBook(Enchantment enchantment,Player player){
        ItemStack book = JDItem.CustomItemStack(Material.ENCHANTED_BOOK,null, LNG.CustomEnchantedItemLore);
        EnchantManager.AddCustomEnchantment(enchantment, book);
        player.getInventory().addItem(book);
    }


}
