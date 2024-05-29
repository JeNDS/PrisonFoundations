package JeNDS.Plugins.PrisonFundations.Mines.Utilities.Events;


import JeNDS.Plugins.JeNDSAPI.Other.JDItem;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.AutoBlock;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.AutoSmelt;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.BlockUtility.BlockUtility;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.BlockUtility.BlockUtilityType;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.Files.UtilitiesFile;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class UtilityEvents implements Listener {

    @EventHandler
    private void placeEvent(BlockPlaceEvent event) {
        if (isUtility(event, Material.CRAFTING_TABLE, "Auto Blocker")) ;
        else {
            isUtility(event, Material.FURNACE, "Auto Smelter");
        }

    }

    private boolean isUtility(BlockPlaceEvent event, Material material, String name) {
        if (event.getPlayer().hasPermission("PF.Admin")) {
            if (event.getItemInHand().isSimilar(JDItem.CustomItemStack(material, Presets.MainColor + name, null))) {
                BlockUtility utility = new BlockUtility(event.getBlockPlaced().getLocation(), BlockUtilityType.GetBlockUtilityTypeFromString(name));
                if (name.equalsIgnoreCase("Auto Blocker")) {
                    utility.setType(BlockUtilityType.AUTOBLOCK);
                }
                if (!Catch.blockUtilities.contains(utility)) {
                    Catch.blockUtilities.add(utility);
                }
                UtilitiesFile.saveUtility(utility);
                event.getPlayer().sendMessage(Presets.MainColor + "You have placed an " + name);
                return true;
            }
        }
        return false;
    }

    @EventHandler
    private void brakeEvent(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("PF.Admin")) {
            BlockUtility utility = BlockUtility.BlockUtilityFromLocation(event.getBlock().getLocation());
            if (utility != null) {
                utility.remove();
            }
        }
    }

    @EventHandler
    private void interactEvent(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            BlockUtility utility = BlockUtility.BlockUtilityFromLocation(Objects.requireNonNull(event.getClickedBlock()).getLocation());
            if (utility != null) {
                if (utility.getType().equals(BlockUtilityType.AUTOBLOCK)) {
                    if (event.getPlayer().hasPermission("PF.AutoBlocking")) {
                        new AutoBlock(event.getPlayer());
                        event.setCancelled(true);
                    }
                }
                if (utility.getType().equals(BlockUtilityType.AUTOSMELT)) {
                    if (event.getPlayer().hasPermission("PF.AutoSmelter")) {
                        new AutoSmelt(event.getPlayer());
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
