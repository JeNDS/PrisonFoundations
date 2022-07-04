package Me.JeNDS.Static;

import Me.JeNDS.Objects.MineObjects.DropParty;
import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.Objects.Rank;
import Me.JeNDS.Objects.Shop;
import Me.JeNDS.PlayerGUI.PFGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class Catch {
    public static HashMap<Player, PFGUI> selectingBlockType = new HashMap<>();
    public static HashMap<Player, Inventory> lastPlayerMenu = new HashMap<>();
    public static HashMap<Player, Inventory> nextPlayerMenu = new HashMap<>();
    public static HashMap<Player, Boolean> autoSell = new HashMap<>();
    public static HashMap<Player, Boolean> autoBlock = new HashMap<>();
    public static HashMap<Player, Boolean> autoSmelt = new HashMap<>();
    public static HashMap<Player, Integer> inventoryFullTimeWait = new HashMap<>();
    public static ArrayList<Mine> RunningMines = new ArrayList<>();
    public static ArrayList<Rank> Ranks = new ArrayList<>();
    public static ArrayList<Shop> Shops = new ArrayList<>();
    public static ArrayList<DropParty> DropParties = new ArrayList<>();


}
