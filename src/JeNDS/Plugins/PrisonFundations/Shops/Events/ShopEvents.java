package JeNDS.Plugins.PrisonFundations.Shops.Events;


import JeNDS.Plugins.PrisonFundations.Managers.EventManager;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class ShopEvents extends EventManager {


    @EventHandler
    public void placeSigns(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.getBlockPlaced().getState() instanceof Sign sign) {
            if (event.getItemInHand().hasItemMeta()) {
                if (event.getItemInHand().getItemMeta().hasDisplayName()) {
                    String[] split = event.getItemInHand().getItemMeta().getDisplayName().split("\\s");
                    if (split.length == 2) {
                        if (!Catch.RunningMines.isEmpty()) {
                            for (Mine mine1 : Catch.RunningMines) {
                                if (split[0].contains(mine1.getConfigName())) {
                                    if (split[1].contains("Percentage")) {
                                        sign.setLine(0, Presets.SecondaryColor + mine1.getConfigName());
                                        sign.setLine(1, "");
                                        sign.setLine(2, Presets.SecondaryColor + "Percentage");
                                        sign.setLine(3, Presets.SecondaryColor + mine1.getMinePercentage().toString());
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
    public void checkSignCreation(SignChangeEvent event) {
        Bukkit.broadcastMessage("test1");
        if (isMinePercentage(event.getLines())) {
            Bukkit.broadcastMessage("test2");
            String mine = getMine(event.getLine(2));
            event.setLine(0, Presets.SecondaryColor + mine);
            event.setLine(1, "");
            event.setLine(2, Presets.MainColor + "Percent");
            event.setLine(3, Presets.SecondaryColor + getPercentage(mine));
        }
        if (isMineTime(event.getLines())) {
            Bukkit.broadcastMessage("test3");
            String mine = getMine(event.getLine(2));
            event.setLine(0, Presets.SecondaryColor + mine);
            event.setLine(1, "");
            event.setLine(2, Presets.MainColor + "Time Left");
            String[] s = Double.toString(getPercentage(mine)).split(".");
            Integer Hours = Integer.parseInt(s[0]);
            Integer Minutes = Integer.parseInt(s[1]);
            event.setLine(3, Presets.SecondaryColor + "H:" + Hours + "   M:" + Minutes);
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
                                    if (lines[2].contains("[" + mine.getConfigName() + "]")) {
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
                                    if (lines[2].equals("[" + mine.getConfigName() + "]")) {
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
                if (mine.getConfigName().equals(line)) {
                    return mine.getConfigName();
                }
            }
        }
        return null;
    }

    public Integer getPercentage(String mineName) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                if (mine.getConfigName().equals(mineName)) {
                    return mine.getMinePercentage();
                }
            }
        }
        return null;
    }

}
