package Me.JeNDS.MainFolder;

import Me.JeNDS.Static.Catch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class OtherEvents implements Listener {

    @EventHandler
    public void giveCatchOnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("PF.AutoSmelt")) {
            if (!Catch.autoSmelt.containsKey(event.getPlayer())) {
                Catch.autoSmelt.put(event.getPlayer(), true);
            }
        }
        if (player.hasPermission("PF.AutoSell")) {
            if (!Catch.autoSell.containsKey(event.getPlayer())) {
                Catch.autoSell.put(event.getPlayer(), true);
            }
        }
        if (player.hasPermission("PF.AutoBlock")) {
            if (!Catch.autoBlock.containsKey(event.getPlayer())) {
                Catch.autoBlock.put(event.getPlayer(), true);
            }
        }
    }

    @EventHandler
    public void clearCatchOnLeave(PlayerQuitEvent event) {
        if (Catch.autoSmelt.containsKey(event.getPlayer())) {
            Catch.autoSmelt.remove(event.getPlayer());
        }
        if (Catch.autoSell.containsKey(event.getPlayer())) {
            Catch.autoSell.remove(event.getPlayer());
        }
        if (Catch.autoBlock.containsKey(event.getPlayer())) {
            Catch.autoBlock.remove(event.getPlayer());
        }
        if (Catch.invetoryFullTimeWait.containsKey(event.getPlayer())) {
            Catch.invetoryFullTimeWait.remove(event.getPlayer());
        }
        if (Catch.lastPlayerMenu.containsKey(event.getPlayer())) {
            Catch.lastPlayerMenu.remove(event.getPlayer());
        }
        if (Catch.nextPlayerMenu.containsKey(event.getPlayer())) {
            Catch.nextPlayerMenu.remove(event.getPlayer());
        }
    }

    @EventHandler
    public void cancelWeirthCommands(PlayerCommandSendEvent event) {
        ArrayList<String> Commands = new ArrayList<>();
        for (String s : event.getCommands()) {
            if (s.contains("prisonfoundations:")) {
                Commands.add(s);
            }
        }
        Commands = changer(Commands, "PF.Admin", "pf", event.getPlayer());
        Commands = changer(Commands, "PF.AutoSell", "autosell", event.getPlayer());
        Commands = changer(Commands, "PF.AutoBlock", "autoblock", event.getPlayer());
        Commands = changer(Commands, "PF.AutoSmelt", "autosmelt", event.getPlayer());
        Commands = changer(Commands, "PF.Sell", "sell", event.getPlayer());
        event.getCommands().removeAll(Commands);
    }

    public ArrayList<String> changer(ArrayList<String> list, String permmision, String replace, Player player) {
        ArrayList<String> Commands = list;
        if (player.hasPermission("PF.Admin")) {
            return Commands;
        }
        if (!player.hasPermission(permmision)) {
                Commands.add(replace);
                return Commands;
        }
        return Commands;
    }
}
