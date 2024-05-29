package JeNDS.Plugins.PrisonFundations.Mines.Utilities.BlockUtility;


import JeNDS.Plugins.JeNDSAPI.Holograms.JDHologram;
import JeNDS.Plugins.JeNDSAPI.Holograms.JDHologramSpace;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.Files.UtilitiesFile;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Location;

import java.util.UUID;

public class BlockUtility {
    private final Location blockLocation;
    private UUID ID = UUID.randomUUID();
    private BlockUtilityType type;
    private String name = Presets.MainColor;
    private JDHologram hologram;

    public BlockUtility(Location blockLocation, BlockUtilityType type) {
        this.blockLocation = blockLocation;
        setType(type);
        createHologram();
    }

    public BlockUtility(Location blockLocation, BlockUtilityType type, UUID ID) {
        this.ID = ID;
        this.blockLocation = blockLocation;
        setType(type);
        createHologram();
    }

    public BlockUtility(Location blockLocation) {
        this.blockLocation = blockLocation;
        createHologram();
    }


    public static BlockUtility BlockUtilityFromLocation(Location location) {
        for (BlockUtility blockUtility : Catch.blockUtilities) {
            if (blockUtility.blockLocation.equals(location)) {
                return blockUtility;
            }
        }
        return null;
    }

    public void remove() {
        hologram.removeHologram();
        UtilitiesFile.RemoveBlockUtility(this);
        Catch.blockUtilities.remove(this);
    }

    public void createHologram() {
        String[] lines = new String[1];
        lines[0] = name;
        Location location = blockLocation.clone();
        this.hologram = new JDHologram(location, lines, 1, JDHologramSpace.SMALL);
    }

    public BlockUtilityType getType() {
        return type;
    }

    public void setType(BlockUtilityType type) {
        if (type.equals(BlockUtilityType.AUTOBLOCK)) {
            name = name + "Auto Blocker";
        }
        if (type.equals(BlockUtilityType.AUTOSMELT)) {
            name = name + "Auto Smelter";
        }
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getBlockLocation() {
        return blockLocation;
    }

    public UUID getID() {
        return ID;
    }
}
