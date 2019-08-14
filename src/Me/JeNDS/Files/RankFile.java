package Me.JeNDS.Files;

import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RankFile {
    private static HashMap<String, String> prefix = new HashMap<>();
    private static HashMap<String, String> nextRank = new HashMap<>();
    private static HashMap<String, Boolean> finalRank = new HashMap<>();
    private static HashMap<String, List> rewards = new HashMap<>();
    private static HashMap<String, Double> price = new HashMap<>();
    private static FileMaker rankFile;

    public RankFile() {
        FileMaker folder = new FileMaker("Ranks Data", true);
        {
            folder.create();
        }
        rankFile = new FileMaker("Ranks", true, "Ranks Data");
        {
            if (rankFile.getRawFile().length() == 0) {
                rankFile.create();
                createRankFile();
                loadRankFile();
            } else {
                loadRankFile();
            }
        }
    }

    public static String FindPlayerRank(Player player) {
        RankFile rankFile = new RankFile();
        ConfigurationSection section = rankFile.getRankFile().getFile().getConfigurationSection("");
        String Rank = null;
        for (String s : section.getKeys(false)) {
            if (player.hasPermission("PF.Ranks." + s)) {
                Rank = s;
            }
        }
        return Rank;
    }

    public static void PlayerRewardsCommands(Player player) {
        String Rank = FindPlayerRank(player);
        if (Rank != null) {
            if (!getRewards(Rank).isEmpty()) {
                ArrayList<String> rs = new ArrayList<>();
                rs.addAll(getRewards(Rank));
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
                            if(found) {
                                thaNew = thaNew + "" + sp;
                            }
                            else {
                                thaNew = thaNew + " " + sp;
                            }
                        } else {
                            thaNew = sp;
                        }
                    }
                    if (broadcast) {
                        Bukkit.broadcastMessage(replacer(thaNew, player, Rank));
                    }
                    if (message) {
                        player.sendMessage(replacer(thaNew, player, Rank));
                    }
                    if (command) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), replacer(thaNew, player, Rank));
                    }
                }
            }
        }
    }

    private static String replacer(String replacer, Player player, String Rank) {

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
                    temp = temp + " " + sp.replaceAll("%nextRank%", getNextRank(Rank));
                } else {
                    temp = sp.replaceAll("%nextRank%", getNextRank(Rank));
                }
            } else if (sp.contains("%rank%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%rank%", Rank);

                } else {
                    temp = sp.replaceAll("%rank%", Rank);
                }
            } else if (sp.contains("%rankPrefix%")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%rankPrefix%", Presets.ColorReplacer(getPrefix(Rank)));
                } else {
                    temp = sp.replaceAll("%rankPrefix%", Presets.ColorReplacer(getPrefix(Rank)));
                }
            } else if (sp.contains("%nextRankPrefix")) {
                if (temp.length() > 1) {
                    temp = temp + " " + sp.replaceAll("%nextRankPrefix%", Presets.ColorReplacer(getPrefix(getNextRank(Rank))));
                } else {
                    temp = sp.replaceAll("%nextRankPrefix%", Presets.ColorReplacer(getPrefix(getNextRank(Rank))));
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

    public static String getPrefix(String Rank) {
        if (prefix.containsKey(Rank)) {
            return prefix.get(Rank);
        } else return new String("");
    }

    public static void setPrefix(String Rank, String Prefix) {
        if (prefix.containsKey(Rank)) {
            prefix.remove(Rank);
            prefix.put(Rank, Prefix);
        } else prefix.put(Rank, Prefix);
    }

    public static String getNextRank(String Rank) {
        if (nextRank.containsKey(Rank)) {
            return nextRank.get(Rank);
        } else return new String("");
    }

    public static void setNextRank(String Rank, String NextRank) {
        if (nextRank.containsKey(Rank)) {
            nextRank.remove(Rank);
            nextRank.put(Rank, NextRank);
        } else nextRank.put(Rank, NextRank);
    }

    public static boolean isLastRank(String Rank) {
        if (finalRank.containsKey(Rank)) {
            return finalRank.get(Rank);
        } else return false;
    }

    public static void setFinalRank(String Rank, boolean isFinalRank) {
        if (finalRank.containsKey(Rank)) {
            finalRank.remove(Rank);
            finalRank.put(Rank, isFinalRank);
        } else finalRank.put(Rank, isFinalRank);
    }

    public static List getRewards(String Rank) {
        if (rewards.containsKey(Rank)) {
            return rewards.get(Rank);
        } else return new ArrayList();
    }

    public static void setRewards(String Rank, List Rewards) {
        if (rewards.containsKey(Rank)) {
            rewards.remove(Rank);
            rewards.put(Rank, Rewards);
        } else rewards.put(Rank, Rewards);
    }

    public static double getPrice(String Rank) {
        if (price.containsKey(Rank)) {
            return price.get(Rank);
        } else return 0.0;
    }

    public static void setPrice(String Rank, Double Price) {
        if (price.containsKey(Rank)) {
            price.remove(Rank);
            price.put(Rank, Price);
        } else price.put(Rank, Price);
    }

    public void createRankFile() {
        String name;
        name = "A";
        rankFile.getFile().set(name + ".Prefix", "&6A");
        rankFile.getFile().set(name + ".Next Rank", "B");
        rankFile.getFile().set(name + ".Rankup Price", 2000);
        ArrayList<String> commandsa = new ArrayList<>();
        ArrayList<String> commandsb = new ArrayList<>();
        commandsa.add("[Broadcast] &A %player% rank up to %nextRank%");
        commandsa.add("[Command] lp user %player% permission set PF.Ranks.%nextRank% true");
        rankFile.getFile().set(name + ".Rankup rewards", commandsa);
        rankFile.save();
        name = "B";
        rankFile.getFile().set(name + ".Prefix", "&7B");
        rankFile.getFile().set(name + ".Next Rank", "C");
        rankFile.getFile().set(name + ".Rankup Price", 4000);
        commandsb.add("[Broadcast] &A %player% rank up to %nextRank%");
        commandsb.add("[Command] lp user %player% permission set PF.Ranks.%nextRank% true");
        rankFile.getFile().set(name + ".Rankup rewards", commandsb);
        rankFile.save();
        name = "C";
        rankFile.getFile().set(name + ".Prefix", "&8C");
        rankFile.getFile().set(name + ".Final Rank", true);
        rankFile.save();
    }

    public void loadRankFile() {
        prefix = new HashMap<>();
        nextRank = new HashMap<>();
        finalRank = new HashMap<>();
        rewards = new HashMap<>();
        price = new HashMap<>();
        ConfigurationSection section = rankFile.getFile().getConfigurationSection("");
        for (String rank : section.getKeys(false)) {
            if (rankFile.getFile().get(rank + ".Prefix") != null) {
                prefix.put(rank, rankFile.getFile().getString(rank + ".Prefix"));
            } else {
                prefix.put(rank, rank);
            }
            if (rankFile.getFile().get(rank + ".Next Rank") != null) {
                nextRank.put(rank, rankFile.getFile().getString(rank + ".Next Rank"));
            } else {
                nextRank.put(rank, rank);
            }
            if (rankFile.getFile().get(rank + ".Rankup Price") != null) {
                price.put(rank, rankFile.getFile().getDouble(rank + ".Rankup Price"));
            } else {
                price.put(rank, 0.0);
            }
            if (rankFile.getFile().get(rank + ".Final Rank") != null) {
                finalRank.put(rank, rankFile.getFile().getBoolean(rank + ".Final Rank"));
            } else {
                finalRank.put(rank, false);
            }
            if (rankFile.getFile().get(rank + ".Rankup rewards") != null) {
                rewards.put(rank, rankFile.getFile().getStringList(rank + ".Rankup rewards"));
            } else {
                rewards.put(rank, new ArrayList<String>());
            }
        }
    }

    public FileMaker getRankFile() {
        return rankFile;
    }


}
