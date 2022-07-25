package JeNDS.JPlugins.Commands;

import JeNDS.JPlugins.Commands.PFCommand.EnchantmentCommands;
import JeNDS.JPlugins.Commands.PFCommand.PFCommand;
import JeNDS.JPlugins.Static.Presets;
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
    protected static CommandSender sender;
    protected static Command cmd;
    protected static Command tabCMD;
    protected static String commandLabel;
    protected static String[] cmArgs;
    protected static String[] tbArgs;
    protected static List<String> tabResult = new ArrayList<>();
    protected static String defaultColor = Presets.MainColor;
    protected static String standOutColor = Presets.SecondaryColor;


    public void loadCommands() {
        loadCommand("pf");
        loadOldCommand("rankup");
        loadOldCommand("pfenchant");
        loadOldCommand("sell");
    }


    private  void loadCommand(String command){
        Objects.requireNonNull(Bukkit.getPluginCommand(command)).setTabCompleter(this);
        Objects.requireNonNull(Bukkit.getPluginCommand(command)).setExecutor(this);
    }
    private  void loadOldCommand(String command){
        Objects.requireNonNull(Bukkit.getPluginCommand(command)).setTabCompleter(new CommandsTabComplete());
        Objects.requireNonNull(Bukkit.getPluginCommand(command)).setExecutor(this);
    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        CommandManager.sender = sender;
        CommandManager.cmd = cmd;
        CommandManager.commandLabel = commandLabel;
        CommandManager.cmArgs = args;
        if (RankCommands.Rankup())return true;
        if (ShopCommands.Sell())return true;
        if (EnchantmentCommands.PFEnchant())return true;
        PFCommand.loadCommand();
        return false;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        tabResult.clear();
        tbArgs = strings;
        tabCMD = command;
        PFCommand.loadResults();
        return tabResult;
    }
}

