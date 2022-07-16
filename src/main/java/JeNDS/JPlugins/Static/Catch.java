package JeNDS.JPlugins.Static;

import JeNDS.JPlugins.Objects.MineObjects.Mine;
import JeNDS.JPlugins.Objects.Rank;
import JeNDS.JPlugins.Objects.Shop;
import JeNDS.JPlugins.PlayerGUI.PFGUI;
import JeNDS.JPlugins.Utilities.BlockUtility.BlockUtility;
import JeNDS.JPlugins.Objects.MineObjects.DropParty;
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
    public static ArrayList<Shop> Shops = new ArrayList<>();
    public static ArrayList<DropParty> DropParties = new ArrayList<>();


}
