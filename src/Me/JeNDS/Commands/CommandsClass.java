package Me.JeNDS.Commands;

import Me.JeNDS.Files.MineFile;
import Me.JeNDS.Files.RankFile;
import Me.JeNDS.Files.ShopsFile;
import Me.JeNDS.MainFolder.Main;
import Me.JeNDS.Objects.Mine;
import Me.JeNDS.PlayerMenus.Inventories.MinesMenu;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Me.JeNDS.Reagions.RegionCreator.PlayerMinePositionOne;
import static Me.JeNDS.Reagions.RegionCreator.PlayerMinePositionTwo;

public class CommandsClass implements CommandExecutor, TabCompleter {
    protected static CommandSender sender;
    protected static Command cmd;
    protected static String commandLabel;
    protected static String[] args;
    protected static ChatColor defaultColor = Presets.DefaultColor;
    protected static ChatColor standOutColor = Presets.StandOutColor;
    private Main plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        this.sender = sender;
        this.cmd = cmd;
        this.commandLabel = commandLabel;
        this.args = args;
        if (Ranks.Rankup()) {
            return true;
        }
        if (ShopsCMD.Sell()) {
            return true;
        }
        if (ShopsCMD.AutoSell()) {
            return true;
        }
        if (ShopsCMD.AutoBlock()) {
            return true;
        }
        if (ShopsCMD.AutoSmelt()) {
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("pf") || cmd.getName().equalsIgnoreCase("PrisonFoundations")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("PF.Admin")) {
                    sender.sendMessage(defaultColor + "You Must Have permission" + standOutColor + " PF.Admin " + defaultColor + "to do this!");
                    return true;
                }
            }
            if (Signs.SignsCommands()) {
                return true;
            }
            if (Mines.LoadMines()) {
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
            if ((sender instanceof Player && sender.hasPermission("PF.Admin")) || !(sender instanceof Player)) {
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
                                    result.addAll(Bukkit.getOnlinePlayers().stream()
                                            .filter(player -> player.getName().toLowerCase().startsWith(args[2].toLowerCase()))
                                            .map(Player::getName).collect(Collectors.toList()));
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
                    result.addAll(Bukkit.getOnlinePlayers().stream()
                            .filter(player -> player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                            .map(Player::getName).collect(Collectors.toList()));
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
                    result.addAll(Bukkit.getOnlinePlayers().stream()
                            .filter(player -> player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                            .map(Player::getName).collect(Collectors.toList()));
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
            if (((sender.hasPermission("PF.Admin") || sender.hasPermission("PF.AutoBlock.Others")) && sender instanceof Player) || !(sender instanceof Player)) {
                //AutoSell
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream()
                            .filter(player -> player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                            .map(Player::getName).collect(Collectors.toList()));
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
            if (((sender.hasPermission("PF.Admin") || sender.hasPermission("PF.Sell.Others")) && sender instanceof Player) || !(sender instanceof Player)) {
                //AutoSell
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream()
                            .filter(player -> player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                            .map(Player::getName).collect(Collectors.toList()));
                }
            }
        }
        if (command.getName().equalsIgnoreCase("rankup")) {
            if (((sender.hasPermission("PF.Admin") || sender.hasPermission("PF.Rankup.Others")) && sender instanceof Player) || !(sender instanceof Player)) {
                if (args.length == 1) {
                    result.addAll(Bukkit.getOnlinePlayers().stream()
                            .filter(player -> player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                            .map(Player::getName).collect(Collectors.toList()));
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
                    if (args.length >= 2) {
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

        }
        return false;
    }
}

class Mines extends CommandsClass {

    public static boolean LoadMines() {
        if (args.length >= 1 && args[0].equalsIgnoreCase("Mines")) {
            if (args.length > 1) {
                if (CreateMine()) {
                    return true;
                }
                if (RegionTool()) {
                    return true;
                }
                if (SetMineSpawn()) {
                    return true;
                }
                if (TeleportMine()) {
                    return true;
                }
                if (MineMenu()) {
                    return true;
                }
                if (MinesHelp()) {
                    return true;
                }
            }
            sender.sendMessage(defaultColor + "/PF Mines Help");
            return true;

        }
        return false;
    }

    private static boolean CreateMine() {
        if (args[1].equalsIgnoreCase("CreateMine")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 3 && args[2] != null) {
                    if (PlayerMinePositionOne.containsKey(player) && PlayerMinePositionTwo.containsKey(player)) {
                        if (!Catch.RunningMines.isEmpty()) {
                            for (Mine mine : Catch.RunningMines) {
                                if (mine.getName().equals(args[2])) {
                                    sender.sendMessage(standOutColor + args[2] + defaultColor + " is already Running!");
                                    return true;
                                }
                            }
                        }
                        Mine mine = new Mine(PlayerMinePositionOne.get(player), PlayerMinePositionTwo.get(player), args[2]);
                        mine.CreateMine();
                        sender.sendMessage(defaultColor + "you have created Mine " + standOutColor + args[2]);
                        return true;
                    } else {
                        sender.sendMessage(defaultColor + "You must select two points first do" + standOutColor + " /region tool" + defaultColor + " for a region selector");
                        return true;
                    }
                } else {
                    sender.sendMessage(defaultColor + "/PF Mines CreateMine " + standOutColor + "<MineName>");
                    return true;
                }
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
                return true;
            }
        } else {
            return false;
        }
    }

    private static boolean RegionTool() {
        if (args[1].equalsIgnoreCase("regionTool")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ItemStack prisonregiontool = new ItemStack(Material.GOLDEN_SHOVEL, 1);
                ItemMeta meta = prisonregiontool.getItemMeta();
                meta.setDisplayName(defaultColor + "Region Selector");
                prisonregiontool.setItemMeta(meta);
                player.getInventory().addItem(prisonregiontool);
                player.sendMessage(defaultColor + "you received a Region Selector ");
                return true;
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
                return true;
            }
        } else {
            return false;
        }

    }

    private static boolean SetMineSpawn() {
        if (args[1].equalsIgnoreCase("setSpawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                for (Mine mine : Catch.RunningMines) {
                    if (Mine.playerCloseToMine(player, mine)) {
                        mine.setSpawn(player.getLocation());
                        player.sendMessage(Presets.DefaultColor + "You have set " + Presets.StandOutColor + mine.getName() + Presets.DefaultColor + " spawn!");
                        return true;
                    }
                }
                sender.sendMessage(Presets.DefaultColor + "No Mine found close to you!");
                return true;

            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
                return true;
            }
        } else {
            return false;
        }
    }

    private static boolean TeleportMine() {
        if (args.length >= 2 && args[1].equalsIgnoreCase("tp")) {
            if (sender instanceof Player) {
                Player player = ((Player) sender).getPlayer();
                if (args.length == 4) {
                    if (Bukkit.getPlayer(args[2]) != null) {
                        for (Mine mine : Catch.RunningMines) {
                            if (mine.getName().equals(args[3])) {
                                if (mine.getSpawn() != null) {
                                    Bukkit.getPlayer(args[2]).teleport(mine.getSpawn());
                                    Bukkit.getPlayer(args[2]).sendMessage(defaultColor + "You been teleported  to mine " + standOutColor + args[2]);
                                    sender.sendMessage(defaultColor + "You have teleported " + standOutColor + args[2] + defaultColor + " to mine " + standOutColor + args[3]);
                                    return true;
                                } else {
                                    sender.sendMessage(standOutColor + args[3] + defaultColor + " does not have a spawn!");
                                    return true;
                                }
                            }
                        }
                        sender.sendMessage(standOutColor + args[3] + defaultColor + " is not a valid Mine!");
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.GREEN + args[2] + defaultColor + " is not a valid player");
                        return true;
                    }
                } else {
                    if (args.length == 3) {
                        for (Mine mine : Catch.RunningMines) {
                            if (mine.getName().equals(args[2])) {
                                if (mine.getSpawn() != null) {
                                    player.teleport(mine.getSpawn());
                                    sender.sendMessage(defaultColor + "You been teleported  to mine " + standOutColor + args[2]);
                                    return true;
                                } else {
                                    sender.sendMessage(standOutColor + args[2] + defaultColor + " does not have a spawn!");
                                    return true;
                                }
                            }
                        }
                        sender.sendMessage(standOutColor + args[2] + defaultColor + " is not a valid Mine!");
                        return true;
                    } else {
                        sender.sendMessage(defaultColor + "/PF Mines TP " + standOutColor + "<MineName>");
                        sender.sendMessage(defaultColor + "/PF Mines TP " + standOutColor + "<Player> <MineName>");
                        return true;
                    }


                }

            } else {
                if (args.length == 4) {
                    if (Bukkit.getPlayer(args[2]) != null) {
                        for (Mine mine : Catch.RunningMines) {
                            if (mine.getName().equals(args[3])) {
                                if (mine.getSpawn() != null) {
                                    Bukkit.getPlayer(args[2]).teleport(mine.getSpawn());
                                    sender.sendMessage(defaultColor + "You have teleported " + standOutColor + args[2] + defaultColor + " to mine " + standOutColor + args[3]);
                                    Bukkit.getPlayer(args[2]).sendMessage(defaultColor + "You been teleported  to mine " + standOutColor + args[2]);
                                    return true;
                                } else {
                                    sender.sendMessage(standOutColor + args[3] + defaultColor + " does not have a spawn!");
                                    return true;
                                }
                            }
                        }
                        sender.sendMessage(standOutColor + args[3] + defaultColor + " is not a valid Mine!");
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.GREEN + args[2] + defaultColor + " is not a valid player");
                        return true;
                    }
                } else {
                    sender.sendMessage(defaultColor + "/PF Mines TP " + standOutColor + "<MineName>");
                    sender.sendMessage(defaultColor + "/PF Mines TP " + standOutColor + "<Player> <MineName>");
                    return true;
                }
            }
        } else return false;
    }

    private static boolean MineMenu() {
        if (args[1].equalsIgnoreCase("Menu")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                for (Inventory inventory : MinesMenu.GetMainMenuPages()) {
                    player.openInventory(inventory);
                    break;
                }
                return true;
            } else {
                sender.sendMessage(defaultColor + "You must be a player to do this!");
                return true;
            }
        }
        return false;

    }

    private static boolean MinesHelp() {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("help")) {
                sender.sendMessage(defaultColor + "/PF mines createmine <minename>");
                sender.sendMessage(defaultColor + "/PF mines menu");
                sender.sendMessage(defaultColor + "/PF mines regiontool");
                sender.sendMessage(defaultColor + "/PF mines tp");
                return true;
            }
        }
        return false;
    }
}

class Ranks extends CommandsClass {
    public static boolean Rankup() {
        if (cmd.getName().equalsIgnoreCase("Rankup")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    if (!RankFile.isLastRank(RankFile.FindPlayerRank(player))) {
                        if (Main.economy.getBalance(player) >= RankFile.getPrice(RankFile.FindPlayerRank(player))) {
                            Main.economy.withdrawPlayer(player, RankFile.getPrice(RankFile.FindPlayerRank(player)));
                            RankFile.PlayerRewardsCommands(player);
                            return true;
                        } else {
                            double temp = RankFile.getPrice(RankFile.FindPlayerRank(player)) - Main.economy.getBalance(player);
                            player.sendMessage(defaultColor + "You still need " + standOutColor + temp + defaultColor + " to rankup!");
                            return true;
                        }
                    } else {
                        player.sendMessage(defaultColor + "You are on the last Rank!");
                        return true;
                    }
                }
                if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (sender.hasPermission("PF.Rankup.Others") || sender.hasPermission("PF.Admin")) {
                            if (!RankFile.isLastRank(RankFile.FindPlayerRank(Bukkit.getPlayer(args[0])))) {
                                RankFile.PlayerRewardsCommands(Bukkit.getPlayer(args[0]));
                                return true;
                            } else {
                                sender.sendMessage(Presets.StandOutColor + args[0] + Presets.DefaultColor + " is on his final Rank!");
                                return true;
                            }
                        } else {
                            sender.sendMessage(Presets.DefaultColor + "You dont have Permissions " + Presets.StandOutColor + "PF.Rankup.Others");
                            return true;
                        }
                    } else {
                        sender.sendMessage(Presets.StandOutColor + args[0] + Presets.DefaultColor + " is not a valid player!");
                        return true;
                    }
                }
            } else {
                if (args.length == 1) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (!RankFile.isLastRank(RankFile.FindPlayerRank(Bukkit.getPlayer(args[0])))) {
                            RankFile.PlayerRewardsCommands(Bukkit.getPlayer(args[0]));
                            return true;
                        } else {
                            sender.sendMessage(Presets.StandOutColor + args[0] + Presets.DefaultColor + " is on his final Rank!");
                            return true;
                        }
                    } else {
                        sender.sendMessage(Presets.StandOutColor + args[0] + Presets.DefaultColor + " is not a valid player!");
                        return true;
                    }
                }
            }
            return true;
        }

        return false;
    }
}

class ShopsCMD extends CommandsClass {
    public static boolean Sell() {
        if (cmd.getName().equalsIgnoreCase("Sell")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    if (player.hasPermission("PF.Sell") || sender.hasPermission("PF.Admin") || sender.hasPermission("PF.SellOthers")) {
                        if (ShopsFile.FindPlayerShop(player) != null) {
                            String shop = ShopsFile.FindPlayerShop(player);
                            if (!ShopsFile.GetShopItems(shop).isEmpty()) {
                                boolean foundToSell = false;
                                double sold = 0.0;
                                Integer itemsSold = 0;
                                for (Material material : ShopsFile.GetShopItems(shop).keySet()) {
                                    for (ItemStack itemStack : player.getInventory().getContents()) {
                                        if (itemStack != null)
                                            if (itemStack.getType() != null) {
                                                if (itemStack.getType().equals(material)) {
                                                    double moeny = itemStack.getAmount() * ShopsFile.GetShopItems(shop).get(material);
                                                    foundToSell = true;
                                                    sold = sold + moeny;
                                                    itemsSold = itemsSold + itemStack.getAmount();
                                                    Main.economy.depositPlayer(player, moeny);
                                                    player.getInventory().remove(itemStack);
                                                }
                                            }
                                    }
                                }
                                if (foundToSell) {
                                    sender.sendMessage(Presets.DefaultColor + "Sold " + Presets.StandOutColor + itemsSold + Presets.DefaultColor + " to shop " + Presets.StandOutColor + shop + Presets.DefaultColor + " for " + Presets.StandOutColor + sold);
                                    return true;
                                } else {
                                    sender.sendMessage(Presets.StandOutColor + "You have No Items to Sell!");
                                    return true;
                                }
                            } else {
                                sender.sendMessage(Presets.DefaultColor + "Could Not Find A Shop To Sell Your Items");
                                return true;
                            }
                        } else {
                            sender.sendMessage(Presets.DefaultColor + "Could Not Find A Shop To Sell Your Items");
                            return true;
                        }
                    }
                }
                if (args.length == 1) {
                    if (player.hasPermission("PF.Sell.Others") || sender.hasPermission("PF.Admin")) {
                        if (ShopsFile.FindPlayerShop(player) != null) {
                            String shop = ShopsFile.FindPlayerShop(player);
                            if (!ShopsFile.GetShopItems(shop).isEmpty()) {
                                if (Bukkit.getPlayer(args[0]) != null) {
                                    boolean foundToSell = false;
                                    double sold = 0.0;
                                    Integer itemsSold = 0;
                                    for (Material material : ShopsFile.GetShopItems(shop).keySet()) {
                                        for (ItemStack itemStack : Bukkit.getPlayer(args[0]).getInventory().getContents()) {
                                            if (itemStack != null)
                                                if (itemStack.getType() != null) {
                                                    if (itemStack.getType().equals(material)) {
                                                        double moeny = itemStack.getAmount() * ShopsFile.GetShopItems(shop).get(material);
                                                        foundToSell = true;
                                                        sold = sold + moeny;
                                                        itemsSold = itemsSold + itemStack.getAmount();
                                                        Main.economy.depositPlayer(Bukkit.getPlayer(args[0]), moeny);
                                                        Bukkit.getPlayer(args[0]).getInventory().remove(itemStack);
                                                    }
                                                }
                                        }
                                    }
                                    if (foundToSell) {
                                        Bukkit.getPlayer(args[0]).sendMessage(Presets.DefaultColor + "Sold " + Presets.StandOutColor + itemsSold + Presets.DefaultColor + " to shop " + Presets.StandOutColor + shop + Presets.DefaultColor + " for " + Presets.StandOutColor + sold);
                                        sender.sendMessage(defaultColor + "Sold all " + standOutColor + args[0] + defaultColor + " items");
                                        return true;
                                    } else {
                                        sender.sendMessage(standOutColor + args[0] + defaultColor + " has no items to sell");
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(standOutColor + args[0] + defaultColor + " is not a valid Player!");
                                    return true;
                                }


                            } else {
                                sender.sendMessage(Presets.DefaultColor + "Could Not Find A Shop To Sell the Items");
                                return true;
                            }

                        } else {
                            sender.sendMessage(Presets.DefaultColor + "Could Not Find A Shop To Sell the Items");
                            return true;
                        }
                    }
                }
            } else {
                if (args.length == 2) {
                    if (!ShopsFile.getShopList().isEmpty() && !ShopsFile.GetShopItems(ShopsFile.getShopList().get(ShopsFile.getShopList().size() - 1)).isEmpty()) {
                        String shop = ShopsFile.getShopList().get(ShopsFile.getShopList().size() - 1);
                        if (Bukkit.getPlayer(args[0]) != null) {
                            boolean foundToSell = false;
                            double sold = 0.0;
                            Integer itemsSold = 0;
                            for (Material material : ShopsFile.GetShopItems(shop).keySet()) {
                                for (ItemStack itemStack : Bukkit.getPlayer(args[0]).getInventory().getContents()) {
                                    if (itemStack != null)
                                        if (itemStack.getType() != null) {
                                            if (itemStack.getType().equals(material)) {
                                                double moeny = itemStack.getAmount() * ShopsFile.GetShopItems(shop).get(material);
                                                foundToSell = true;
                                                sold = sold + moeny;
                                                itemsSold = itemsSold + itemStack.getAmount();
                                                Main.economy.depositPlayer(Bukkit.getPlayer(args[0]), moeny);
                                                Bukkit.getPlayer(args[0]).getInventory().remove(itemStack);
                                            }
                                        }
                                }
                            }
                            if (foundToSell) {
                                Bukkit.getPlayer(args[0]).sendMessage(Presets.DefaultColor + "Sold " + Presets.StandOutColor + itemsSold + Presets.DefaultColor + " to shop " + Presets.StandOutColor + shop + Presets.DefaultColor + " for " + Presets.StandOutColor + sold);
                                sender.sendMessage(defaultColor + "Sold all " + standOutColor + args[0] + defaultColor + " items");
                                return true;
                            } else {
                                sender.sendMessage(standOutColor + args[0] + defaultColor + " has no items to sell");
                                return true;
                            }
                        } else {
                            sender.sendMessage(standOutColor + args[0] + defaultColor + " is not a valid Player!");
                            return true;
                        }

                    } else {
                        sender.sendMessage(Presets.DefaultColor + "Could Not Find A Shop To Sell the Items");
                        return true;
                    }

                }
                return true;
            }
        }
        return false;
    }

    public static boolean AutoSell() {
        if (cmd.getName().equalsIgnoreCase("AutoSell")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("PF.AutoSell") || sender.hasPermission("PF.Admin") || sender.hasPermission("PF.AutoSell.Others")) {
                    if (args.length == 0) {
                        if (!Catch.autoSell.containsKey((((Player) sender).getPlayer()))) {
                            Catch.autoSell.put(((Player) sender).getPlayer(), true);
                            sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "On");
                            return true;
                        } else {
                            if (Catch.autoSell.get(((Player) sender).getPlayer())) {
                                Catch.autoSell.put(((Player) sender).getPlayer(), false);
                                sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "Off");
                                return true;
                            } else {
                                Catch.autoSell.put(((Player) sender).getPlayer(), true);
                                sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "On");
                                return true;
                            }
                        }
                    }
                }
                if (!sender.hasPermission("PF.AutoSell")) {
                    sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoSell");
                    return true;
                }

                if (args.length >= 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (sender.hasPermission("PF.AutoSell.Others") || sender.hasPermission("PF.Admin")) {
                            if (args.length >= 2) {
                                if (args[1].equalsIgnoreCase("on")) {
                                    Catch.autoSell.put(Bukkit.getPlayer(args[0]), true);
                                    sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "On" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoSell has been turn " + standOutColor + "On" + defaultColor + " for You!");
                                    return true;
                                }
                                if (args[1].equalsIgnoreCase("off")) {
                                    Catch.autoSell.put(Bukkit.getPlayer(args[0]), false);
                                    sender.sendMessage(defaultColor + "You have turn AutoSell " + standOutColor + "Off" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoSell has been turn " + standOutColor + "Off" + defaultColor + " for You!");
                                    return true;
                                }
                            }
                        } else {
                            sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoSell.Others");
                            return true;
                        }
                    } else {
                        sender.sendMessage(standOutColor + args[0] + defaultColor + " is not a valid Player!");
                        return true;
                    }
                }
                sender.sendMessage(defaultColor + "/AutoSell <Player> <On>:<Off>");
                return true;
            }

        }
        return false;
    }

    public static boolean AutoBlock() {
        if (cmd.getName().equalsIgnoreCase("AutoBlock")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("PF.AutoBlock") || sender.hasPermission("PF.Admin") || sender.hasPermission("PF.AutoBlock")) {
                    if (args.length == 0) {
                        if (!Catch.autoBlock.containsKey((((Player) sender).getPlayer()))) {
                            Catch.autoBlock.put(((Player) sender).getPlayer(), true);
                            sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "On");
                            return true;
                        } else {
                            if (Catch.autoBlock.get(((Player) sender).getPlayer())) {
                                Catch.autoBlock.put(((Player) sender).getPlayer(), false);
                                sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "Off");
                                return true;
                            } else {
                                Catch.autoBlock.put(((Player) sender).getPlayer(), true);
                                sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "On");
                                return true;
                            }
                        }
                    }
                }
                if (!sender.hasPermission("PF.AutoBlock")) {
                    sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoBlock");
                    return true;
                }

                if (args.length >= 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (sender.hasPermission("PF.AutoBlock") && sender.hasPermission("PF.AutoBlock.Others")) {
                            if (args.length >= 2) {
                                if (args[1].equalsIgnoreCase("on")) {
                                    Catch.autoBlock.put(Bukkit.getPlayer(args[0]), true);
                                    sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "On" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoBlock has been turn " + standOutColor + "On" + defaultColor + " for You!");
                                    return true;
                                }
                                if (args[1].equalsIgnoreCase("off")) {
                                    Catch.autoBlock.put(Bukkit.getPlayer(args[0]), false);
                                    sender.sendMessage(defaultColor + "You have turn AutoBlock " + standOutColor + "Off" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoBlock has been turn " + standOutColor + "Off" + defaultColor + " for You!");
                                    return true;
                                }
                            }
                        } else {
                            sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoBlock.Others");
                            return true;
                        }
                    } else {
                        sender.sendMessage(standOutColor + args[0] + defaultColor + " is not a valid Player!");
                        return true;
                    }
                }
                sender.sendMessage(defaultColor + "/AutoBlock <Player> <On>:<Off>");
                return true;
            }

        }
        return false;
    }

    public static boolean AutoSmelt() {
        if (cmd.getName().equalsIgnoreCase("AutoSmelt")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("PF.AutoSmelt") || sender.hasPermission("PF.Admin") || sender.hasPermission("PF.AutoSmelt.Others")) {
                    if (args.length == 0) {
                        if (!Catch.autoSmelt.containsKey((((Player) sender).getPlayer()))) {
                            Catch.autoSmelt.put(((Player) sender).getPlayer(), true);
                            sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "On");
                            return true;
                        } else {
                            if (Catch.autoSmelt.get(((Player) sender).getPlayer())) {
                                Catch.autoSmelt.put(((Player) sender).getPlayer(), false);
                                sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "Off");
                                return true;
                            } else {
                                Catch.autoSmelt.put(((Player) sender).getPlayer(), true);
                                sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "On");
                                return true;
                            }
                        }
                    }
                }
                if (!sender.hasPermission("PF.AutoSmelt")) {
                    sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoSmelt");
                    return true;
                }

                if (args.length >= 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (sender.hasPermission("PF.AutoSmelt.Others") || sender.hasPermission("PF.Admin")) {
                            if (args.length >= 2) {
                                if (args[1].equalsIgnoreCase("on")) {
                                    Catch.autoSmelt.put(Bukkit.getPlayer(args[0]), true);
                                    sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "On" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoSmelt has been turn " + standOutColor + "On" + defaultColor + " for You!");
                                    return true;
                                }
                                if (args[1].equalsIgnoreCase("off")) {
                                    Catch.autoSmelt.put(Bukkit.getPlayer(args[0]), false);
                                    sender.sendMessage(defaultColor + "You have turn AutoSmelt " + standOutColor + "Off" + defaultColor + " for " + standOutColor + args[0]);
                                    Bukkit.getPlayer(args[0]).sendMessage(defaultColor + "AutoSmelt has been turn " + standOutColor + "Off" + defaultColor + " for You!");
                                    return true;
                                }
                            }
                        } else {
                            sender.sendMessage(defaultColor + "You don't have Permission " + standOutColor + " PF.AutoSmelt.Others");
                            return true;
                        }
                    } else {
                        sender.sendMessage(standOutColor + args[0] + defaultColor + " is not a valid Player!");
                        return true;
                    }
                }
                sender.sendMessage(defaultColor + "/AutoSmelt <Player> <On>:<Off>");
                return true;
            }

        }
        return false;
    }
}

class Signs extends CommandsClass {
    public static boolean SignsCommands() {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("signs")) {
                if (MinesSigns()) {
                    return true;
                }
                sender.sendMessage(defaultColor+"/PF Signs Help");
                return true;
            }
        }
        return false;
    }

    public static boolean MinesSigns() {
        if (args.length >= 2) {
            if (args[1].equalsIgnoreCase("mines")) {
                if (args.length >= 3) {
                    if (args[2].equalsIgnoreCase("percentage")) {
                        if (args.length >= 4) {
                            if (!Catch.RunningMines.isEmpty()) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (args[3].equals(mine.getName())) {
                                        Player player = (Player) sender;
                                        if(!player.getInventory().contains(giveSign(mine.getName(),"Percentage"))) {
                                            player.getInventory().addItem(giveSign(mine.getName(), "Percentage"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (args[2].equalsIgnoreCase("time")) {
                        if (args.length >= 4) {
                            if (!Catch.RunningMines.isEmpty()) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (args[3].equals(mine.getName())) {
                                        Player player = (Player) sender;
                                        if(!player.getInventory().contains(giveSign(mine.getName(),"Time"))) {
                                            player.getInventory().addItem(giveSign(mine.getName(), "Time"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean ShopsSigns(){
        return false;
    }

    private static ItemStack giveSign(String mineName, String todo) {
        ItemStack itemStack = new ItemStack(Material.AIR);

        if(Bukkit.getVersion().contains("1.4.4")){
            itemStack.setType(Material.OAK_SIGN);
        }
        else{
            //1.3.2
            itemStack.setType(Material.SIGN);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Presets.DefaultColor + mineName + " " + todo);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}