package JeNDS.Plugins.PrisonFundations.Commands.PFCommand;


import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.PlayerData.Multiplier;
import JeNDS.Plugins.PrisonFundations.PlayerData.PFPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Time;
import java.util.Objects;

public class MultiplierCommands extends CommandManager {

    public static boolean LoadMultipliers() {
        if (cmArgs.length >= 1 && cmArgs[0].equalsIgnoreCase("multiplier")) {
            if (cmArgs.length > 1) {
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

        if (cmArgs[1].equalsIgnoreCase("give")) {
            if (cmArgs.length > 2) {
                if (Bukkit.getPlayer(cmArgs[2]) != null) {
                    if (cmArgs.length >= 5) {
                        double amount = Double.parseDouble(cmArgs[3]);
                        boolean permanent = false;
                        int days = 0;
                        Time time = new Time(0,0,0);
                        if (cmArgs[4].equalsIgnoreCase("permanent")) permanent = true;
                        else {
                            String[] timeSplit  = cmArgs[4].split(":");
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
                        Player player = Bukkit.getPlayer(cmArgs[2]);
                        assert player != null;
                        Multiplier multiplier = new Multiplier(player.getUniqueId(),amount,permanent,time,days);
                        PFPlayer pfPlayer = PFPlayer.GetPFPlayer(Objects.requireNonNull(Bukkit.getPlayer(cmArgs[2])).getUniqueId());
                        if (pfPlayer != null) {
                            pfPlayer.addMultiplier(multiplier);
                        }
                        else {
                            pfPlayer = new PFPlayer(player.getUniqueId());
                            pfPlayer.addMultiplier(multiplier);
                        }
                        if(permanent) {
                            sender.sendMessage(defaultColor + "You have gave " + cmArgs[2] + " a permanent multiplier of " + amount + "X");
                        }
                        else {
                            sender.sendMessage(defaultColor + "You have gave " + cmArgs[2] + " a multiplier of " + amount + "X for "
                                    + multiplier.getDays() + "Days " + multiplier.getTime().getHours() + "Hours " + multiplier.getTime().getMinutes() + "Minutes " + multiplier.getTime().getSeconds() + "Seconds");
                        }
                    } else {
                        sender.sendMessage(defaultColor + "/PF multiplier give {player} {amount} {0:0:0:0}:{permanent}");
                    }
                } else {
                    sender.sendMessage(defaultColor + cmArgs[2] + " is not a valid player!");
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
        if (cmArgs[1].equalsIgnoreCase("clear")) {
            if (cmArgs.length > 2) {
                if (Bukkit.getPlayer(cmArgs[2]) != null) {
                        PFPlayer pfPlayer = PFPlayer.GetPFPlayer(Bukkit.getPlayer(cmArgs[2]).getUniqueId());
                        if (pfPlayer != null) {
                            pfPlayer.clearMultipliers();
                        }
                    sender.sendMessage(defaultColor +"You have clear all multipliers from "+ cmArgs[2]);
                } else {
                    sender.sendMessage(defaultColor + cmArgs[2] + " is not a valid player!");
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
        if (cmArgs.length == 2) {
            if (cmArgs[1].equalsIgnoreCase("help")) {
                sender.sendMessage(defaultColor + "/PF multiplier give {player} {amount} {0:0:0:0}:{permanent}");
                sender.sendMessage(defaultColor + "/PF multiplier clear {player}");
                return true;
            }
        }
        return false;
    }

}
