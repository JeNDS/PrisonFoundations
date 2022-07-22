package JeNDS.JPlugins.PlayerData;

import JeNDS.JPlugins.PlayerData.Files.PlayerDataFile;
import JeNDS.JPlugins.Static.Catch;

import java.util.ArrayList;
import java.util.UUID;

public class PFPlayer {
    private UUID uuid;
    private ArrayList<Multiplier> multipliers = new ArrayList<>();

    public PFPlayer(UUID uuid) {
        this.uuid = uuid;
        if (!Catch.PFPlayers.contains(this)) {
            Catch.PFPlayers.add(this);
        }
    }

    public static PFPlayer GetPFPlayer(UUID uuid) {
        for (PFPlayer pfPlayer : Catch.PFPlayers) {
            if (pfPlayer.uuid.equals(uuid)) {
                return pfPlayer;
            }
        }
        return null;
    }

    public void addMultiplier(Multiplier multiplier) {
        multipliers.add(multiplier);
        PlayerDataFile.AddMultiplierToFile(multiplier);

    }

    public void removeMultiplier(UUID uuid) {
        for (Multiplier multiplier : multipliers) {
            if (multiplier.getID().equals(uuid)) {
                multiplier.stopTimer();
                PlayerDataFile.RemoveMultiplierToFile(multiplier);
            }
        }
        multipliers.removeIf(multiplier -> multiplier.getID().equals(uuid));

    }

    public void clearMultipliers() {
        for (Multiplier multiplier : multipliers) {
            multiplier.stopTimer();
            PlayerDataFile.RemoveMultiplierToFile(multiplier);
        }
        multipliers = new ArrayList<>();
    }

    public double getFinalMultiplier() {
        double multiplier = 1.0;
        for (Multiplier multiplier1 : multipliers) {
            multiplier = multiplier * multiplier1.getAmount();
        }
        return multiplier;
    }

    public void setMultipliers(ArrayList<Multiplier> multipliers) {
        this.multipliers = multipliers;
    }
}
