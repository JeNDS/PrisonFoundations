package JeNDS.Plugins.PrisonFundations.Commands.PFCommand;




import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.Commands.HandGrandeCommands;
import JeNDS.Plugins.PrisonFundations.Commands.PFCommand.Mines.MineCommands;
import JeNDS.Plugins.PrisonFundations.Commands.PFCommand.Mines.SignCommands;
import JeNDS.Plugins.PrisonFundations.Mines.Files.MineFile;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.Files.UtilitiesFile;
import JeNDS.Plugins.PrisonFundations.Other.Files.Config;
import JeNDS.Plugins.PrisonFundations.Ranks.File.RankFile;
import JeNDS.Plugins.PrisonFundations.Shops.File.ShopsFile;
import org.bukkit.entity.Player;

public class PFCommand extends CommandManager {
    public static void loadCommand() {
        pfCommand();
    }

    public static void loadResults() {
        if (tabCMD.getName().equalsIgnoreCase("pf") || tabCMD.getName().equalsIgnoreCase("PrisonFoundations")) {
            if (!(sender instanceof Player) || sender.hasPermission("PF.Admin")) {
                if (tbArgs.length == 1) {
                    tabResult.clear();
                    if ("mines".startsWith(tbArgs[0].toLowerCase())) {
                        tabResult.add("mines");
                    }
                    if ("reload".startsWith(tbArgs[0].toLowerCase())) {
                        tabResult.add("reload");
                    }
                    if ("help".startsWith(tbArgs[0].toLowerCase())) {
                        tabResult.add("help");
                    }
                    if ("utility".startsWith(tbArgs[0].toLowerCase())) {
                        tabResult.add("utility");
                    }
                    if ("multiplier".startsWith(tbArgs[0].toLowerCase())) {
                        tabResult.add("multiplier");
                    }
                    if ("handGrenades".startsWith(tbArgs[0].toLowerCase())) {
                        tabResult.add("handGrenades");
                    }
                }
                UtilityCommands.loadResults();
                HandGrandeCommands.loadResults();
            }
        }
    }

    private static void pfCommand() {
        if (cmd.getName().equalsIgnoreCase("pf") || cmd.getName().equalsIgnoreCase("PrisonFoundations")) {
            if (sender instanceof Player player) {
                if (!player.hasPermission("PF.Admin")) {
                    sender.sendMessage(defaultColor + "You Must Have permission" + standOutColor + " PF.Admin " + defaultColor + "to do this!");
                    return;
                }
            }
            UtilityCommands.loadCommand();
            HandGrandeCommands.loadCommand();
            if (SignCommands.SignsCommands()) return;
            if (MineCommands.LoadMines()) return;
            if (MultiplierCommands.LoadMultipliers()) return;
            Help();
            if (pfReload()) {
            }
        }

    }

    private static boolean pfReload() {
        if (cmArgs.length >= 1) {
            if (cmArgs[0].equalsIgnoreCase("reload")) {
                if (cmArgs.length == 1) {
                    MineFile.ReloadMines();
                    ShopsFile.ReloadShops();
                    RankFile.ReloadRanks();
                    UtilitiesFile.ReloadUtilities();
                    Config.LoadConfig();
                    sender.sendMessage(defaultColor + "You have reloaded all files");
                    return true;
                } else {
                    if (cmArgs[1].equalsIgnoreCase("mines")) {
                        MineFile.ReloadMines();
                        sender.sendMessage(defaultColor + "You have reloaded all mines");
                        return true;
                    }
                    if (cmArgs[1].equalsIgnoreCase("shops")) {
                        ShopsFile.ReloadShops();
                        sender.sendMessage(defaultColor + "You have reloaded all shops");
                        return true;
                    }
                    if (cmArgs[1].equalsIgnoreCase("ranks")) {
                        RankFile.ReloadRanks();
                        sender.sendMessage(defaultColor + "You have reloaded all ranks");
                        return true;
                    }
                    if (cmArgs[1].equalsIgnoreCase("utilities")) {
                        UtilitiesFile.ReloadUtilities();
                        sender.sendMessage(defaultColor + "You have reloaded all utilities");
                        return true;
                    }
                    if (cmArgs[1].equalsIgnoreCase("config")) {
                        Config.LoadConfig();
                        sender.sendMessage(defaultColor + "You have reloaded the config");
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private static void Help() {
        if (cmArgs.length >= 1) {
            if (cmArgs[0].equalsIgnoreCase("help")) {
                sender.sendMessage(defaultColor + "/PF Mines");
                sender.sendMessage(defaultColor + "/PF Utility");
                sender.sendMessage(defaultColor + "/PF Reload");
                sender.sendMessage(defaultColor + "/PF HandGrenades");
                sender.sendMessage(defaultColor + "/PF Multiplier");
            }
        }
    }
}
