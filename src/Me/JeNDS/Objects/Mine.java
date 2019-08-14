package Me.JeNDS.Objects;

import Me.JeNDS.Files.MineFile;
import Me.JeNDS.MainFolder.Main;
import Me.JeNDS.Reagions.RegionCreator;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import static Me.JeNDS.Static.Catch.MineResetting;
import static Me.JeNDS.Static.Catch.RunningMines;

public class Mine {
    private String Name;
    private World world;
   private ArrayList<Location> BlockLocation = new ArrayList<>();
    private ArrayList<BlockType> blockTypes = new ArrayList<>();
    private Location Spawn;
    private Location PointOne;
    private Location PointTwo;
    private Integer MinePercentage = 0;
    private Integer MinePercentageReset = 0;
    private double MineResetTime = 0.0;
    private double TimeBeforeReset = 0.0;
    private boolean PvP;
    private Mine Mine = this;
    private Integer TimeResetTaskID = null;
    private Integer PercentageResetTaskID = null;


    public Mine(String Name) {
        this.Name = Name;


    }

    public Mine(Location Point1, Location Point2, String Name) {
        this.Name = Name;
        this.BlockLocation = BlockLocation;
        this.PointOne = Point1;
        this.PointTwo = Point2;
        BlockLocation = RegionCreator.regionCreator(Point1, Point2);
        this.world = Point1.getWorld();

    }

    public static boolean playerCloseToMine(Player player, Mine mine) {
        for (Location location : mine.getBlockLocation()) {
            double range = Math.sqrt(Math.pow(location.getX() - player.getLocation().getX(), 2) +
                    Math.pow(location.getY() - player.getLocation().getY(), 2) +
                    Math.pow(location.getZ() - player.getLocation().getZ(), 2));
            if ((int) range <= 15) {
                return true;
            }
        }
        return false;
    }

    public void LoadMine() {
        MineFile mineFile = new MineFile(this.Name);
        world = Bukkit.getWorld(mineFile.getWorld());
        BlockLocation = mineFile.getBlockLocation();
        Spawn = mineFile.getSpawn();
        PointOne = mineFile.getPointOne();
        PointTwo = mineFile.getPointTwo();
        MinePercentageReset = mineFile.getResetPercentage();
        MineResetTime = mineFile.getResetTime();
        TimeBeforeReset = mineFile.getLastTime();
        PvP = mineFile.isPvpRule();
        for (Material material : mineFile.getBlockTypes().keySet()) {
            BlockType bt = new BlockType();
            bt.setMaterial(material);
            bt.setPercentage(mineFile.getBlockTypes().get(material));
            blockTypes.add(bt);
        }
        if (!RunningMines.contains(this)) {
            RunningMines.add(this);
        }
        timeCounter();
        percentageCheck();
    }

    public void CreateMine() {
        HashMap<Material, Integer> types = new HashMap<>();
        PvP = false;
        BlockType Default = new BlockType();
        Default.setMaterial(Material.STONE);
        Default.setPercentage(50);
        types.put(Material.STONE, 50);
        BlockType Default2 = new BlockType();
        Default2.setMaterial(Material.COAL_ORE);
        Default2.setPercentage(50);
        types.put(Material.COAL_ORE, 50);
        blockTypes.add(Default);
        blockTypes.add(Default2);
        ResetMine();
        if (!RunningMines.contains(this)) {
            RunningMines.add(this);
        }
        MineResetTime = 0.10;
        MinePercentageReset= 50;
        timeCounter();
        percentageCheck();
        MineFile thisMine = new MineFile(Name);
        thisMine.createMineFile(world.getName(), BlockLocation, PointOne, PointTwo, types,MineResetTime,MinePercentageReset);

    }

    public void ResetMine() {
        HashMap<Material, Integer> ammountOfBlocks = new HashMap<>();
        if (MineResetting) {
            while (MineResetting != false) {
                ResetMine();
            }
        } else {
            Integer Percentage = 0;
            Integer totalBlocksUsed = 0;

            if (!blockTypes.isEmpty()) {
                for (BlockType block : blockTypes) {
                    ammountOfBlocks.put(block.getMaterial(), BlockLocation.size() * block.getPercentage() / 100);
                    totalBlocksUsed = (totalBlocksUsed + BlockLocation.size()) * block.getPercentage() / 100;
                    Percentage = Percentage + block.getPercentage();
                }
                if (Percentage == 100) {
                    if (Spawn != null) {
                        for (Player player : Mine.getWorld().getPlayers()) {
                            if (playerCloseToMine(player, Mine)) {
                                player.sendMessage(Presets.DefaultColor + "Resetting Mine");
                            }
                            teleportToMineSpawn(player);
                        }
                    }
                    int blockTracker = blockTypes.size();
                    ArrayList<Location> blocksNotUsed = new ArrayList<>();
                    for (Location location : BlockLocation) {
                        blocksNotUsed.add(location);

                    }
                    for (BlockType block : blockTypes) {
                        ArrayList<Location> locationData = new ArrayList<>();
                        while (locationData.size() < ammountOfBlocks.get(block.getMaterial())) {
                            double random = Math.random() * blocksNotUsed.size();
                            try {


                                if (blocksNotUsed.size() >= 1) {
                                    if ((int) random <= blocksNotUsed.size() - 1) {
                                        Location location = blocksNotUsed.get((int) random);
                                        if (!locationData.contains(location)) {
                                            locationData.add(location);
                                            blocksNotUsed.remove(location);
                                            location.getBlock().setType(block.getMaterial());
                                        }
                                    }

                                }
                                if (blocksNotUsed.size() == 1) {
                                    Location location = blocksNotUsed.get(0);
                                    if (!locationData.contains(location)) {
                                        locationData.add(location);
                                        blocksNotUsed.remove(location);
                                        location.getBlock().setType(block.getMaterial());
                                    }

                                }

                            } catch (IndexOutOfBoundsException | ConcurrentModificationException exception) {

                            }

                        }
                        if (blockTracker == 1) {
                            if (!blocksNotUsed.isEmpty()) {
                                for (Location location : blocksNotUsed) {
                                    locationData.add(location);
                                    blocksNotUsed.remove(location);
                                    location.getBlock().setType(block.getMaterial());
                                }
                            }
                        }
                        blockTracker--;


                    }
                }

                MineResetting = false;

            }
        }
    }

    private void teleportToMineSpawn(Player player) {
        for (Location location : this.getBlockLocation()) {
            double range = Math.sqrt(Math.pow(location.getX() - player.getLocation().getX(), 2) +
                    Math.pow(location.getY() - player.getLocation().getY(), 2) +
                    Math.pow(location.getZ() - player.getLocation().getZ(), 2));
            if ((int) range <= 2) {
                if (Spawn != null) {
                    player.teleport(Spawn);
                }
            }
        }
    }

    private void timeCounter() {
        TimeResetTaskID= Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
            Integer Hours = 0;
            Integer Minutes = 0;

            @Override
            public void run() {
                MineFile mineFile = new MineFile(Name);
                if (TimeBeforeReset == MineResetTime) {
                    DecimalFormat format = new DecimalFormat("0.00");
                    String temp = format.format(MineResetTime);
                    String converter[] = temp.split("\\.");
                    try {

                        Hours = Integer.parseInt(converter[0]);
                        Minutes = Integer.parseInt(converter[1]);
                    } catch (NullPointerException | NumberFormatException e) {

                    }
                }
                else {
                    String temp = "" + getTimeBeforeReset();
                    String converter[] = temp.split("\\.");
                    try {

                        Hours = Integer.parseInt(converter[0]);
                        Minutes = Integer.parseInt(converter[1]);
                    } catch (NullPointerException | NumberFormatException e) {

                    }
                }
                if (Minutes == 0 && Hours == 0) {
                    if(MineResetting){
                        return;
                    }
                    ResetMine();
                    TimeBeforeReset = MineResetTime;
                    mineFile.setLastTime(TimeBeforeReset);
                    return;
                }
                if (Minutes - 1 >= 0) {
                    Minutes--;
                    String time = "";
                    if(Minutes<10){
                        time = Hours + ".0" + Minutes;
                    }
                    else {
                        time = Hours + "." + Minutes;
                    }
                    TimeBeforeReset = Double.parseDouble(time);
                    mineFile.setLastTime(TimeBeforeReset);
                } else {
                    if (Hours > 0) {
                        Minutes = 60 - 1;
                        Hours--;
                        String time = Hours + "." + Minutes;
                        TimeBeforeReset = Double.parseDouble(time);
                        mineFile.setLastTime(TimeBeforeReset);
                    }
                }
            }
        }, 0l, 1200l);

    }

    private void percentageCheck() {
        PercentageResetTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getProvidingPlugin(Main.class), new Runnable() {
            @Override
            public void run() {
                int totalAmount = BlockLocation.size();
                int amountNotAir = 0;
                if (!BlockLocation.isEmpty()) {
                    for (Location location : BlockLocation) {
                        if (location.getBlock() != null) {
                            if (location.getBlock().getType() != null) {
                                for (BlockType blockType : blockTypes) {
                                    if (blockType.getMaterial() == location.getBlock().getType()) {
                                        amountNotAir++;
                                    }
                                }
                            }
                        }
                    }
                    MinePercentage = amountNotAir * 100 / totalAmount;
                    if (MinePercentage < MinePercentageReset) {
                        if(MineResetting){
                            return;
                        }
                        ResetMine();
                    }
                }
            }
        }, 0l, 40l);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;

    }

    public World getWorld() {
        return world;
    }

    public ArrayList<Location> getBlockLocation() {
        return BlockLocation;
    }

    public ArrayList<BlockType> getBlockTypes() {
        return blockTypes;
    }

    public void setBlockTypes(HashMap<Material, Integer> types) {
        ArrayList<BlockType> temp = new ArrayList<>();
        for (Material material : types.keySet()) {
            BlockType blockType = new BlockType();
            blockType.setMaterial(material);
            blockType.setPercentage(types.get(material));
            temp.add(blockType);
        }
        this.blockTypes = temp;
    }

    public Location getSpawn() {
        return Spawn;
    }

    public void setSpawn(Location spawn) {
        Spawn = spawn;
        MineFile file = new MineFile(Name);
        file.setSpawn(spawn);
    }

    public Location getPointOne() {
        return PointOne;
    }

    public Location getPointTwo() {
        return PointTwo;
    }

    public Integer getMinePercentage() {
        return MinePercentage;
    }


    public boolean isPvP() {
        return PvP;
    }

    public void setPvP(boolean pvP) {
        PvP = pvP;
    }

    public double getMineResetTime() {
        return MineResetTime;
    }

    public void setMineResetTime(double mineResetTime) {
        MineResetTime = mineResetTime;
    }

    public Integer getMinePercentageReset() {
        return MinePercentageReset;
    }

    public void setMinePercentageReset(Integer minePercentageReset) {
        MinePercentageReset = minePercentageReset;
    }

    public double getTimeBeforeReset() {
        return TimeBeforeReset;
    }

    public Integer getTimeResetTaskID() {
        return TimeResetTaskID;
    }

    public Integer getPercentageResetTaskID() {
        return PercentageResetTaskID;
    }
}

class BlockType {
    private Material material;
    private int percentage;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }



}

