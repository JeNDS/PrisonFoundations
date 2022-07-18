package JeNDS.JPlugins.Events;

import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Objects.MineObjects.Mine;
import JeNDS.JPlugins.Objects.MineObjects.UpdateType;
import JeNDS.JPlugins.Static.Catch;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class SignEvents extends EventManager {

    @EventHandler
    public void placeSigns(BlockPlaceEvent event) {
        if (event.getItemInHand().hasItemMeta()) {
            if (event.getItemInHand().getItemMeta().hasDisplayName()) {
                if (event.getBlockPlaced().getState() instanceof Sign sign) {
                    placeMineSign(event, "Mine Percentage", true);
                    placeMineSign(event, "Time Until Reset", false);
                    event.setCancelled(true);
                }
            }
        }

    }


    @EventHandler
    public void hologram(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand().getType().equals(Material.ARMOR_STAND)) {
                Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation().add(event.getBlockFace().getDirection());
                if (placeHologram(player, location, "Mine Percentage", UpdateType.MINE_PERCENTAGE))
                    event.setCancelled(true);
                if (placeHologram(player, location, "Time Until Reset", UpdateType.MINE_TIME_RESET))
                    event.setCancelled(true);
            }

        }

    }

    @EventHandler
    public void singBrake(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("PF.Admin")) {
            for (Mine mine : Catch.RunningMines) {
                if (event.getBlock().getState() instanceof Sign) {
                    if (mine.deleteMineSing(event.getBlock().getLocation())) {
                        return;
                    }
                }
                if (mine.deleteMineSing(event.getBlock().getRelative(1, 0, 0).getLocation())) return;
                if (mine.deleteMineSing(event.getBlock().getRelative(-1, 0, 0).getLocation())) return;
                if (mine.deleteMineSing(event.getBlock().getRelative(0, 0, -1).getLocation())) return;
                if (mine.deleteMineSing(event.getBlock().getRelative(0, 0, 1).getLocation())) return;
            }
        }

    }

    private void placeMineSign(BlockPlaceEvent event, String name, boolean percentage) {
        ItemStack itemStack = event.getItemInHand();
        for (Mine mine : Catch.RunningMines) {
            if (itemStack.getItemMeta().getDisplayName().contains(name) && itemStack.getItemMeta().getDisplayName().contains(mine.getName())) {
                Sign sign = (Sign) event.getBlockPlaced().getState();
                Bukkit.getScheduler().scheduleSyncDelayedTask(PF.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        sign.setLine(0, Presets.MainColor + "" + mine.getName());
                        sign.setLine(1, Presets.SecondaryColor + "" + name);
                        sign.setLine(2, "");
                        UpdateType singType = null;
                        if (percentage) {
                            sign.setLine(3, Presets.ThirdColor + "" + mine.getMinePercentage() + "%");
                            singType = UpdateType.MINE_PERCENTAGE;
                        } else {
                            sign.setLine(3, Presets.ThirdColor + "" + mine.getTimeBeforeReset() + "M");
                            singType = UpdateType.MINE_TIME_RESET;
                        }
                        sign.setEditable(false);
                        sign.update(true, false);
                        event.getPlayer().getOpenInventory().close();
                        mine.createMineSign(sign.getLocation(), singType);
                    }
                }, 1L);

            }
        }
    }

    private boolean placeHologram(Player player, Location location, String name, UpdateType updateType) {
        for (Mine mine : Catch.RunningMines) {
            if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().contains(name) && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(mine.getName())) {
                return mine.createHologram(location,updateType);
            }
        }
        return false;
    }

}
