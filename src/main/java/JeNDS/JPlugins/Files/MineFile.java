package JeNDS.JPlugins.Files;

import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Objects.MineObjects.*;
import JeNDS.JPlugins.Reagions.RegionCreator;
import JeNDS.JPlugins.Static.Catch;
import JeNDS.Plugins.PluginAPI.Files.YMLFile;
import JeNDS.Plugins.PluginAPI.JDAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class MineFile {
    private static PF plugin = PF.getPlugin(PF.class);
    private final YMLFile mineFile;
    private HashMap<Material, Integer> BlockTypes = new HashMap();
    private String Name;
    private String world;
    private ArrayList<Location> BlockLocation = new ArrayList<>();
    private Location Spawn;
    private Location PointOne;
    private Location PointTwo;
    private boolean pvpRule;
    private Integer ResetTime;
    private Integer ResetPercentage;
    private Integer lastTime;


    public MineFile(String mineName) {
        Name = mineName;
        mineFile = JDAPI.getFileManipulation.createFile("Mine Data", mineName + ".yml", PF.getInstance());
        loadConfig();
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
        if (plugin.getDataFolder().exists()) {
            File dir = new File(plugin.getDataFolder(), "Mine Data");
            if (dir.exists()) {
                File[] mineList = dir.listFiles();
                if (mineList != null) {
                    for (File mine : mineList) {
                        String name = mine.getName().replaceAll(".yml", "");
                        MineFile mineFile = new MineFile(name);
                        mineFile.loadConfig();
                        Mine mines = new Mine(name);
                        mines.loadMine();
                        mines.setMineSigns(mineFile.loadSigns());
                        mines.setHolograms(mineFile.loadHolograms());
                    }
                }
            }
        }


    }

    public void createMineFile(String worldName, ArrayList<Location> region, Location Position1, Location Position2, HashMap<Material, Integer> blocktypes, Integer resetTime, Integer resetPercentage) {
        for (Material material : blocktypes.keySet()) {
            mineFile.getFileConfiguration().set("Block Types." + material.toString() + ".Percentage", blocktypes.get(material));
            mineFile.save();
        }
        mineFile.getFileConfiguration().set("Mine Name", Name);
        mineFile.getFileConfiguration().set("World Name", worldName);
        mineFile.getFileConfiguration().set("PvP", false);
        mineFile.getFileConfiguration().set("Reset Percentage", resetPercentage);
        mineFile.getFileConfiguration().set("Reset Time", resetTime);
        mineFile.getFileConfiguration().set("Last Reset Time", resetTime);
        mineFile.getFileConfiguration().set("Region Locations.Point One.X", Position1.getX());
        mineFile.getFileConfiguration().set("Region Locations.Point One.Y", Position1.getY());
        mineFile.getFileConfiguration().set("Region Locations.Point One.Z", Position1.getZ());
        mineFile.getFileConfiguration().set("Region Locations.Point Two.X", Position2.getX());
        mineFile.getFileConfiguration().set("Region Locations.Point Two.Y", Position2.getY());
        mineFile.getFileConfiguration().set("Region Locations.Point Two.Z", Position2.getZ());
        mineFile.save();
    }


    public void loadConfig() {

        if (mineFile.getFileConfiguration().getString("Mine Name") != null) {
            Name = mineFile.getFileConfiguration().getString("Mine Name");
        }
        if (mineFile.getFileConfiguration().get("World Name") != null) {
            world = mineFile.getFileConfiguration().getString("World Name");
        }
        if (mineFile.getFileConfiguration().get("PvP") != null) {
            pvpRule = mineFile.getFileConfiguration().getBoolean("PvP");
        }
        if (mineFile.getFileConfiguration().get("Spawn") != null) {
            if (mineFile.getFileConfiguration().get("World Name") != null) {
                World world = Bukkit.getWorld(Objects.requireNonNull(mineFile.getFileConfiguration().getString("World Name")));
                double x;
                double y;
                double z;
                float yaw;
                float pitch;
                x = mineFile.getFileConfiguration().getDouble("Spawn.X");
                y = mineFile.getFileConfiguration().getDouble("Spawn.Y");
                z = mineFile.getFileConfiguration().getDouble("Spawn.Z");
                yaw = (float) mineFile.getFileConfiguration().getDouble("Spawn.Yaw");
                pitch = (float) mineFile.getFileConfiguration().getDouble("Spawn.Pitch");
                Location sp = new Location(world, x, y, z, yaw, pitch);
                Spawn = sp;
            }
        }

        if (mineFile.getFileConfiguration().get("Reset Time") != null) {
            ResetTime = mineFile.getFileConfiguration().getInt("Reset Time");
        }
        if (mineFile.getFileConfiguration().get("Reset Percentage") != null) {
            ResetPercentage = mineFile.getFileConfiguration().getInt("Reset Percentage");
        }
        if (mineFile.getFileConfiguration().get("Last Reset Time") != null) {
            lastTime = mineFile.getFileConfiguration().getInt("Last Reset Time");
        }
        if (mineFile.getFileConfiguration().get("Region Locations") != null) {
            Location location1 = new Location(Bukkit.getWorld(world), mineFile.getFileConfiguration().getDouble("Region Locations.Point One.X"),
                    mineFile.getFileConfiguration().getDouble("Region Locations.Point One.Y"), mineFile.getFileConfiguration().getDouble("Region Locations.Point One.Z"));
            Location location2 = new Location(Bukkit.getWorld(world), mineFile.getFileConfiguration().getDouble("Region Locations.Point Two.X"),
                    mineFile.getFileConfiguration().getDouble("Region Locations.Point Two.Y"), mineFile.getFileConfiguration().getDouble("Region Locations.Point Two.Z"));
            mineFile.getFileConfiguration().getDouble("Region Locations.Point Two.X");
            PointOne = location1;
            PointTwo = location2;
            BlockLocation = RegionCreator.regionCreator(PointOne, PointTwo);
        }
        if (mineFile.getFileConfiguration().get("Block Types") != null) {
            ConfigurationSection sections = mineFile.getFileConfiguration().getConfigurationSection("Block Types");
            for (String material : sections.getKeys(false)) {
                if (Material.getMaterial(material) != null) {
                    BlockTypes.put(Material.getMaterial(material), mineFile.getFileConfiguration().getInt("Block Types." + material + ".Percentage"));
                }
            }
        }


    }

    private ArrayList<PFSign> loadSigns() {
        ArrayList<PFSign> signs = new ArrayList<>();
        if (mineFile.getFileConfiguration().get("Signs") != null) {
            ConfigurationSection sections = mineFile.getFileConfiguration().getConfigurationSection("Signs");
            for (String id : sections.getKeys(false)) {
                String[] loc = mineFile.getFileConfiguration().getString("Signs." + id + ".Location").split(",");
                if (loc.length == 4) {
                    Location location = new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]));
                    PFSign sign = new PFSign(Name, location, UpdateType.GetUpdateTypeByName(mineFile.getFileConfiguration().getString("Signs." + id + ".Update Type")), UUID.fromString(id));
                    signs.add(sign);
                }
            }
        }
        return signs;
    }

    private ArrayList<PFHologram> loadHolograms() {
        ArrayList<PFHologram> pfHolograms = new ArrayList<>();
        if (mineFile.getFileConfiguration().get("Holograms") != null) {
            ConfigurationSection sections = mineFile.getFileConfiguration().getConfigurationSection("Holograms");
            for (String id : sections.getKeys(false)) {
                String[] loc = mineFile.getFileConfiguration().getString("Holograms." + id + ".Location").split(",");
                if (loc.length == 4) {
                    Location location = new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]));
                    PFHologram hologram = new PFHologram(location, Name, UpdateType.GetUpdateTypeByName(mineFile.getFileConfiguration().getString("Holograms." + id + ".Update Type")), UUID.fromString(id));
                    pfHolograms.add(hologram);
                }
            }
        }
        return pfHolograms;
    }

    public void deleteConfig() {
        mineFile.getFile().delete();
    }

    public void updateBlockTypes(ArrayList<BlockType> newBlockTypes) {
        for (BlockType blockType : newBlockTypes) {
            mineFile.getFileConfiguration().set("Block Types." + blockType.getMaterial().toString() + ".Percentage", blockType.getPercentage());
            mineFile.save();
        }
    }

    public void createSignFile(UUID ID, UpdateType updateType, Location location) {
        if (mineFile.getFileConfiguration().get("Signs." + ID) == null) {
            mineFile.getFileConfiguration().set("Signs." + ID + ".Location", location.getWorld().getName() + "," + location.toVector());
            mineFile.getFileConfiguration().set("Signs." + ID + ".Update Type", updateType.getName());
            mineFile.save();
        }
    }

    public void createHologramFile(UUID ID, UpdateType updateType, Location location) {
        if (mineFile.getFileConfiguration().get("Holograms." + ID) == null) {
            mineFile.getFileConfiguration().set("Holograms." + ID + ".Location", location.getWorld().getName() + "," + location.toVector());
            mineFile.getFileConfiguration().set("Holograms." + ID + ".Update Type", updateType.getName());
            mineFile.save();
        }
    }

    public void removeSignFile(UUID ID) {
        if (mineFile.getFileConfiguration().get("Signs." + ID) != null) {
            mineFile.getFileConfiguration().set("Signs." + ID, null);
            mineFile.save();
        }
    }

    public void removeHologramFile(UUID ID) {
        if (mineFile.getFileConfiguration().get("Holograms." + ID) != null) {
            mineFile.getFileConfiguration().set("Holograms." + ID, null);
            mineFile.save();
        }
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
        mineFile.getFileConfiguration().set("Spawn.X", spawn.getX());
        mineFile.getFileConfiguration().set("Spawn.Y", spawn.getY());
        mineFile.getFileConfiguration().set("Spawn.Z", spawn.getZ());
        mineFile.getFileConfiguration().set("Spawn.Yaw", spawn.getYaw());
        mineFile.getFileConfiguration().set("Spawn.Pitch", spawn.getPitch());
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
        mineFile.getFileConfiguration().set("PvP", pvpRule);
        mineFile.save();
    }

    public Integer getResetTime() {
        return ResetTime;
    }

    public void setResetTime(Integer resetTime) {
        ResetTime = resetTime;
        mineFile.getFileConfiguration().set("Reset Time", resetTime);
        mineFile.save();
    }

    public Integer getLastTime() {
        return lastTime;
    }

    public void setLastTime(Integer lastTime) {
        this.lastTime = lastTime;
        mineFile.getFileConfiguration().set("Last Reset Time", lastTime);
        mineFile.save();
    }

    public HashMap<Material, Integer> getBlockTypes() {
        return BlockTypes;
    }


    public Integer getResetPercentage() {
        return ResetPercentage;
    }

    public void setResetPercentage(Integer resetPercentage) {
        ResetPercentage = resetPercentage;
        mineFile.getFileConfiguration().set("Reset Percentage", resetPercentage);
        mineFile.save();
    }
}
