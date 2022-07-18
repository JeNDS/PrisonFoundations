package JeNDS.JPlugins.CustomEnchants;

import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Objects.MineObjects.Mine;
import JeNDS.JPlugins.Utilities.AutoPickUp;
import JeNDS.Plugins.PluginAPI.Enchants.CustomEnchant;
import JeNDS.Plugins.PluginAPI.Enchants.EnchantmentWrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Explosion extends CustomEnchant {
    public static Enchantment explosion = new EnchantmentWrapper("explosion", "Explosion", 1);


    public Explosion() {
        register(explosion, this, PF.getInstance());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void blockBrakeEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        double luck = 10;
        if(Math.random()*100<=luck) {
            if (PlayerHasEnchantedItem(explosion, player)) {
                createExplosion(player, event.getBlock().getLocation());
            }
        }
    }

    private void createExplosion(Player player, Location location) {
        //todo keep testing
        ArrayList<ItemStack > itemStacks = new ArrayList<>();
        ArrayList<Location> locations = generateLocation(location, 2);
        if(!locations.isEmpty()) {
            for (Location location1 : locations) {
                if (location1.getBlock().getType() != Material.AIR) {
                    itemStacks.addAll(location1.getBlock().getDrops());
                    location1.getBlock().setType(Material.AIR);
                }
            }
            for (ItemStack itemStack : itemStacks) {
                new AutoPickUp(player,itemStack);
            }
            Objects.requireNonNull(location.getWorld()).playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 2, 0);
        }
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
                        double random = Math.random()*100;
                        if(bx  == bx+ size-2 || by  == by+ size-2  || bz  == bz+ size-2 ){
                            if(random<= 60){
                                if (!locations.contains(l)) {
                                    if (Mine.LocationInMine(l)) locations.add(l);
                                }
                            }
                        }
                        else {
                            if (!locations.contains(l)) {
                                if (Mine.LocationInMine(l)) locations.add(l);
                            }
                        }

                    }

                }
            }
        }
        return locations;
    }


    @Override
    protected Enchantment getEnchantment() {
        return explosion;
    }
}
