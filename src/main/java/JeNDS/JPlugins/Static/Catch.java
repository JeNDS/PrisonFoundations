package JeNDS.JPlugins.Static;

import JeNDS.JPlugins.Mines.MineObjects.Mine;
import JeNDS.JPlugins.PlayerData.PFPlayer;
import JeNDS.JPlugins.Ranks.Rank;
import JeNDS.JPlugins.Shops.Shop;
import JeNDS.JPlugins.Managers.PFGUI;
import JeNDS.JPlugins.Mines.Utilities.BlockUtility.BlockUtility;
import JeNDS.JPlugins.Mines.MineObjects.DropParty;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class Catch {
    public static HashMap<Player, PFGUI> selectingBlockType = new HashMap<>();
    public static ArrayList<BlockUtility> blockUtilities = new ArrayList<>();
    public static HashMap<Player, Inventory> lastPlayerMenu = new HashMap<>();
    public static HashMap<Player, Inventory> nextPlayerMenu = new HashMap<>();
    public static HashMap<Player, Boolean> autoSell = new HashMap<>();
    public static HashMap<Player, Integer> inventoryFullTimeWait = new HashMap<>();
    public static ArrayList<Mine> RunningMines = new ArrayList<>();
    public static ArrayList<Rank> Ranks = new ArrayList<>();
    public static ArrayList<PFPlayer> PFPlayers = new ArrayList<>();
    public static ArrayList<Shop> Shops = new ArrayList<>();
    public static ArrayList<DropParty> DropParties = new ArrayList<>();


}
