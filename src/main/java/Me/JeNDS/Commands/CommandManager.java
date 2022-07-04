package Me.JeNDS.Commands;

import Me.JeNDS.Files.MineFile;
import Me.JeNDS.Files.RankFile;
import Me.JeNDS.Files.ShopsFile;
import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    protected static CommandSender sender;
    protected static Command cmd;
    protected static String commandLabel;
    protected static String[] args;
    protected static ChatColor defaultColor = Presets.DefaultColor;
    protected static ChatColor standOutColor = Presets.StandOutColor;
    private PF plugin = PF.getPlugin(PF.class);

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
        if (ShopCommands.AutoBlock()) {
            return true;
        }
        if (ShopCommands.AutoSmelt()) {
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("pf") || cmd.getName().equalsIgnoreCase("PrisonFoundations")) {
            if (sender instanceof Player player) {
                if (!player.hasPermission("PF.Admin")) {
                    sender.sendMessage(defaultColor + "You Must Have permission" + standOutColor + " PF.Admin " + defaultColor + "to do this!");
                    return true;
                }
            }
            if (Signs.SignsCommands()) {
                return true;
            }
            if (MineCommands.LoadMines()) {
                return true;
            }
            if (pfHelp()) {
                return true;
            }
            if (pfReload()) {
                return true;
            }
            sender.sendMessage(defaultColor + "/PF Help");
            return true;
        }

        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("pf") || command.getName().equalsIgnoreCase("PrisonFoundations")) {
            if (!(sender instanceof Player) || sender.hasPermission("PF.Admin")) {
                if (args.length == 1) {
                    if ("mines".startsWith(args[0].toLowerCase())) {
                        result.add("mines");
                    }
                    if ("reload".startsWith(args[0].toLowerCase())) {
                        result.add("reload");
                    }
                    if ("help".startsWith(args[0].toLowerCase())) {
                        result.add("help");
                    }
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("mines")) {
                        if ("regiontool".startsWith(args[1].toLowerCase())) {
                            result.add("regiontool");
                        }
                        if ("setspawn".startsWith(args[1].toLowerCase())) {
                            result.add("setspawn");
                        }
                        if ("menu".startsWith(args[1].toLowerCase())) {
                            result.add("menu");
                        }
                        if ("createmine".startsWith(args[1].toLowerCase())) {
                            result.add("createmine");
                        }
                        if ("help".startsWith(args[1].toLowerCase())) {
                            result.add("help");
                        }
                    }
                }
                //command tp mines
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("mines")) {
                        if (args.length == 2) {
                            if ("tp".startsWith(args[1].toLowerCase())) {
                                result.add("tp");
                            }
                        }
                        if (args[1].equalsIgnoreCase("tp")) {
                            if (args.length >= 3) {
                                if (args.length == 3) {
                                    if (!Catch.RunningMines.isEmpty()) {
                                        for (Mine mine : Catch.RunningMines) {
                                            if (mine.getName().startsWith(args[2].toLowerCase())) {
                                                result.add(mine.getName());
                                            }
                                        }
                                    }
                                    result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase())).toList());
                                }
                                if (args.length == 4) {
                                    if (Bukkit.getPlayer(args[2]) != null) {
                                        if (!Catch.RunningMines.isEmpty()) {
                                            for (Mine mine : Catch.RunningMines) {
                                                if (mine.getName().startsWith(args[3].toLowerCase())) {
                                                    result.add(mine.getName());
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
                //reload
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        if ("mines".startsWith(args[1].toLowerCase())) {
                            result.add("mines");
                        }
                        if ("ranks".startsWith(args[1].toLowerCase())) {
                            result.add("ranks");
                        }
                        if ("shops".startsWith(args[1].toLowerCase())) {
                            result.add("shops");
                        }
                    }
                }
            }
        }
        //AutoSell
        if (command.getName().equalsIgnoreCase("autosell")) {
            if ((sender.hasPermission("PF.Admin") || (sender.hasPermission("PF.AutoSell.Others")) && sender instanceof Player) || !(sender instanceof Player)) {
                //AutoSell
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList());
                }
                if (args.length == 2) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if ("on".startsWith(args[1].toLowerCase())) {
                            result.add("on");
                        }
                        if ("off".startsWith(args[1].toLowerCase())) {
                            result.add("off");
                        }
                    }
                }
            }
        }
        if (command.getName().equalsIgnoreCase("autosmelt")) {
            if ((sender.hasPermission("PF.Admin") || (sender.hasPermission("PF.AutoSmelt.Others")) && sender instanceof Player) || !(sender instanceof Player)) {
                //AutoSell
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList());
                }
                if (args.length == 2) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if ("on".startsWith(args[1].toLowerCase())) {
                            result.add("on");
                        }
                        if ("off".startsWith(args[1].toLowerCase())) {
                            result.add("off");
                        }
                    }
                }
            }
        }
        if (command.getName().equalsIgnoreCase("autoblock")) {
            if ((sender.hasPermission("PF.Admin") || sender.hasPermission("PF.AutoBlock.Others")) || !(sender instanceof Player)) {
                //AutoSell
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList());
                }
                if (args.length == 2) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if ("on".startsWith(args[1].toLowerCase())) {
                            result.add("on");
                        }
                        if ("off".startsWith(args[1].toLowerCase())) {
                            result.add("off");
                        }
                    }
                }
            }
        }
        //Sell
        if (command.getName().equalsIgnoreCase("sell")) {
            if ((sender.hasPermission("PF.Admin") || sender.hasPermission("PF.Sell.Others")) || !(sender instanceof Player)) {
                //AutoSell
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList());
                }
            }
        }
        if (command.getName().equalsIgnoreCase("rankup")) {
            if ((sender.hasPermission("PF.Admin") || sender.hasPermission("PF.Rankup.Others")) || !(sender instanceof Player)) {
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList());
                }
            }
        }


        return result;
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
                    ShopsFile shopsFile = new ShopsFile();
                    RankFile rankFile = new RankFile();
                    MineFile.LoadMines();
                    return true;
                } else {
                    if (args[1].equalsIgnoreCase("mines")) {
                        MineFile.LoadMines();
                        sender.sendMessage(defaultColor + "You have reloaded all mine and its files");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("shops")) {
                        ShopsFile shopsFile = new ShopsFile();
                        sender.sendMessage(defaultColor + "You have reloaded all shops and its files");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("ranks")) {
                        RankFile rankFile = new RankFile();
                        sender.sendMessage(defaultColor + "You have reloaded all ranks and its files");
                        return true;
                    }
                }
            }

        }
        return false;
    }
}

