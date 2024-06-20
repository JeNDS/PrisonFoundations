package JeNDS.Plugins.PrisonFundations.Mines.Events;

import JeNDS.Plugins.JeNDSAPI.Other.JDItem;
import JeNDS.Plugins.JeNDSAPI.Tools.BlockBreakEXP;
import JeNDS.Plugins.JeNDSAPI.Tools.BlockBreakPickup;
import JeNDS.Plugins.JeNDSAPI.Tools.Utility;
import JeNDS.Plugins.PrisonFundations.Managers.EventManager;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.GameMode;
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

import static JeNDS.Plugins.PrisonFundations.Other.Files.Config.DisableExperience;

public class MineEvents extends EventManager {


    @EventHandler()
    public void mineEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (Mine.LocationInMine(event.getBlock().getLocation())) {
            if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                if (isMineResetting(event.getBlock().getLocation())) {
                    event.setCancelled(true);
                    player.sendMessage(Presets.SecondaryColor + "You have to wait for the reset to finish!");
                }
                event.setDropItems(false);
                if (Utility.SpaceInInventory(event.getPlayer())) {
                    new BlockBreakPickup(player, event);
                    if (!DisableExperience) {
                        new BlockBreakEXP(player, event);
                    } else {
                        event.setExpToDrop(0);
                    }
                } else {
                    event.setDropItems(true);
                    player.sendMessage(Presets.SecondaryColor + "Your Inventory is Full");
                }
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
            if (event.getPlayer().hasPermission("pf.admin")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void hologramRemover(PlayerInteractEvent event) {
        if (event.getPlayer().hasPermission("pf.admin")) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(JDItem.CustomItemStack(Material.BLAZE_ROD, Presets.MainColor + "Hologram Remover", null))) {
                    for (Entity entity : event.getPlayer().getWorld().getNearbyEntities(event.getPlayer().getLocation(), 1, 2, 1)) {
                        if (entity instanceof ArmorStand) {
                            if (entity.getCustomName() != null) {
                                if (Mine.RemoveMineHologramFromLocation(entity.getLocation())) return;
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
                if (mine.getBlockLocations().contains(location)) return true;
            }
        }
        return false;
    }

    private boolean isMineResetting(Location location) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                if (mine.getBlockLocations().contains(location)) {
                    return mine.isMineResetting();
                }
            }
        }
        return false;
    }


}
