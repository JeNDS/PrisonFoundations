package JeNDS.JPlugins.Mines.MineObjects;

import JeNDS.JPlugins.PF;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.UUID;


public class PFSign {
    private Sign Sign;
    private UUID ID = UUID.randomUUID();
    private final UpdateType singType;
    private Integer updateTask;
    private final String mineName;
    private Location location;

    public PFSign(String mineName, Location location, UpdateType singType, UUID ID) {
        if(ID!=null) this.ID = ID;
        this.mineName = mineName;
        this.location = location;
        this.singType = singType;
        startUpdate();
    }



    public void delete(){
        stopUpdate();
        Block block = location.getBlock();
        block.setType(Material.AIR);
    }

    private void startUpdate() {
        updateTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(PF.getInstance(), new Runnable() {
            @Override
            public void run() {
                Mine mine = Mine.GetMineFromName(mineName);
                if (mine != null) {
                    if (location.getBlock().getState() instanceof Sign sign) {
                        if (singType.equals(UpdateType.MINE_PERCENTAGE)) {
                            sign.setLine(3, Presets.ThirdColor + "" + mine.getMinePercentage() + "%");
                        }
                        if (singType.equals(UpdateType.MINE_TIME_RESET)) {
                            sign.setLine(3, Presets.ThirdColor + "" + mine.getTimeBeforeReset() + "M");
                        }
                        sign.update(true);
                    }
                }
            }
        }, 80L, 80L);
    }

    public void stopUpdate() {
        if (updateTask != null) {
            Bukkit.getScheduler().cancelTask(updateTask);
        }
    }

    public Sign getSign() {
        return Sign;
    }

    public void setSign(Sign sign) {
        Sign = sign;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public UUID getID() {
        return ID;
    }

    public UpdateType getSingType() {
        return singType;
    }

    public String getMineName() {
        return mineName;
    }
}
