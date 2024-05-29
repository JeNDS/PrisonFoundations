package JeNDS.Plugins.PrisonFundations.HandBombs;


import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.AutoPickUp;
import JeNDS.Plugins.PrisonFundations.Mines.Utilities.AutoSell;
import JeNDS.Plugins.PrisonFundations.PF;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class HandBomb {
    private final int size;
    private final ItemStack bomb;
    private final int speed;
    private final Player player;
    private int taskID;


    public HandBomb(int size, int speed, ItemStack bomb, Player player) {
        this.size = size;
        this.speed = speed;
        this.bomb = bomb;
        this.player = player;

    }


    public void launchGrande() {
         setDirection();
    }

    public boolean createExplosion(Location location) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        ArrayList<Location> locations = generateLocation(location, size);
        if (!locations.isEmpty()) {
            for (Location location1 : locations) {
                if (location1.getBlock().getType() != Material.AIR) {
                    itemStacks.addAll(location1.getBlock().getDrops());
                    location1.getBlock().setType(Material.AIR);
                }
            }
            for (ItemStack itemStack : itemStacks) {
                new AutoPickUp(player, itemStack);
                new AutoSell(player);
            }
            Objects.requireNonNull(location.getWorld()).playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 2, 0);
            return true;
        }
        return false;
    }

    private ArrayList<Location> generateLocation(Location location, int size) {
        ArrayList<Location> locations = new ArrayList<>();
        int bx = (int) location.getX();
        int by = (int) location.getY();
        int bz = (int) location.getZ();
        for (int x = bx - size; x <= bx + size; x++) {
            for (int y = by - size; y <= by + size; y++) {
                for (int z = bz - size; z <= bz + size; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if (distance < size * size) {
                        Location l = new Location(location.getWorld(), x, y, z);
                        double random = Math.random() * 100;
                        Mine mine = Mine.GetMineFromLocation(l);
                        if (mine != null)
                            if (!mine.isMineResetting()) {
                                if (bx == bx + size - 2 || by == by + size - 2 || bz == bz + size - 2) {
                                    if (random <= 60) {
                                        if (Mine.LocationInMine(l)) locations.add(l);

                                    }
                                } else {
                                    if (Mine.LocationInMine(l)) locations.add(l);
                                }

                            } else return new ArrayList<>();
                    }

                }
            }
        }
        return locations;
    }

    private void setDirection() {
        if (bomb != null) {
            try {
                Location location = player.getEyeLocation();
                World world = player.getWorld();
                Item item = world.dropItem(location, bomb);
                item.setVelocity(player.getLocation().getDirection().multiply(speed));
                item.setPickupDelay(Integer.MAX_VALUE);
                taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PF.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        double x = item.getLocation().getX();
                        double y = item.getLocation().getY();
                        double z = item.getLocation().getZ();
                        if (nullCheck(world.getBlockAt(item.getLocation()))
                                || nullCheck(world.getBlockAt(item.getLocation().add(.5, 0, 0)))
                                || nullCheck(world.getBlockAt(item.getLocation().subtract(.5, 0, 0)))
                                || nullCheck(world.getBlockAt(item.getLocation().add(0, .5, 0)))
                                || nullCheck(world.getBlockAt(item.getLocation().add(0, 0, .5)))
                                || nullCheck(world.getBlockAt(item.getLocation().subtract(0, 0, .5)))
                                || nullCheck(world.getBlockAt(item.getLocation().subtract(0, .5, 0)))) {
                            Location location1 = item.getLocation();
                            item.remove();
                            if (createExplosion(location1) && player.getGameMode().equals(GameMode.SURVIVAL)) {
                                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                            }
                            Bukkit.getScheduler().cancelTask(taskID);
                        }
                    }
                }, 10L, 5L);
            } catch (NullPointerException ignored) {
            }
        }
    }

    private boolean nullCheck(Block itemStack) {
        if (itemStack != null) {
            itemStack.getType();
            return itemStack.getType() != Material.AIR;
        }
        return false;
    }
}
