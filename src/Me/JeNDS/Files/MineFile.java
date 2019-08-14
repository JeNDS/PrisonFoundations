package Me.JeNDS.Files;

import Me.JeNDS.MainFolder.Main;
import Me.JeNDS.Objects.Mine;
import Me.JeNDS.Reagions.RegionCreator;
import Me.JeNDS.Static.Catch;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MineFile {
    private static Main plugin = Main.getPlugin(Main.class);
    private HashMap<Material, Integer> BlockTypes = new HashMap();
    private String Name;
    private String world;
    private ArrayList<Location> BlockLocation = new ArrayList<>();
    private Location Spawn;
    private Location PointOne;
    private Location PointTwo;
    private boolean pvpRule;
    private FileMaker mineFile;
    private double ResetTime;
    private Integer ResetPercentage;
    private double lastTime;


    public MineFile(String mineName) {
        Name = mineName;
        FileMaker folder = new FileMaker("Mine Data", true);
        {
            folder.create();
        }
        mineFile = new FileMaker(mineName, true, "Mine Data");
        {
            if (mineFile.getRawFile().length() == 0) {
                mineFile.create();
                loadConfig();
            } else {
                loadConfig();
            }
        }
    }

    public static void LoadMines() {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                if (mine.getPercentageResetTaskID() != null) {
                    Bukkit.getScheduler().cancelTask(mine.getPercentageResetTaskID());
                }
                if (mine.getTimeResetTaskID() != null) {
                    Bukkit.getScheduler().cancelTask(mine.getTimeResetTaskID());
                }
            }
        }
        Catch.MineResetting = false;
        if (plugin.getDataFolder().exists()) {
            File dir = new File(plugin.getDataFolder(), "Mine Data");
            if (dir.exists()) {
                File[] mineList = dir.listFiles();
                if (mineList != null) {
                    for (File mine : mineList) {
                        MineFile mineFile = new MineFile(mine.getName());
                        mineFile.loadConfig();
                        Mine mines = new Mine(mine.getName());
                        mines.LoadMine();

                    }
                }
            }
        }

    }

    public void createMineFile(String worldName, ArrayList<Location> region, Location Position1, Location Position2, HashMap<Material, Integer> blocktypes,Double resetTime,Integer resetPercentage) {
        for (Material material : blocktypes.keySet()) {
            mineFile.getFile().set("Block Types." + material.toString() + ".Percentage", blocktypes.get(material));
            mineFile.save();
        }
        mineFile.getFile().set("Mine Name", Name);
        mineFile.getFile().set("World Name", worldName);
        mineFile.getFile().set("PvP", false);
        mineFile.getFile().set("Reset Percentage", resetPercentage);
        mineFile.getFile().set("Reset Time", resetTime);
        mineFile.getFile().set("Region Locations.Point One.X", Position1.getX());
        mineFile.getFile().set("Region Locations.Point One.Y", Position1.getY());
        mineFile.getFile().set("Region Locations.Point One.Z", Position1.getZ());
        mineFile.getFile().set("Region Locations.Point Two.X", Position2.getX());
        mineFile.getFile().set("Region Locations.Point Two.Y", Position2.getY());
        mineFile.getFile().set("Region Locations.Point Two.Z", Position2.getZ());
        mineFile.save();
    }


    public void loadConfig() {
        if (mineFile.getFile().get("Mine Name") != null) {
            Name = mineFile.getFile().getString("Mine Name");
        }
        if (mineFile.getFile().get("World Name") != null) {
            world = mineFile.getFile().getString("World Name");
        }
        if (mineFile.getFile().get("PvP") != null) {
            pvpRule = mineFile.getFile().getBoolean("PvP");
        }
        if (mineFile.getFile().get("Spawn") != null) {
            if (mineFile.getFile().get("World Name") != null) {
                World world = Bukkit.getWorld(mineFile.getFile().getString("World Name"));
                double x;
                double y;
                double z;
                float yaw;
                float pitch;
                x = mineFile.getFile().getDouble("Spawn.X");
                y = mineFile.getFile().getDouble("Spawn.Y");
                z = mineFile.getFile().getDouble("Spawn.Z");
                yaw = (float) mineFile.getFile().getDouble("Spawn.Yaw");
                pitch = (float) mineFile.getFile().getDouble("Spawn.Pitch");
                Location sp = new Location(world, x, y, z, yaw, pitch);
                Spawn = sp;
            }
        }

        if (mineFile.getFile().get("Reset Time") != null) {
            ResetTime = mineFile.getFile().getDouble("Reset Time");
        }
        if (mineFile.getFile().get("Reset Percentage") != null) {
            ResetPercentage = mineFile.getFile().getInt("Reset Percentage");
        }
        if (mineFile.getFile().get("Last Reset Time") != null) {
            lastTime = mineFile.getFile().getDouble("Last Reset Time");
        }
        if (mineFile.getFile().get("Region Locations") != null) {
            Location location1 = new Location(Bukkit.getWorld(world), mineFile.getFile().getDouble("Region Locations.Point One.X"),
                    mineFile.getFile().getDouble("Region Locations.Point One.Y"), mineFile.getFile().getDouble("Region Locations.Point One.Z"));
            Location location2 = new Location(Bukkit.getWorld(world), mineFile.getFile().getDouble("Region Locations.Point Two.X"),
                    mineFile.getFile().getDouble("Region Locations.Point Two.Y"), mineFile.getFile().getDouble("Region Locations.Point Two.Z"));
            mineFile.getFile().getDouble("Region Locations.Point Two.X");
            PointOne = location1;
            PointTwo = location2;
            BlockLocation = RegionCreator.regionCreator(PointOne, PointTwo);
        }
        if (mineFile.getFile().get("Block Types") != null) {
            ConfigurationSection sections = mineFile.getFile().getConfigurationSection("Block Types");
            for (String material : sections.getKeys(false)) {
                if (Material.getMaterial(material) != null) {
                    BlockTypes.put(Material.getMaterial(material), mineFile.getFile().getInt("Block Types." + material + ".Percentage"));
                }
            }
        }
    }

    public void deletConfig() {
        mineFile.delete();
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getWorld() {
        return world;
    }

    public ArrayList<Location> getBlockLocation() {
        return BlockLocation;
    }

    public Location getSpawn() {
        return Spawn;
    }

    public void setSpawn(Location spawn) {
        Spawn = spawn;
        mineFile.getFile().set("Spawn.X", spawn.getX());
        mineFile.getFile().set("Spawn.Y", spawn.getY());
        mineFile.getFile().set("Spawn.Z", spawn.getZ());
        mineFile.getFile().set("Spawn.Yaw", spawn.getYaw());
        mineFile.getFile().set("Spawn.Pitch", spawn.getPitch());
        mineFile.save();
    }

    public Location getPointOne() {
        return PointOne;
    }

    public Location getPointTwo() {
        return PointTwo;
    }

    public boolean isPvpRule() {
        return pvpRule;
    }

    public void setPvpRule(boolean pvpRule) {
        this.pvpRule = pvpRule;
        mineFile.getFile().set("PvP", pvpRule);
        mineFile.save();
    }

    public double getResetTime() {
        return ResetTime;
    }

    public void setResetTime(double resetTime) {
        ResetTime = resetTime;
        mineFile.getFile().set("Reset Time", resetTime);
        mineFile.save();
    }

    public double getLastTime() {
        return lastTime;
    }

    public void setLastTime(double lastTime) {
        this.lastTime = lastTime;
        mineFile.getFile().set("Last Reset Time", lastTime);
        mineFile.save();
    }

    public HashMap<Material, Integer> getBlockTypes() {
        return BlockTypes;
    }

    public void setBlockTypes(HashMap<Material, Integer> blockTypes) {
        BlockTypes = blockTypes;
        mineFile.getFile().set("Block Types", null);
        for (Material material : BlockTypes.keySet()) {
            mineFile.getFile().set("Block Types." + material.toString() + ".Percentage", BlockTypes.get(material));
            mineFile.save();
        }

    }

    public Integer getResetPercentage() {
        return ResetPercentage;
    }

    public void setResetPercentage(Integer resetPercentage) {
        ResetPercentage = resetPercentage;
        mineFile.getFile().set("Reset Percentage", resetPercentage);
        mineFile.save();
    }
}
