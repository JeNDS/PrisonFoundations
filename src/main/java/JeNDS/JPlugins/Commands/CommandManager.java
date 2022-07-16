package JeNDS.JPlugins.Commands;

import JeNDS.JPlugins.Files.UtilitiesFile;
import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Files.MineFile;
import JeNDS.JPlugins.Files.RankFile;
import JeNDS.JPlugins.Files.ShopsFile;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    protected static CommandSender sender;
    protected static Command cmd;
    protected static String commandLabel;
    protected static String[] args;
    protected static ChatColor defaultColor = Presets.DefaultColor;
    protected static ChatColor standOutColor = Presets.StandOutColor;
    private PF plugin = PF.getPlugin(PF.class);


    public static void LoadCommands() {
        LoadCommand("pf");
        LoadCommand("rankup");
        LoadCommand("pfenchant");
        LoadCommand("sell");
        LoadCommand("autosell");
    }
    private static void LoadCommand(String command){
        PF.getInstance().getCommand(command).setExecutor(new CommandManager());
        PF.getInstance().getCommand(command).setTabCompleter(new CommandsTabComplete());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        CommandManager.sender = sender;
        CommandManager.cmd = cmd;
        CommandManager.commandLabel = commandLabel;
        CommandManager.args = args;
        if (RankCommands.Rankup()) {
            return true;
        }
        if (ShopCommands.Sell()) {
            return true;
        }
        if (ShopCommands.AutoSell()) {
            return true;
        }
        if (EnchantmentCommands.PFEnchant()) {
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("pf") || cmd.getName().equalsIgnoreCase("PrisonFoundations")) {
            if (sender instanceof Player player) {
                if (!player.hasPermission("PF.Admin")) {
                    sender.sendMessage(defaultColor + "You Must Have permission" + standOutColor + " PF.Admin " + defaultColor + "to do this!");
                    return true;
                }
            }
            if (UtilityCommands.LoadUtility()) return true;
            if (SignCommands.SignsCommands()) return true;
            if (MineCommands.LoadMines()) return true;
            if (pfHelp()) return true;
            if (pfReload()) return true;
            sender.sendMessage(defaultColor + "/PF Help");
            return true;
        }

        return false;
    }


    public boolean pfHelp() {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(defaultColor + "/PF Mines");
                sender.sendMessage(defaultColor + "/PF Reload");
                return true;
            }
        }

        return false;
    }

    public boolean pfReload() {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (args.length == 1) {
                    MineFile.ReloadMines();
                    ShopsFile.ReloadShops();
                    RankFile.ReloadRanks();
                    UtilitiesFile.ReloadUtilities();
                    sender.sendMessage(defaultColor + "You have reloaded all files");
                    return true;
                } else {
                    if (args[1].equalsIgnoreCase("mines")) {
                        MineFile.ReloadMines();
                        sender.sendMessage(defaultColor + "You have reloaded all mines");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("shops")) {
                        ShopsFile.ReloadShops();
                        sender.sendMessage(defaultColor + "You have reloaded all shops");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("ranks")) {
                        RankFile.ReloadRanks();
                        sender.sendMessage(defaultColor + "You have reloaded all ranks");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("utilities")) {
                        UtilitiesFile.ReloadUtilities();
                        sender.sendMessage(defaultColor + "You have reloaded all utilities");
                        return true;
                    }
                }
            }

        }
        return false;
    }
}

