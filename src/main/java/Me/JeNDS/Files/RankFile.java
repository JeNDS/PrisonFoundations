package Me.JeNDS.Files;

import JeNDS.Plugins.PluginAPI.Files.YMLFile;
import JeNDS.Plugins.PluginAPI.JDAPI;
import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.Rank;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RankFile {

    private static YMLFile ranksFile = JDAPI.getFileManipulation().copyFile("Ranks.yml", PF.getInstance());

    public static void LoadRanks(){
        ConfigurationSection section = ranksFile.getFileConfiguration().getConfigurationSection("");
        assert section != null;
        for (String rankName : section.getKeys(false)) {
            String prefix = "";
            int priority = 1;
            double rankUpPrice = 0.0;
            ArrayList<String> rewards = new ArrayList<>();
            boolean lastRank = false;
            if (ranksFile.getFileConfiguration().get(rankName + ".Prefix") != null) {
               prefix =  ranksFile.getFileConfiguration().getString(rankName + ".Prefix");
            }
            if (ranksFile.getFileConfiguration().get(rankName + ".Next Rank") != null) {
                priority = ranksFile.getFileConfiguration().getInt(rankName + ".Priority");
            }
            if (ranksFile.getFileConfiguration().get(rankName + ".RankUp Price") != null) {
                rankUpPrice = ranksFile.getFileConfiguration().getDouble(rankName + ".RankUp Price");
            }
            if (ranksFile.getFileConfiguration().get(rankName + ".Final Rank") != null) {
                lastRank = ranksFile.getFileConfiguration().getBoolean(rankName + ".Final Rank");
            }
            if (ranksFile.getFileConfiguration().get(rankName + ".RankUp rewards") != null) {
                rewards = new ArrayList<>(ranksFile.getFileConfiguration().getStringList(rankName + ".RankUp rewards"));
            }
            Rank rank = new Rank(rankName,priority,prefix,lastRank,rankUpPrice,rewards);
            if(!Catch.Ranks.contains(rank)){
                Catch.Ranks.add(rank);
            }
        }
    }


    public static void RunPlayerCommands(Player player) {
        Rank rank = Rank.GetPlayerRank(player);
        if (!rank.getRankRewards().isEmpty()) {
            ArrayList<String> rs = new ArrayList<>(rank.getRankRewards());
            for (String s : rs) {
                boolean command = false;
                boolean message = false;
                boolean broadcast = false;
                String[] splits = s.split("\\s");
                String thaNew = "";
                for (String sp : splits) {
                    boolean found = false;
                    if (sp.contains("[Command]")) {
                        found = true;
                        command = true;
                        sp = sp.replace("[Command]", "");
                    }
                    if (sp.contains("[Message]")) {
                        found = true;
                        message = true;
                        sp = sp.replace("[Message]", "");
                    }
                    if (sp.contains("[Broadcast]")) {
                        found = true;
                        broadcast = true;
                        sp = sp.replace("[Broadcast]", "");
                    }
                    if (thaNew.length() > 1) {
                        if (found) {
                            thaNew = thaNew + "" + sp;
                        } else {
                            thaNew = thaNew + " " + sp;
                        }
                    } else {
                        thaNew = sp;
                    }
                }
                if (broadcast) {
                    Bukkit.broadcastMessage(replacer(thaNew, player, rank));
                }
                if (message) {
                    player.sendMessage(replacer(thaNew, player, rank));
                }
                if (command) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), replacer(thaNew, player, rank));
                }
            }
        }

    }

    private static String replacer(String replacer, Player player, Rank rank) {

        String temp = "";
        String[] split = replacer.split("\\s");
        for (String sp : split) {
            if (sp.contains("%player%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%player%", player.getName());
                } else {
                    temp = sp.replaceAll("%player%", player.getName());
                }
            } else if (sp.contains("%nextRank%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%nextRank%", Rank.GetPlayerNextRank(player).getRankName());
                } else {
                    temp = sp.replaceAll("%nextRank%", Rank.GetPlayerNextRank(player).getRankName());
                }
            } else if (sp.contains("%rank%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%rank%", rank.getRankName());

                } else {
                    temp = sp.replaceAll("%rank%", rank.getRankName());
                }
            } else if (sp.contains("%rankPrefix%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%rankPrefix%", Presets.ColorReplacer(rank.getPrefix()));
                } else {
                    temp = sp.replaceAll("%rankPrefix%", Presets.ColorReplacer(rank.getPrefix()));
                }
            } else if (sp.contains("%nextRankPrefix")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%nextRankPrefix%", Presets.ColorReplacer(Rank.GetPlayerNextRank(player).getPrefix()));
                } else {
                    temp = sp.replaceAll("%nextRankPrefix%", Presets.ColorReplacer(Rank.GetPlayerNextRank(player).getPrefix()));
                }
            } else {
                if (temp.length() > 1) {
                    temp = temp + " " + sp;
                } else {
                    temp = sp;
                }
            }
        }
        return Presets.ColorReplacer(temp);
    }


}
