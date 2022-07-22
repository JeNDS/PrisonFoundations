package JeNDS.JPlugins.Commands;

import JeNDS.JPlugins.Managers.CommandManager;
import JeNDS.JPlugins.PlayerData.Files.PlayerDataFile;
import JeNDS.JPlugins.PlayerData.Multiplier;
import JeNDS.JPlugins.PlayerData.PFPlayer;
import JeNDS.JPlugins.Static.Presets;
import JeNDS.Plugins.PluginAPI.Other.JDItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.Time;
import java.util.Objects;

public class MultiplierCommands extends CommandManager {

    public static boolean LoadMultipliers() {
        if (args.length >= 1 && args[0].equalsIgnoreCase("multiplier")) {
            if (args.length > 1) {
                if (give()) return true;
                if (set()) return true;
                if (Help()) return true;
            }
            sender.sendMessage(defaultColor + "/PF multiplier Help");
            return true;

        }
        return false;
    }

    private static boolean give() {

        if (args[1].equalsIgnoreCase("give")) {
            if (args.length > 2) {
                if (Bukkit.getPlayer(args[2]) != null) {
                    if (args.length >= 5) {
                        double amount = Double.parseDouble(args[3]);
                        boolean permanent = false;
                        int days = 0;
                        Time time = new Time(0,0,0);
                        if (args[4].equalsIgnoreCase("permanent")) permanent = true;
                        else {
                            String[] timeSplit  = args[4].split(":");
                            if(timeSplit.length == 4){
                                days = Integer.parseInt(timeSplit[0]);
                                int hours = Integer.parseInt(timeSplit[1]);
                                int minutes = Integer.parseInt(timeSplit[2]);
                                int seconds = Integer.parseInt(timeSplit[3]);
                                time = new Time(hours,minutes,seconds);

                            }
                            else {
                                sender.sendMessage(defaultColor + "/PF multiplier give {player} {amount} {0:0:0:0}:{permanent}");
                                return true;
                            }
                        }
                        Player player = Bukkit.getPlayer(args[2]);
                        assert player != null;
                        Multiplier multiplier = new Multiplier(player.getUniqueId(),amount,permanent,time,days);
                        PFPlayer pfPlayer = PFPlayer.GetPFPlayer(Objects.requireNonNull(Bukkit.getPlayer(args[2])).getUniqueId());
                        if (pfPlayer != null) {
                            pfPlayer.addMultiplier(multiplier);
                        }
                        else {
                            pfPlayer = new PFPlayer(player.getUniqueId());
                            pfPlayer.addMultiplier(multiplier);
                        }
                        if(permanent) {
                            sender.sendMessage(defaultColor + "You have gave " + args[2] + " a permanent multiplier of " + amount + "X");
                        }
                        else {
                            sender.sendMessage(defaultColor + "You have gave " + args[2] + " a multiplier of " + amount + "X for "
                                    + multiplier.getDays() + "Days " + multiplier.getTime().getHours() + "Hours " + multiplier.getTime().getMinutes() + "Minutes " + multiplier.getTime().getSeconds() + "Seconds");
                        }
                    } else {
                        sender.sendMessage(defaultColor + "/PF multiplier give {player} {amount} {0:0:0:0}:{permanent}");
                    }
                } else {
                    sender.sendMessage(defaultColor + args[2] + " is not a valid player!");
                }
            } else {
                sender.sendMessage(defaultColor + "/PF multiplier give {player} {amount} {0:0:0:0}:{permanent}");
            }
            return true;
        } else {
            return false;
        }

    }

    private static boolean set() {
        if (args[1].equalsIgnoreCase("clear")) {
            if (args.length > 2) {
                if (Bukkit.getPlayer(args[2]) != null) {
                        PFPlayer pfPlayer = PFPlayer.GetPFPlayer(Bukkit.getPlayer(args[2]).getUniqueId());
                        if (pfPlayer != null) {
                            pfPlayer.clearMultipliers();
                        }
                    sender.sendMessage(defaultColor +"You have clear all multipliers from "+ args[2]);
                } else {
                    sender.sendMessage(defaultColor + args[2] + " is not a valid player!");
                }
            } else {
                sender.sendMessage(defaultColor + "/PF multiplier clear {player}");
            }
            return true;
        } else {
            return false;
        }

    }

    private static boolean Help() {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("help")) {
                sender.sendMessage(defaultColor + "/PF multiplier give {player} {amount} {0:0:0:0}:{permanent}");
                sender.sendMessage(defaultColor + "/PF multiplier clear {player}");
                return true;
            }
        }
        return false;
    }

}
