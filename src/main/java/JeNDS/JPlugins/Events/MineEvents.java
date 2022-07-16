package JeNDS.JPlugins.Events;

import JeNDS.Plugins.PluginAPI.Other.JDItem;
import JeNDS.JPlugins.Objects.MineObjects.Mine;
import JeNDS.JPlugins.Static.Catch;
import JeNDS.JPlugins.Static.Presets;
import JeNDS.JPlugins.Utilities.AutoPickUp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
                new AutoPickUp(player, event);
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
            if(event.getPlayer().hasPermission("PF.Admin")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void hologramRemover(PlayerInteractEvent event) {
        if (event.getPlayer().hasPermission("PF.Admin")) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(JDItem.CustomItemStack(Material.BLAZE_ROD, Presets.DefaultColor + "Hologram Remover", null))) {
                    for (Entity entity : event.getPlayer().getWorld().getNearbyEntities(event.getPlayer().getLocation(), 1, 2, 1)) {
                        if (entity instanceof ArmorStand) {
                            if (entity.getCustomName() != null) {
                                if(Mine.RemoveMineHologramFromLocation(entity.getLocation()))return;
                                else entity.remove();
                            }
                        }
                    }
                }
            }
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
