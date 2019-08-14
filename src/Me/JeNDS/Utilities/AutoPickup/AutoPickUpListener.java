package Me.JeNDS.Utilities.AutoPickup;

import Me.JeNDS.Files.ShopsFile;
import Me.JeNDS.MainFolder.Main;
import Me.JeNDS.Objects.Mine;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

import static Me.JeNDS.Utilities.AutoBlock.AutoBlockListener.AutoBlock;
import static Me.JeNDS.Utilities.AutoSmelt.AutoSmeltListener.AutoSmelter;

public class AutoPickUpListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void autoPickup(BlockBreakEvent event) {
        if (event.getPlayer() instanceof Player) {
            if (event.getBlock() != null) {
                if (isBlockInMines(event.getBlock().getLocation())) {
                    for (ItemStack itemStack : event.getBlock().getDrops()) {
                        if (AutoSmelter(itemStack) != null) {
                            if (Catch.autoSmelt.containsKey(event.getPlayer()) && Catch.autoSmelt.get(event.getPlayer())) {
                                event.getPlayer().getInventory().addItem(AutoSmelter(itemStack));

                            } else {
                                event.getPlayer().getInventory().addItem(itemStack);
                            }

                            if (Catch.autoBlock.containsKey(event.getPlayer()))
                                if (Catch.autoBlock.get(event.getPlayer())) {
                                    AutoBlock(event.getPlayer());

                                }
                        } else {
                            event.getPlayer().getInventory().addItem(itemStack);
                            if (Catch.autoBlock.containsKey(event.getPlayer()))
                                if (Catch.autoBlock.get(event.getPlayer())) {
                                    AutoBlock(event.getPlayer());

                                }
                        }
                    }
                    if (event.getExpToDrop() > 0) {
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        event.getPlayer().giveExp(event.getExpToDrop());
                        event.setExpToDrop(0);
                    }
                    if (!spaceInInventory(event.getPlayer())) {
                        Integer wait = 5;
                        boolean foundToSell = false;
                        if (Catch.autoSell.containsKey(event.getPlayer()))
                            if (Catch.autoSell.get(event.getPlayer())) {
                                if (ShopsFile.FindPlayerShop(event.getPlayer()) != null) {
                                    String shop = ShopsFile.FindPlayerShop(event.getPlayer());
                                    if (!ShopsFile.GetShopItems(shop).isEmpty()) {
                                        double sold = 0.0;
                                        Integer itemsSold = 0;
                                        for (Material material : ShopsFile.GetShopItems(shop).keySet()) {
                                            for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
                                                if (itemStack != null)
                                                    if (itemStack.getType() != null) {
                                                        if (itemStack.getType().equals(material)) {
                                                            double moeny = itemStack.getAmount() * ShopsFile.GetShopItems(shop).get(material);
                                                            foundToSell = true;
                                                            sold = sold + moeny;
                                                            itemsSold = itemsSold + itemStack.getAmount();
                                                            Main.economy.depositPlayer(event.getPlayer(), moeny);
                                                            event.getPlayer().getInventory().remove(itemStack);
                                                        }
                                                    }
                                            }
                                        }
                                        if (foundToSell) {
                                            event.getPlayer().sendMessage(Presets.DefaultColor + "Sold " + Presets.StandOutColor + itemsSold + Presets.DefaultColor + " to shop " + Presets.StandOutColor + shop + Presets.DefaultColor + " for " + Presets.StandOutColor + sold);
                                        }
                                    }
                                }
                            }
                        if (!foundToSell) {
                            if (Catch.invetoryFullTimeWait.containsKey(event.getPlayer())) {
                                wait = Catch.invetoryFullTimeWait.get(event.getPlayer());
                            }
                            if (wait == 5) {
                                event.getPlayer().sendMessage(Presets.DefaultColor + "Your Inventory is Full, time to sell your Items!");
                            }
                            wait--;
                            if (wait == 0) {
                                Catch.invetoryFullTimeWait.put(event.getPlayer(), 5);
                            } else {
                                Catch.invetoryFullTimeWait.put(event.getPlayer(), wait);
                            }
                        }
                    }
                    event.setDropItems(false);
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
    public void preventPlayerBuild(BlockPlaceEvent event){
        if(isBlockInMines(event.getBlockPlaced().getLocation())){
            event.setCancelled(true);
        }
    }

    private boolean spaceInInventory(Player player) {
        for (ItemStack itemStack : player.getInventory().getStorageContents()) {
            if (itemStack == null) {
                return true;
            }
        }
        return false;
    }


    private boolean isBlockInMines(Location location){
        if(!Catch.RunningMines.isEmpty()){
            for(Mine mine : Catch.RunningMines){
                if(!mine.getBlockLocation().isEmpty()){
                    for(Location location1 : mine.getBlockLocation()){
                        if(location.equals(location1)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
