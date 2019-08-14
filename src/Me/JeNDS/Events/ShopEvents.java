package Me.JeNDS.Events;

import Me.JeNDS.Objects.Mine;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import jdk.nashorn.internal.ir.Block;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

public class ShopEvents implements Listener {


    @EventHandler
    public void placeSigns(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(event.getBlockPlaced().getState() instanceof Sign){
            Sign sign = (Sign) event.getBlockPlaced().getState();
            if(event.getItemInHand().hasItemMeta()){
                if(event.getItemInHand().getItemMeta().hasDisplayName()){
                    String[] split = event.getItemInHand().getItemMeta().getDisplayName().split("\\s");
                    if(split.length == 2) {
                        Bukkit.broadcastMessage("test 1");
                        if (!Catch.RunningMines.isEmpty()) {
                            for (Mine mine1 : Catch.RunningMines) {
                                Bukkit.broadcastMessage("test 2 "+split[0]);
                                if (split[0].contains(mine1.getName())) {
                                    Bukkit.broadcastMessage("test 2");
                                    if (split[1].contains("Percentage")){
                                        Bukkit.broadcastMessage("test 3");
                                        sign.setLine(0,Presets.StandOutColor+mine1.getName());
                                        sign.setLine(1,"");
                                        sign.setLine(2,Presets.StandOutColor+"Percentage");
                                        sign.setLine(3,Presets.StandOutColor+mine1.getMinePercentage().toString());
                                        //player.getWorld().getBlockAt(event.getBlockPlaced().getLocation()).se

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }


    @EventHandler
    public void ceckSignCreation(SignChangeEvent event) {
        Bukkit.broadcastMessage("test1");
        if (isMinePercentage(event.getLines())) {
            Bukkit.broadcastMessage("test2" +
                    "" +
                    "" +
                    "");
            String mine = getMine(event.getLine(2));
            event.setLine(0, Presets.StandOutColor + mine);
            event.setLine(1,"");
            event.setLine(2, Presets.DefaultColor + "Percent");
            event.setLine(3, Presets.StandOutColor + "" + getPercentage(mine));
        }
        if (isMineTime(event.getLines())) {
            Bukkit.broadcastMessage("test3");
            String mine = getMine(event.getLine(2));
            event.setLine(0, Presets.StandOutColor + mine);
            event.setLine(1,"");
            event.setLine(2, Presets.DefaultColor + "Time Left");
            String[] s = Double.toString(getPercentage(mine)).split(".");
            Integer Hours = Integer.parseInt(s[0]);
            Integer Minutes = Integer.parseInt(s[1]);
            event.setLine(3, Presets.StandOutColor + "H:" + Hours + "   M:" + Minutes);
        }
        event.setCancelled(true);
    }


    public boolean isMinePercentage(String[] lines) {
        if (lines.length >= 1) {
            if (lines[0].contains("[Mines Info]")) {
                if (lines.length >= 2) {
                    if (lines[1].contains("[Percentage]")) {
                        if (lines.length >= 3) {
                            if (!Catch.RunningMines.isEmpty()) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (lines[2].contains("[" + mine.getName() + "]")) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isMineTime(String[] lines) {
        if (lines.length >= 1) {
            if (lines[0].equals("[Mines Info]")) {
                if (lines.length >= 2) {
                    if (lines[1].equals("[Time]")) {
                        if (lines.length >= 3) {
                            if (!Catch.RunningMines.isEmpty()) {
                                for (Mine mine : Catch.RunningMines) {
                                    if (lines[2].equals("[" + mine.getName() + "]")) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public String getMine(String line) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                line = line.replace("[", "");
                line = line.replace("]", "");
                Bukkit.broadcastMessage(line);
                if (mine.getName().equals(line)) {
                    return mine.getName();
                }
            }
        }
        return null;
    }

    public Integer getPercentage(String mineName) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                if (mine.getName().equals(mineName)) {
                    return mine.getMinePercentage();
                }
            }
        }
        return null;
    }

    public Double getTime(String mineName) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                if (mine.getName().equals(mineName)) {
                    return mine.getTimeBeforeReset();
                }
            }
        }
        return null;
    }
}
