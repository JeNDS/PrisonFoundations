package JeNDS.Plugins.PrisonFundations.Commands.PFCommands;


import JeNDS.Plugins.PrisonFundations.Commands.CommandManager;
import JeNDS.Plugins.PrisonFundations.PlayerData.Multiplier;
import JeNDS.Plugins.PrisonFundations.PlayerData.PFPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Time;
import java.util.Objects;

public class MultiplierCommands extends CommandManager {

    protected static void LoadCommand() {
        if (hasPermission(commandSender, "pf.multipliers"))
            if (commandArgs[0].equalsIgnoreCase("multiplier")) {

                IsCommand = true;
                if (commandArgs.length > 1) {
                    giveCommand();
                    clearCommand();
                    Help();
                } else {
                    commandSender.sendMessage(defaultColor + "/PF multiplier Help");
                }
            }
    }

    protected static void LoadResults() {
        if (tabArgs.length >= 2) {
            if (hasPermission(tabSender, "pf.multipliers"))
                if (tabArgs[0].equalsIgnoreCase("multiplier")) {
                    tabResult.clear();
                    if (tabArgs.length == 2) {
                        if ("give".startsWith(tabArgs[1].toLowerCase())) {
                            tabResult.add("give");
                        }
                        if ("set".startsWith(tabArgs[1].toLowerCase())) {
                            tabResult.add("set");
                        }
                        if ("clear".startsWith(tabArgs[1].toLowerCase())) {
                            tabResult.add("clear");
                        }
                    }
                    if (tabArgs[1].equalsIgnoreCase("give")) {
                        if (tabArgs.length == 3) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (player.getName().toLowerCase().startsWith(tabArgs[2].toLowerCase())) {
                                    tabResult.add(player.getName());
                                }
                            }
                        }
                        if (tabArgs.length == 4) {
                            for (double i = 1; i != 100; i++) {
                                if (Double.toString(i).startsWith(tabArgs[3].toLowerCase())) {
                                    tabResult.add(Double.toString(i));
                                }
                            }
                        }
                        if (tabArgs.length == 5) {

                            if ("permanent".startsWith(tabArgs[4].toLowerCase())) {
                                tabResult.add("permanent");
                            }
                            if ("Days:Hour:Minutes:Seconds".startsWith(tabArgs[4].toLowerCase())) {
                                tabResult.add("Days:Hour:Minutes:Seconds");
                            }
                            if (!"permanent".startsWith(tabArgs[4].toLowerCase())) {
                                tabResult.add("Days:Hour:Minutes:Seconds");
                            }

                        }
                    }
                    if (tabArgs[1].equalsIgnoreCase("set")) {
                        if (tabArgs.length == 3) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (player.getName().toLowerCase().startsWith(tabArgs[2].toLowerCase())) {
                                    tabResult.add(player.getName());
                                }
                            }
                        }
                        if (tabArgs.length == 4) {
                            for (double i = 1; i != 100; i++) {
                                if (Double.toString(i).startsWith(tabArgs[3].toLowerCase())) {
                                    tabResult.add(Double.toString(i));
                                }
                            }
                        }
                    }
                    if (tabArgs[1].equalsIgnoreCase("clear")) {
                        if (tabArgs.length == 3) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (player.getName().startsWith(tabArgs[2].toLowerCase())) {
                                    tabResult.add(player.getName());
                                }
                            }

                        }

                    }
                    if ("help".startsWith(tabArgs[1].toLowerCase())) {
                        tabResult.add("help");
                    }
                }
        }
    }

    private static void giveCommand() {
        if (commandArgs[1].equalsIgnoreCase("give") || (commandArgs[1].equalsIgnoreCase("set"))) {
            if (Bukkit.getPlayer(commandArgs[2]) != null) {
                if (commandArgs.length >= 4) {
                    Double amount = Double.parseDouble(commandArgs[3]);
                    if (amount != null)
                        if (amount != 0.00) {
                            boolean permanent = false;
                            int days = 0;
                            Time time = new Time(0, 0, 0);
                            if (commandArgs.length == 5) {
                                if (commandArgs[4].equalsIgnoreCase("permanent")) permanent = true;
                                else {
                                    String[] timeSplit = commandArgs[4].split(":");
                                    if (timeSplit.length == 4) {
                                        days = Integer.parseInt(timeSplit[0]);
                                        int hours = Integer.parseInt(timeSplit[1]);
                                        int minutes = Integer.parseInt(timeSplit[2]);
                                        int seconds = Integer.parseInt(timeSplit[3]);
                                        time = new Time(hours, minutes, seconds);

                                    } else {
                                        commandSender.sendMessage(defaultColor + "Invalid Time Format");
                                        commandSender.sendMessage(defaultColor + "/PF multiplier give {player} {amount} {0:0:0:0 | permanent}");
                                        return;

                                    }
                                }
                            }

                            Player player = Bukkit.getPlayer(commandArgs[2]);
                            assert player != null;
                            PFPlayer pfPlayer = PFPlayer.GetPFPlayer(Objects.requireNonNull(Bukkit.getPlayer(commandArgs[2])).getUniqueId());
                            Multiplier multiplier;
                            if (commandArgs[1].equalsIgnoreCase("set")) {
                                multiplier = new Multiplier(player.getUniqueId(), amount, true, new Time(0, 0, 0), 0);
                                pfPlayer.clearMultipliers();
                                pfPlayer.addMultiplier(multiplier);
                                commandSender.sendMessage(defaultColor + "You have set " + commandArgs[2] + " multiplier to " + amount + "X");
                            } else {
                                multiplier = new Multiplier(player.getUniqueId(), amount, permanent, time, days);
                                pfPlayer.addMultiplier(multiplier);
                                if (permanent) {
                                    commandSender.sendMessage(defaultColor + "You have gave " + commandArgs[2] + " a permanent multiplier of " + amount + "X");
                                } else {
                                    commandSender.sendMessage(defaultColor + "You have gave " + commandArgs[2] + " a multiplier of " + amount + "X for "
                                            + multiplier.getDays() + "Days " + multiplier.getTime().getHours() + "Hours " + multiplier.getTime().getMinutes() + "Minutes " + multiplier.getTime().getSeconds() + "Seconds");
                                }
                            }
                            return;
                        }
                }
            } else {
                commandSender.sendMessage(defaultColor + commandArgs[2] + " is not a valid player!");
            }
            if (commandArgs[1].equalsIgnoreCase("give"))
                commandSender.sendMessage(defaultColor + "/PF multiplier give {player} {amount} {Days:Hour:Minutes:Seconds | permanent}");
            if (commandArgs[1].equalsIgnoreCase("set"))
                commandSender.sendMessage(defaultColor + "/PF multiplier set {player} {amount}");
        }

    }

    private static void clearCommand() {
        if (commandArgs[1].equalsIgnoreCase("clear")) {
            if (Bukkit.getPlayer(commandArgs[2]) != null) {
                PFPlayer pfPlayer = PFPlayer.GetPFPlayer(Bukkit.getPlayer(commandArgs[2]).getUniqueId());
                if (pfPlayer != null) {
                    pfPlayer.clearMultipliers();
                }
                commandSender.sendMessage(defaultColor + "You have clear all multipliers from " + commandArgs[2]);
                return;
            } else {
                commandSender.sendMessage(defaultColor + commandArgs[2] + " is not a valid player!");
            }
            commandSender.sendMessage(defaultColor + "/PF multiplier clear {player}");


        }

    }


    private static void Help() {
        if (commandArgs.length == 2) {
            if (commandArgs[1].equalsIgnoreCase("help")) {
                commandSender.sendMessage(defaultColor + "/PF multiplier give {player} {amount} {Days:Hour:Minutes:Seconds | permanent}");
                commandSender.sendMessage(defaultColor + "/PF multiplier clear {player}");

            }
        }

    }

}
