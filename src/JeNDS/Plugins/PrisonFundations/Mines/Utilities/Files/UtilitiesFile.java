package JeNDS.Plugins.PrisonFundations.Mines.Utilities.Files;


import JeNDS.Plugins.JeNDSAPI.Files.YMLFile;
import JeNDS.Plugins.JeNDSAPI.JDAPI;
import JeNDS.Plugins.PrisonFundations.Main;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.BlockUtility.BlockUtility;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.BlockUtility.BlockUtilityType;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class UtilitiesFile {

    private static YMLFile utilityFile;

    public static void LoadUtilities() {
        utilityFile = JDAPI.getFileManipulation.createFile("Utilities.yml", Main.getInstance());
        ConfigurationSection section = utilityFile.getFileConfiguration().getConfigurationSection("");
        assert section != null;
        for (String utility : section.getKeys(false)) {
            LoadUtility(utility);
        }
    }

    public static void ReloadUtilities() {
        Catch.blockUtilities = new ArrayList<>();
        LoadUtilities();
    }

    private static void LoadUtility(String ID) {
        List<String> loc = Arrays.asList(utilityFile.getFileConfiguration().getString(ID + ".Location").split(","));
        if (loc.size() == 4) {
            World world = Bukkit.getWorld(loc.get(0));
            double x = Double.parseDouble(loc.get(1));
            double y = Double.parseDouble(loc.get(2));
            double z = Double.parseDouble(loc.get(3));
            Location location = new Location(world, x, y, z);
            BlockUtilityType type = BlockUtilityType.valueOf(utilityFile.getFileConfiguration().getString(ID + ".Utility Type"));
            BlockUtility blockUtility = new BlockUtility(location, type, UUID.fromString(ID));
            if (!Catch.blockUtilities.contains(blockUtility)) {
                Catch.blockUtilities.add(blockUtility);
            }
        }
    }


    public static void saveUtility(BlockUtility blockUtility) {
        utilityFile.getFileConfiguration().set(blockUtility.getID() + ".Utility Type", blockUtility.getType().toString());
        utilityFile.getFileConfiguration().set(blockUtility.getID() + ".Location", Objects.requireNonNull(blockUtility.getBlockLocation().getWorld()).getName() + "," + blockUtility.getBlockLocation().toVector());
        utilityFile.save();
    }

    public static void RemoveBlockUtility(BlockUtility blockUtility) {
        if (utilityFile.getFileConfiguration().get(blockUtility.getID().toString()) != null) {
            utilityFile.getFileConfiguration().set(blockUtility.getID().toString(), null);
            utilityFile.save();
        }
    }

}
