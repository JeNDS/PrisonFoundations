package Me.JeNDS.Objects.MineObjects;

import Me.JeNDS.Main.PF;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;


public class PFSign {
    private Sign Sign;

    private SingType singType;
    private Integer updateTask;
    private Mine mine;
    private Location location;

    public PFSign(Mine mine, Location location,SingType singtype){
        this.mine = mine;
        this.location = location;
        this.singType = singtype;
        startUpdate();
    }
    private void startUpdate(){
       updateTask =  Bukkit.getScheduler().scheduleSyncRepeatingTask(PF.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(location.getBlock().getState() instanceof Sign sign){
                    if(singType.equals(SingType.MinePercentage)) {
                        sign.setLine(3, Presets.StandOutColor2+""+ mine.getMinePercentage() + "%");
                    }
                    if(singType.equals(SingType.MineTimeReset)) {
                        sign.setLine(3, Presets.StandOutColor2+""+mine.getTimeBeforeReset() + "M");
                    }
                    sign.update(true);
                }
            }
        },80L,80L);
    }

    public void stopUpdate(){
        if(updateTask!=null){
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
}
