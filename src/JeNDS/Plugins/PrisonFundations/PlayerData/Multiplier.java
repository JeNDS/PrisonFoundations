package JeNDS.Plugins.PrisonFundations.PlayerData;

import JeNDS.Plugins.PrisonFundations.Main;
import org.bukkit.Bukkit;

import java.sql.Time;
import java.util.UUID;

public class Multiplier {
    private final UUID playerId;
    private final UUID ID;
    private final double amount;
    private final boolean permanent;
    private Time time; // 0:0:0:0
    private int days;
    private int taskID;

    public Multiplier(UUID playerId, double amount, boolean permanent, Time time, int days) {
        this.playerId = playerId;
        this.ID = UUID.randomUUID();
        this.time = time;
        this.amount = amount;
        this.permanent = permanent;
        this.days = days;
        if (!permanent) runTimer();
    }

    public Multiplier(String playerId, String ID, double amount, boolean permanent, String time) {
        this.playerId = UUID.fromString(playerId);
        this.ID = UUID.fromString(ID);
        String[] split = time.split(":");
        if (split.length == 4) {
            this.days = Integer.parseInt(split[0]);
            this.time = new Time(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
        }
        this.amount = amount;
        this.permanent = permanent;
        if (!permanent) runTimer();
    }

    public void stopTimer() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public void runTimer() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            @Override
            public void run() {
                if (time.getTime() - 1000 <= 0) {
                    if (days >= 1) {
                        days = days - 1;
                        time = new Time(23, 59, 59);
                    } else {
                        PFPlayer player = PFPlayer.GetPFPlayer(playerId);
                        if (player != null) {
                            player.removeMultiplier(ID);
                        }
                        stopTimer();
                    }
                } else {
                    time.setTime(time.getTime() - 1000);
                }
            }
        }, 0L, 20L);
    }

    public UUID getID() {
        return ID;
    }

    public Time getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }

    public int getDays() {
        return days;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
