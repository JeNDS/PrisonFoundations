package Me.JeNDS.Events;

import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Utilities.AutoPickUp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class MineEvents extends EventManager {


    @EventHandler()
    public void mineEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (isBlockInMines(event.getBlock().getLocation())) {
            if (isMineResetting(event.getBlock().getLocation())) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You have to wait for the reset to finish!");
            } else {
                new AutoPickUp(player,event);
                event.setDropItems(false);
            }
        }
    }

    @EventHandler
    public void playSoundOnLevelUp(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() > event.getOldLevel()) {
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }
    }

    @EventHandler
    public void preventPlayerBuild(BlockPlaceEvent event) {
        if (isBlockInMines(event.getBlockPlaced().getLocation())) {
            event.setCancelled(true);
        }
    }


    private boolean isBlockInMines(Location location) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                if (mine.getBlockLocation().contains(location)) return true;
            }
        }
        return false;
    }

    private boolean isMineResetting(Location location) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                if (mine.getBlockLocation().contains(location)) {
                    return mine.isMineResetting();
                }
            }
        }
        return false;
    }

}
