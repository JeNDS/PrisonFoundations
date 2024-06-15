package JeNDS.Plugins.PrisonFundations.Static;


import JeNDS.Plugins.PrisonFundations.Managers.PFGUI;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.DropParty;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.BlockUtility.BlockUtility;
import JeNDS.Plugins.PrisonFundations.PlayerData.PFPlayer;
import JeNDS.Plugins.PrisonFundations.Ranks.Rank;
import JeNDS.Plugins.PrisonFundations.Shops.Shop;
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
