package JeNDS.Plugins.PrisonFundations.Commands;

import JeNDS.Plugins.PrisonFundations.Commands.OtherCommands.PricesCommand;
import JeNDS.Plugins.PrisonFundations.Commands.OtherCommands.RankCommands;
import JeNDS.Plugins.PrisonFundations.Commands.OtherCommands.ShopCommands;
import JeNDS.Plugins.PrisonFundations.Commands.PFCommands.PFCommand;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandManager implements TabExecutor {
    protected static CommandSender commandSender;
    protected static Command command;
    protected static String commandLabel;
    protected static String[] commandArgs;
    protected static CommandSender tabSender;
    protected static Command tabCommand;
    protected static String[] tabArgs;
    protected static List<String> tabResult = new ArrayList<>();
    protected static boolean IsCommand = false;
    protected static String color1 = Presets.MainColor;
    protected static String color2 = Presets.SecondaryColor;
    protected static String color3 = Presets.ThirdColor;

    protected static boolean hasPermission(CommandSender sender, String permission) {
        if (sender.hasPermission("pf.admin") || sender.isOp()) return true;
        return sender.hasPermission(permission);
    }

    //todo permission groups
    public void loadCommands() {
        loadCommand("pf");
        loadCommand("sell");
        loadCommand("rankup");
        loadCommand("rank");
        loadCommand("ranks");
        loadCommand("prices");
    }

    private void loadCommand(String command) {
        Objects.requireNonNull(Bukkit.getPluginCommand(command)).setTabCompleter(this);
        Objects.requireNonNull(Bukkit.getPluginCommand(command)).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        CommandManager.commandSender = sender;
        CommandManager.command = cmd;
        CommandManager.commandLabel = commandLabel;
        CommandManager.commandArgs = args;
        PFCommand.LoadCommand();
        RankCommands.LoadCommand();
        ShopCommands.LoadCommand();
        PricesCommand.LoadCommand();
        return IsCommand;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender tabSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        CommandManager.tabSender = tabSender;
        tabResult.clear();
        tabArgs = strings;
        tabCommand = command;
        PFCommand.LoadResults();
        ShopCommands.LoadResults();
        RankCommands.LoadResults();
        PricesCommand.LoadResults();
        return tabResult;
    }
}

