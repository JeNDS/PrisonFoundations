package JeNDS.JPlugins.Mines.MineObjects;

import JeNDS.JPlugins.Mines.Files.MineFile;
import JeNDS.JPlugins.PF;
import JeNDS.JPlugins.Mines.Reagions.RegionCreator;
import JeNDS.JPlugins.Static.Catch;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static JeNDS.JPlugins.Static.Catch.RunningMines;

public class Mine {
    private final Mine Mine = this;
    private String Name;
    private World world;
    private ArrayList<Location> BlockLocations = new ArrayList<>();
    private ArrayList<PFSign> mineSigns = new ArrayList<>();
    private ArrayList<PFHologram> holograms = new ArrayList<>();
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
        BlockLocations = RegionCreator.regionCreator(Point1, Point2);
        this.world = Point1.getWorld();
    }

    public static boolean PlayerCloseToMine(Player player, Mine mine) {
        for (Location location : mine.getBlockLocations()) {
            double range = Math.sqrt(Math.pow(location.getX() - player.getLocation().getX(), 2) + Math.pow(location.getY() - player.getLocation().getY(), 2) + Math.pow(location.getZ() - player.getLocation().getZ(), 2));
            if ((int) range <= 10) {
                return true;
            }
        }
        return false;
    }
    public static Mine ClosesMineToPlayer(Player player) {
        for(JeNDS.JPlugins.Mines.MineObjects.Mine mine : RunningMines) {
            if(PlayerCloseToMine(player,mine)) return mine;
        }
        return null;
    }

    public static boolean LocationInMine(Location location){
        for(JeNDS.JPlugins.Mines.MineObjects.Mine mine : RunningMines){
            if(mine.getBlockLocations().contains(location)){
                return true;
            }
            for(Location location1 : mine.getBlockLocations()){
                int distance = (int) location.distance(location1);
                if(distance == 0){
                    return true;
                }
            }
        }
        return false;
    }
    public static Mine GetMineFromLocation(Location location){
        for(JeNDS.JPlugins.Mines.MineObjects.Mine mine : RunningMines){
            if(mine.getBlockLocations().contains(location)) return mine;
        }
        return null;
    }

    public static Mine GetMineFromHologramLocation(Location location) {
        for (JeNDS.JPlugins.Mines.MineObjects.Mine mine : RunningMines) {
            for (PFHologram pfHologram : mine.getHolograms()) {
                for (ArmorStand stand : pfHologram.getArmorStands()) {
                    if (stand.getLocation().equals(location)) {
                        return mine;
                    }
                }
            }
        }
        return null;
    }

    public static boolean RemoveMineHologramFromLocation(Location location) {
        for (JeNDS.JPlugins.Mines.MineObjects.Mine mine : RunningMines) {
            for (PFHologram pfHologram : mine.getHolograms()) {
                for (ArmorStand stand : pfHologram.getArmorStands()) {
                    if (stand.getLocation().equals(location)) {
                        mine.deleteHologram(pfHologram);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Mine GetMineFromName(String name) {
        for (JeNDS.JPlugins.Mines.MineObjects.Mine mine : RunningMines) {
            if (mine.getName().contains(name)) return mine;
        }
        return null;
    }

    public void deleteMine() {
        MineFile mineFile = mineFile();
        for (PFSign pfSign : mineSigns) {
            pfSign.delete();
            mineFile.removeSignFile(pfSign.getID());
        }
        stopUpdate();
        for (PFHologram pfHologram : getHolograms()) {
            pfHologram.removeHologram();
        }
        mineFile.deleteConfig();
        Catch.RunningMines.remove(this);
    }

    public void startUpdate() {
        timeCounter();
        percentageCheck();
    }

    public void stopUpdate() {
        Bukkit.getScheduler().cancelTask(getPercentageResetTaskID());
        Bukkit.getScheduler().cancelTask(getTimeResetTaskID());
    }

    public void loadMine() {
        MineFile mineFile = new MineFile(this.Name);
        world = Bukkit.getWorld(mineFile.getWorld());
        BlockLocations = mineFile.getBlockLocation();
        Spawn = mineFile.getSpawn();
        PointOne = mineFile.getPointOne();
        PointTwo = mineFile.getPointTwo();
        MinePercentageReset = mineFile.getResetPercentage();
        MineResetTime = mineFile.getResetTime();
        TimeBeforeReset = mineFile.getLastTime();
        PvP = mineFile.isPvpRule();
        for (Material material : mineFile.getBlockTypes().keySet()) {
            BlockType bt = new BlockType(material, mineFile.getBlockTypes().get(material));
            blockTypes.add(bt);
        }
        if (!RunningMines.contains(this)) {
            RunningMines.add(this);
        }
        startUpdate();
    }

    public void createMine() {
        HashMap<Material, Integer> types = new HashMap<>();
        PvP = false;
        BlockType Default = new BlockType(Material.STONE, 50);
        types.put(Material.STONE, 50);
        BlockType Default2 = new BlockType(Material.COAL_ORE, 50);
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
        mineFile().createMineFile(world.getName(), BlockLocations, PointOne, PointTwo, types, MineResetTime, MinePercentageReset);

    }

    public void createMineSign(Location location, UpdateType singType) {
        for (PFSign pfSign : mineSigns) {
            if (pfSign.getLocation().equals(location)) return;
        }
        PFSign pfSign = new PFSign(Name, location, singType, null);
        mineSigns.add(pfSign);
        mineFile().createSignFile(pfSign.getID(), pfSign.getSingType(), pfSign.getLocation());

    }

    public boolean createHologram(Location location, UpdateType updateType) {
        for (PFHologram pfHologram : holograms) {
            if (pfHologram.isHologram(location)) return false;
        }
        PFHologram pfHologram = new PFHologram(location, Name, updateType, null);
        holograms.add(pfHologram);
        mineFile().createHologramFile(pfHologram.getId(), pfHologram.getUpdateType(), location);
        return true;
    }

    public boolean deleteMineSing(Location location) {
        for (PFSign pfSign : mineSigns) {
            if (pfSign.getLocation().equals(location)) {
                pfSign.delete();
                mineFile().removeSignFile(pfSign.getID());
                return true;
            }
        }
        return false;
    }

    public void deleteHologram(PFHologram pFHologram) {
        mineFile().removeHologramFile(pFHologram.getId());
        holograms.remove(pFHologram);
        pFHologram.removeHologram();
    }

    private MineFile mineFile() {
        return new MineFile(Name);
    }

    public void resetMine(boolean fullReset, boolean slowReset) {
        HashMap<Material, Integer> amountOfBlocks = new HashMap<>();
        if (!MineResetting) {
            int Percentage = 0;
            int totalBlocksUsed = 0;

            if (!blockTypes.isEmpty()) {
                for (BlockType block : blockTypes) {
                    amountOfBlocks.put(block.getMaterial(), BlockLocations.size() * block.getPercentage() / 100);
                    totalBlocksUsed = (totalBlocksUsed + BlockLocations.size()) * block.getPercentage() / 100;
                    Percentage = Percentage + block.getPercentage();
                }
                if (Percentage == 100) {
                    if (Spawn != null) {
                        for (Player player : Mine.getWorld().getPlayers()) {
                            if (PlayerCloseToMine(player, Mine)) {
                                player.sendMessage(Presets.MainColor + "Resetting Mine");
                            }
                            teleportToMineSpawn(player);
                        }
                    }
                    ArrayList<Location> temLoc1 = new ArrayList<>(BlockLocations);
                    ArrayList<Location> temLoc2 = new ArrayList<>(BlockLocations);
                    HashMap<Location, Material> locationMaterial = new HashMap<>();
                    if (!fullReset) {
                        temLoc1 = new ArrayList<>();
                        for (Location location : BlockLocations) {
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

    public void updateBlockTypes(ArrayList<BlockType> newBlockTypes) {
        mineFile().updateBlockTypes(newBlockTypes);
        this.blockTypes = newBlockTypes;
    }

    public Integer getTotalPercentage() {
        int i = 0;
        for (BlockType blockType : blockTypes) {
            i = i + blockType.getPercentage();
        }
        return i;
    }

    public boolean containsBlockType(Material material) {
        for (BlockType blockType : blockTypes) {
            if (blockType.getMaterial().equals(material)) return true;
        }
        return false;
    }

    private void teleportToMineSpawn(Player player) {
        for (Location location : this.getBlockLocations()) {
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
            int totalAmount = BlockLocations.size();
            int amountNotAir = 0;
            if (!BlockLocations.isEmpty()) {
                for (Location location : BlockLocations) {
                    if (Material.AIR != location.getBlock().getType()) {
                        amountNotAir++;
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

    public ArrayList<Location> getBlockLocations() {
        return BlockLocations;
    }

    public ArrayList<BlockType> getBlockTypes() {
        return blockTypes;
    }

    public void setBlockTypes(HashMap<Material, Integer> types) {
        ArrayList<BlockType> temp = new ArrayList<>();
        for (Material material : types.keySet()) {
            BlockType blockType = new BlockType(material, types.get(material));
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

    public ArrayList<PFSign> getMineSigns() {
        return mineSigns;
    }

    public void setMineSigns(ArrayList<PFSign> mineSigns) {
        this.mineSigns = mineSigns;
    }

    public ArrayList<PFHologram> getHolograms() {
        return holograms;
    }

    public void setHolograms(ArrayList<PFHologram> holograms) {
        this.holograms = holograms;
    }
}

