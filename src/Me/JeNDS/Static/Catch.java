package Me.JeNDS.Static;

import Me.JeNDS.Objects.DropParty;
import Me.JeNDS.Objects.Mine;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class Catch {
    public static HashMap<Player,Boolean> selectingBlockType = new HashMap<>();
    public static HashMap<Player, Inventory> lastPlayerMenu = new HashMap<>();
    public static HashMap<Player,Inventory> nextPlayerMenu = new HashMap<>();
    public static HashMap<Player,Boolean> autoSell = new HashMap<>();
    public static HashMap<Player,Boolean> autoBlock = new HashMap<>();
    public static HashMap<Player,Boolean> autoSmelt = new HashMap<>();
    public static HashMap<Player,Integer> invetoryFullTimeWait = new HashMap<>();
    public static boolean MineResetting = false;
    public static ArrayList<Mine> RunningMines = new ArrayList<>();
    public static ArrayList<DropParty> DropParties = new ArrayList<>();


}
