package JeNDS.Plugins.PrisonFundations.PlayerData;

import JeNDS.Plugins.PrisonFundations.PlayerData.Files.PlayerDataFile;
import JeNDS.Plugins.PrisonFundations.Static.Catch;

import java.util.ArrayList;
import java.util.UUID;

public class PFPlayer {
    private final UUID uuid;
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
        return new PFPlayer(uuid);
    }

    public void addMultiplier(Multiplier multiplier) {
        multipliers.add(multiplier);
        PlayerDataFile.AddMultiplierToFile(multiplier);

    }

    public void setMultiplier(Multiplier multiplier) {
        multipliers.clear();
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
        double multiplier = 0.0;
        for (Multiplier multiplier1 : multipliers) {
            multiplier = multiplier + multiplier1.getAmount();
        }
        return multiplier;
    }

    public void setMultipliers(ArrayList<Multiplier> multipliers) {
        this.multipliers = multipliers;
    }
}
