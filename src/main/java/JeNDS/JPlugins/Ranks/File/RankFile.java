package JeNDS.JPlugins.Ranks.File;

import JeNDS.JPlugins.PF;
import JeNDS.JPlugins.Ranks.Rank;
import JeNDS.JPlugins.Static.Catch;
import JeNDS.Plugins.PluginAPI.Files.YMLFile;
import JeNDS.Plugins.PluginAPI.JDAPI;
import JeNDS.Plugins.PluginAPI.Other.JTools;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class RankFile {

    public static void LoadRanks() {
        YMLFile ranksFile = JDAPI.getFileManipulation.copyFile("Ranks.yml", PF.getInstance());
        ConfigurationSection section = ranksFile.getFileConfiguration().getConfigurationSection("");
        assert section != null;
        for (String rankName : section.getKeys(false)) {
            String prefix = "";
            int priority = 1;
            double rankUpPrice = 0.0;
            ArrayList<String> rewards = new ArrayList<>();
            boolean lastRank = false;
            if (ranksFile.getFileConfiguration().get(rankName + ".Prefix") != null) {
                prefix = ranksFile.getFileConfiguration().getString(rankName + ".Prefix");
                assert prefix != null;
                prefix = JTools.FormatString(prefix);
            }
            if (ranksFile.getFileConfiguration().get(rankName + ".Priority") != null) {
                priority = ranksFile.getFileConfiguration().getInt(rankName + ".Priority");
            }
            if (ranksFile.getFileConfiguration().get(rankName + ".RankUp Price") != null) {
                rankUpPrice = ranksFile.getFileConfiguration().getDouble(rankName + ".RankUp Price");
            }
            if (ranksFile.getFileConfiguration().get(rankName + ".Final Rank") != null) {
                lastRank = ranksFile.getFileConfiguration().getBoolean(rankName + ".Final Rank");
            }
            if (ranksFile.getFileConfiguration().get(rankName + ".RankUp Rewards") != null) {
                rewards = new ArrayList<>(ranksFile.getFileConfiguration().getStringList(rankName + ".RankUp Rewards"));
            }
            Rank rank = new Rank(rankName, priority, prefix, lastRank, rankUpPrice, rewards);
            if (!Catch.Ranks.contains(rank)) {
                Catch.Ranks.add(rank);
            }
        }
    }


    public static void ReloadRanks() {
        Catch.Ranks = new ArrayList<>();
        LoadRanks();
    }


}
