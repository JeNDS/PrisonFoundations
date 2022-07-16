package JeNDS.JPlugins.Objects.MineObjects;

import JeNDS.Plugins.PluginAPI.Holograms.JDHologram;
import JeNDS.Plugins.PluginAPI.Holograms.JDHologramSpace;
import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

public class PFHologram extends JDHologram {

    private final String mineName;
    private final UpdateType updateType;

    private  UUID id = UUID.randomUUID();
    private Integer task;



    public PFHologram(Location location, String mineName, UpdateType updateType, UUID uuid) {
        super(location, lines(mineName,updateType), 1.5, JDHologramSpace.SMALL);
        this.updateType = updateType;
        this.mineName = mineName;
        if(uuid!=null) this.id = uuid;
        startUpdater();
    }



    private void startUpdater(){
       task = Bukkit.getScheduler().scheduleSyncRepeatingTask(PF.getInstance(), new Runnable() {
            @Override
            public void run() {
                updateHologram();
            }
        },80L,80L);
    }

    @Override
    public void removeHologram() {
        super.removeHologram();
        if(task!=null){
            Bukkit.getScheduler().cancelTask(task);
        }
    }

    private void updateHologram(){
        Mine mine = Mine.GetMineFromName(mineName);
        if(mine!=null) {
            ArmorStand armorStand = getArmorStands().get(2);
            if (updateType.equals(UpdateType.MINE_PERCENTAGE)) {
                armorStand.setCustomName(Presets.StandOutColor2 + "" + mine.getMinePercentage() + "%");
            }
            if (updateType.equals(UpdateType.MINE_TIME_RESET)) {
                armorStand.setCustomName(Presets.StandOutColor2 + "" + mine.getTimeBeforeReset() + "M");
            }
        }

    }
    private  static String[] lines(String mineName, UpdateType updateType) {
        Mine mine = Mine.GetMineFromName(mineName);
        String[] lines = new String[4];
        lines[0] = Presets.DefaultColor + "" + mine.getName();
        lines[1] = Presets.StandOutColor + "" + updateType.getName();
        lines[2] = "";
        if (updateType.equals(UpdateType.MINE_PERCENTAGE)) {
            lines[3] = Presets.StandOutColor2 + "" + mine.getMinePercentage() + "%";
        }
        if (updateType.equals(UpdateType.MINE_TIME_RESET)) {
            lines[3] = Presets.StandOutColor2 + "" + mine.getTimeBeforeReset() + "M";
        }
        return lines;
    }

    public UUID getId() {
        return id;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }
}
