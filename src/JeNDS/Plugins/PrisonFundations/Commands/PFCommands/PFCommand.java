package JeNDS.Plugins.PrisonFundations.Commands.PFCommands;


import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Mines.Files.MineFile;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.Files.UtilitiesFile;
import JeNDS.Plugins.PrisonFundations.Other.Files.Config;
import JeNDS.Plugins.PrisonFundations.Ranks.File.RankFile;
import JeNDS.Plugins.PrisonFundations.Shops.File.ShopsFile;

public class PFCommand extends CommandManager {
    public static void LoadCommand() {
        if (command.getName().equalsIgnoreCase("pf") || command.getName().equalsIgnoreCase("PrisonFoundations")) {
            IsCommand = true;
            EnchantmentCommands.LoadCommand();
            HandGrandeCommands.LoadCommand();
            MineCommands.LoadCommand();
            MultiplierCommands.LoadCommand();
            SignCommands.LoadCommand();
            UtilityCommands.LoadCommand();
            pfReload();
            //Help();
        } else IsCommand = false;
    }

    public static void LoadResults() {
        if (tabCommand.getName().equalsIgnoreCase("pf") || tabCommand.getName().equalsIgnoreCase("PrisonFoundations")) {
            if (tabArgs.length == 1) {
                tabResult.clear();
                if (hasPermission(tabSender, "pf.mines"))
                    if ("Mines".toLowerCase().startsWith(tabArgs[0].toLowerCase())) {
                        tabResult.add("Mines");
                    }
                if (hasPermission(tabSender, ""))
                    if ("Reload".toLowerCase().startsWith(tabArgs[0].toLowerCase())) {
                        tabResult.add("Reload");
                    }
                if (hasPermission(tabSender, "pf.utilities"))
                    if ("Utility".toLowerCase().startsWith(tabArgs[0].toLowerCase())) {
                        tabResult.add("Utility");
                    }
                if (hasPermission(tabSender, "pf.multipliers"))
                    if ("Multiplier".toLowerCase().startsWith(tabArgs[0].toLowerCase())) {
                        tabResult.add("Multiplier");
                    }
                if (hasPermission(tabSender, "pf.handgrenades"))
                    if ("HandGrenades".toLowerCase().startsWith(tabArgs[0].toLowerCase())) {
                        tabResult.add("HandGrenades");
                    }
                if (hasPermission(tabSender, "pf.enchants"))
                    if ("Enchant".toLowerCase().startsWith(tabArgs[0].toLowerCase())) {
                        tabResult.add("Enchant");
                    }
                if (hasPermission(tabSender, "pf.signs"))
                    if ("Signs".toLowerCase().startsWith(tabArgs[0].toLowerCase())) {
                        tabResult.add("Signs");
                    }
            }
            EnchantmentCommands.LoadResults();
            HandGrandeCommands.LoadResults();
            MineCommands.LoadResults();
            MultiplierCommands.LoadResults();
            SignCommands.LoadResults();
            UtilityCommands.LoadResults();

        }
    }


    private static void pfReload() {
        if (commandArgs.length >= 1) {
            if (commandArgs[0].equalsIgnoreCase("reload")) {
                if (commandArgs.length == 1) {
                    MineFile.ReloadMines();
                    ShopsFile.ReloadShops();
                    RankFile.ReloadRanks();
                    UtilitiesFile.ReloadUtilities();
                    Config.LoadConfig();
                    commandSender.sendMessage(defaultColor + "You have reloaded all files");

                } else {
                    if (commandArgs[1].equalsIgnoreCase("mines")) {
                        MineFile.ReloadMines();
                        commandSender.sendMessage(defaultColor + "You have reloaded all mines");

                    }
                    if (commandArgs[1].equalsIgnoreCase("shops")) {
                        ShopsFile.ReloadShops();
                        commandSender.sendMessage(defaultColor + "You have reloaded all shops");

                    }
                    if (commandArgs[1].equalsIgnoreCase("ranks")) {
                        RankFile.ReloadRanks();
                        commandSender.sendMessage(defaultColor + "You have reloaded all ranks");

                    }
                    if (commandArgs[1].equalsIgnoreCase("utilities")) {
                        UtilitiesFile.ReloadUtilities();
                        commandSender.sendMessage(defaultColor + "You have reloaded all utilities");

                    }
                    if (commandArgs[1].equalsIgnoreCase("config")) {
                        Config.LoadConfig();
                        commandSender.sendMessage(defaultColor + "You have reloaded the config");

                    }
                }
            }

        }

    }

    private static void Help() {
        if (commandArgs.length >= 1) {
            if (commandArgs[0].equalsIgnoreCase("help")) {
                commandSender.sendMessage(defaultColor + "/PF Mines");
                commandSender.sendMessage(defaultColor + "/PF Utility");
                commandSender.sendMessage(defaultColor + "/PF Reload");
                commandSender.sendMessage(defaultColor + "/PF HandGrenades");
                commandSender.sendMessage(defaultColor + "/PF Multiplier");
                commandSender.sendMessage(defaultColor + "/PF Enchant");
            }
        }
    }
}
