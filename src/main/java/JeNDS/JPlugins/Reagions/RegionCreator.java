package JeNDS.JPlugins.Reagions;

import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class RegionCreator implements Listener {
    public static HashMap<Player, Location> PlayerMinePositionOne = new HashMap<>();
    public static HashMap<Player, Location> PlayerMinePositionTwo = new HashMap<>();

    public static ArrayList<Location> regionCreator(Location location1, Location location2) {
        int x = (int) location1.getX();
        int y = (int) location1.getY();
        int z = (int) location1.getZ();
        int x2 = (int) location2.getX();
        int y2 = (int) location2.getY();
        int z2 = (int) location2.getZ();
        ArrayList<Location> region = new ArrayList<>();
        for (int xi = 0; xi < numberChanges((int) location1.getX(), (int) location2.getX()); xi++) {
            z = (int) location1.getZ();
            for (int zi = 0; zi < numberChanges((int) location1.getZ(), (int) location2.getZ()); zi++) {
                y = (int) location1.getY();
                for (int yi = 0; yi < numberChanges((int) location1.getY(), (int) location2.getY()); yi++) {
                    Location newLoc = new Location(location1.getWorld(), x, y, z);
                    region.add(newLoc);
                    if (greater(y, y2)) {
                        y--;
                    } else {
                        y++;
                    }
                }
                if (greater(z, z2)) {
                    z--;
                } else {
                    z++;
                }
            }
            if (greater(x, x2)) {
                x--;
            } else {
                x++;
            }

        }
        return region;


    }

    private static boolean greater(int x, int x2) {
        if (x >= x2) {
            return true;
        } else {
            return false;
        }
    }

    private static int numberChanges(int x, int x2) {
        int newint = x - x2;
        if (newint > 0) {
            return newint + 1;
        } else {
            return Math.abs(newint) + 1;
        }
    }

    @EventHandler
    public void regionSelectorListener(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand() != null) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() != null) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLDEN_SHOVEL) {
                    if (event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Region Selector")) {
                            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                                if (!PlayerMinePositionTwo.isEmpty()) {
                                    if (PlayerMinePositionTwo.containsKey(event.getPlayer())) {
                                        if (PlayerMinePositionTwo.get(event.getPlayer()).equals(event.getClickedBlock().getLocation())) {
                                            event.setCancelled(true);
                                            return;
                                        }
                                    }
                                }
                                PlayerMinePositionTwo.put(event.getPlayer(), event.getClickedBlock().getLocation());
                                event.getPlayer().sendMessage(Presets.MainColor + "you have selected position 2");
                                event.setCancelled(true);
                                return;

                            }
                            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                                if (!PlayerMinePositionOne.isEmpty()) {
                                    if (PlayerMinePositionOne.containsKey(event.getPlayer())) {
                                        if (PlayerMinePositionOne.get(event.getPlayer()).equals(event.getClickedBlock().getLocation())) {
                                            event.setCancelled(true);
                                            return;
                                        }
                                    }
                                }
                                PlayerMinePositionOne.put(event.getPlayer(), event.getClickedBlock().getLocation());
                                event.getPlayer().sendMessage(Presets.MainColor + "you have selected position 1");
                                event.getClickedBlock().setType(event.getClickedBlock().getType());
                                event.setCancelled(true);
                                return;
                            }
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }

    }
}
