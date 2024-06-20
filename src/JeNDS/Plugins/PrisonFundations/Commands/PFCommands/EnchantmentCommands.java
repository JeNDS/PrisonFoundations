package JeNDS.Plugins.PrisonFundations.Commands.PFCommands;


import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.JeNDSAPI.Other.JDItem;
import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Lenguage.LNG;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchant.AddCustomEnchantment;

public class EnchantmentCommands extends CommandManager {


    //todo add levels
    protected static void LoadCommand() {
        if (commandArgs.length >= 1 && commandArgs[0].equalsIgnoreCase("enchant")) {
            if (commandArgs.length > 1) {
                if (hasPermission(commandSender, "pf.enchants")) {
                    for (Enchantment enchantment : CustomEnchant.EnchantmentList) {
                        String name = enchantment.getName().replaceAll(" ", "");
                        name = name.toLowerCase();
                        if (commandArgs[1].equalsIgnoreCase(name)) {
                            if (commandArgs.length == 3) {
                                if (Bukkit.getPlayer(tabArgs[2]) != null) {
                                    enchantedBook(enchantment, Bukkit.getPlayer(tabArgs[2]));
                                } else {
                                    commandSender.sendMessage(color2 + tabArgs[2] + color1 + " is not a valid Player!");
                                }
                            }
                            if (commandArgs.length == 2) {
                                if (commandSender instanceof Player player) {
                                    enchantedBook(enchantment, player);
                                }

                            }
                        }
                    }
                } else {
                    commandSender.sendMessage(LNG.NoPermissionCommandMessage);
                }

            }
        }
    }

    private static void enchantedBook(Enchantment enchantment, Player player) {
        ItemStack book = JDItem.CustomItemStack(Material.ENCHANTED_BOOK, null, List.of(ChatColor.WHITE + "Drag and drop on your item"));
        AddCustomEnchantment(enchantment, book);
        player.getInventory().addItem(book);
    }

    protected static void LoadResults() {
        if (tabArgs.length >= 2) {
            if (tabArgs[0].equalsIgnoreCase("enchant")) {
                tabResult.clear();
                if (hasPermission(tabSender, "pf.enchants")) {
                    if (tabArgs.length == 2) {
                        for (Enchantment enchantment : CustomEnchant.EnchantmentList) {
                            String name = enchantment.getName().replaceAll(" ", "").toLowerCase();
                            if (name.startsWith(tabArgs[1].toLowerCase())) {
                                tabResult.add(name);
                            }

                        }
                    }
                    if (tabArgs.length == 3) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            tabResult.add(player.getName());
                        }
                    }

                }
            }
        }
    }


}
