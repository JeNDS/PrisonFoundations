package Me.JeNDS.Objects.MineObjects;

import Me.JeNDS.Files.MineFile;
import Me.JeNDS.Main.PF;
import Me.JeNDS.Reagions.RegionCreator;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static Me.JeNDS.Static.Catch.RunningMines;

public class Mine {
    private final Mine Mine = this;
    private String Name;
    private World world;
    private ArrayList<Location> BlockLocation = new ArrayList<>();
    private ArrayList<PFSign> mineSigns = new ArrayList<>();
    private ArrayList<BlockType> blockTypes = new ArrayList<>();
    private Location Spawn;
    private Location PointOne;
    private Location PointTwo;
    private Integer MinePercentage = 0;
    private Integer MinePercentageReset = 0;
    private Integer MineResetTime = 10;
    private Integer TimeBeforeReset = 10;
    private boolean PvP;
    private boolean MineResetting;
    private Integer TimeResetTaskID = null;
    private Integer PercentageResetTaskID = null;


    public Mine(String Name) {
        this.Name = Name;


    }

    public Mine(Location Point1, Location Point2, String Name) {
        this.Name = Name;
        this.PointOne = Point1;
        this.PointTwo = Point2;
        BlockLocation = RegionCreator.regionCreator(Point1, Point2);
        this.world = Point1.getWorld();

    }

    public static boolean PlayerCloseToMine(Player player, Mine mine) {
        for (Location location : mine.getBlockLocation()) {
            double range = Math.sqrt(Math.pow(location.getX() - player.getLocation().getX(), 2) + Math.pow(location.getY() - player.getLocation().getY(), 2) + Math.pow(location.getZ() - player.getLocation().getZ(), 2));
            if ((int) range <= 15) {
                return true;
            }
        }
        return false;
    }

    public void loadMine() {
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
            BlockType bt = new BlockType(material,mineFile.getBlockTypes().get(material));
            blockTypes.add(bt);
        }
        if (!RunningMines.contains(this)) {
            RunningMines.add(this);
        }
        timeCounter();
        percentageCheck();
    }

    public void createMine() {
        HashMap<Material, Integer> types = new HashMap<>();
        PvP = false;
        BlockType Default = new BlockType(Material.STONE,50);
        types.put(Material.STONE, 50);
        BlockType Default2 = new BlockType(Material.COAL_ORE,50);
        types.put(Material.COAL_ORE, 50);
        blockTypes.add(Default);
        blockTypes.add(Default2);
        resetMine(true, false);
        if (!RunningMines.contains(this)) {
            RunningMines.add(this);
        }
        MineResetTime = 10;
        TimeBeforeReset = 10;
        MinePercentageReset = 50;
        timeCounter();
        percentageCheck();
        MineFile thisMine = new MineFile(Name);
        thisMine.createMineFile(world.getName(), BlockLocation, PointOne, PointTwo, types, MineResetTime, MinePercentageReset);

    }

    public void createMineSign(Location location,SingType singType){
        for(PFSign pfSign : mineSigns){
            if(pfSign.getLocation().equals(location)) return;
        }
        mineSigns.add(new PFSign(this,location,singType));
    }

    public boolean deleteMineSing(Location location){
        for(PFSign pfSign: mineSigns){
            if(pfSign.getLocation().equals(location)){
                pfSign.stopUpdate();
                mineSigns.remove(pfSign);
                return true;
            }
        }
        return false;
    }

    public void resetMine(boolean fullReset, boolean slowReset) {
        HashMap<Material, Integer> amountOfBlocks = new HashMap<>();
        if (!MineResetting) {
            int Percentage = 0;
            int totalBlocksUsed = 0;

            if (!blockTypes.isEmpty()) {
                for (BlockType block : blockTypes) {
                    amountOfBlocks.put(block.getMaterial(), BlockLocation.size() * block.getPercentage() / 100);
                    totalBlocksUsed = (totalBlocksUsed + BlockLocation.size()) * block.getPercentage() / 100;
                    Percentage = Percentage + block.getPercentage();
                }
                if (Percentage == 100) {
                    if (Spawn != null) {
                        for (Player player : Mine.getWorld().getPlayers()) {
                            if (PlayerCloseToMine(player, Mine)) {
                                player.sendMessage(Presets.DefaultColor + "Resetting Mine");
                            }
                            teleportToMineSpawn(player);
                        }
                    }
                    ArrayList<Location> temLoc1 = new ArrayList<>(BlockLocation);
                    ArrayList<Location> temLoc2 = new ArrayList<>(BlockLocation);
                    HashMap<Location, Material> locationMaterial = new HashMap<>();
                    if (!fullReset) {
                        temLoc1 = new ArrayList<>();
                        for (Location location : BlockLocation) {
                            if (location.getBlock().getType() == Material.AIR) temLoc1.add(location);
                        }
                        temLoc2 = new ArrayList<>(temLoc1);
                    }
                    for (BlockType blockType : blockTypes) {
                        int scope = (int) ((blockType.getPercentage() / 100.0) * temLoc2.size());
                        for (int i = 0; i != scope; i++) {
                            int randomPick = ThreadLocalRandom.current().nextInt(0, temLoc1.size());
                            if (!locationMaterial.containsKey(temLoc1.get(randomPick))) {
                                locationMaterial.put(temLoc1.get(randomPick), blockType.getMaterial());
                            }
                            temLoc1.remove(temLoc1.get(randomPick));
                        }
                    }
                    if (slowReset) {
                        MineResetting = true;
                        new BukkitRunnable() {
                            final List<Location> keys = new ArrayList<>(locationMaterial.keySet());
                            int size = 0;

                            @Override
                            public void run() {
                                for (int i = 1; i != 20; i++) {
                                    if (size == locationMaterial.size()) {
                                        MineResetting = false;
                                        this.cancel();
                                    } else {
                                        keys.get(size).getBlock().setType(locationMaterial.get(keys.get(size)));
                                        size++;
                                    }
                                }
                            }
                        }.runTaskTimer(PF.getPlugin(PF.class), 0L, 1L);
                    } else {
                        for (Location location : locationMaterial.keySet()) {
                            location.getBlock().setType(locationMaterial.get(location));
                        }
                    }
                }


            }
        }
    }

    public void updateBlockTypes(ArrayList<BlockType> newBlockTypes){
        MineFile file = new MineFile(Name);
        file.updateBlockTypes(newBlockTypes);
        this.blockTypes = newBlockTypes;
    }

    public Integer getTotalPercentage(){
        int i = 0;
        for(BlockType blockType : blockTypes){
            i = i+ blockType.getPercentage();
        }
        return i;
    }

    public boolean containsBlockType(Material material){
        for(BlockType blockType : blockTypes){
            if(blockType.getMaterial().equals(material)) return true;
        }
        return false;
    }

    private void teleportToMineSpawn(Player player) {
        for (Location location : this.getBlockLocation()) {
            double range = Math.sqrt(Math.pow(location.getX() - player.getLocation().getX(), 2) + Math.pow(location.getY() - player.getLocation().getY(), 2) + Math.pow(location.getZ() - player.getLocation().getZ(), 2));
            if ((int) range <= 2) {
                if (Spawn != null) {
                    player.teleport(Spawn);
                }
            }
        }
    }

    private void timeCounter() {
        TimeResetTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PF.getPlugin(PF.class), new Runnable() {

            @Override
            public void run() {
                if (TimeBeforeReset == 0) {
                    resetMine(false, true);
                    TimeBeforeReset = MineResetTime;
                } else {
                    TimeBeforeReset--;
                }
            }
        }, 0L, 1200L);

    }

    private void percentageCheck() {
        PercentageResetTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PF.getProvidingPlugin(PF.class), () -> {
            int totalAmount = BlockLocation.size();
            int amountNotAir = 0;
            if (!BlockLocation.isEmpty()) {
                for (Location location : BlockLocation) {
                    location.getBlock();
                    location.getBlock().getType();
                    for (BlockType blockType : blockTypes) {
                        if (blockType.getMaterial() == location.getBlock().getType()) {
                            amountNotAir++;
                        }
                    }
                }
                MinePercentage = amountNotAir * 100 / totalAmount;
                if (MinePercentage < MinePercentageReset) {
                    if (MineResetting) {
                        return;
                    }
                    resetMine(false, true);
                }
            }
        }, 0L, 40L);
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
            BlockType blockType = new BlockType(material,types.get(material));
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

    public Integer getMineResetTime() {
        return MineResetTime;
    }

    public void setMineResetTime(Integer mineResetTime) {
        MineFile file = new MineFile(Name);
        MineResetTime = mineResetTime;
        TimeBeforeReset = MineResetTime;
        file.setResetTime(mineResetTime);
    }

    public Integer getMinePercentageReset() {
        return MinePercentageReset;
    }

    public void setMinePercentageReset(Integer minePercentageReset) {
        MineFile file = new MineFile(Name);
        file.setResetPercentage(minePercentageReset);
        MinePercentageReset = minePercentageReset;
    }

    public Integer getTimeBeforeReset() {
        return TimeBeforeReset;
    }

    public Integer getTimeResetTaskID() {
        return TimeResetTaskID;
    }

    public Integer getPercentageResetTaskID() {
        return PercentageResetTaskID;
    }

    public boolean isMineResetting() {
        return MineResetting;
    }
}

